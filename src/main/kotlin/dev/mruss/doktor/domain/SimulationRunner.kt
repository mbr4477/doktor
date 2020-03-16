package dev.mruss.doktor.domain

import dev.mruss.doktor.model.disease.CompartmentalDiseaseModel
import dev.mruss.doktor.model.sim.*
import kotlin.math.roundToInt

class SimulationRunner(
    private val sim: CDMSim
) {
    val history = mutableListOf<CDMSimState>()

    init {
        reset()
    }

    fun reset() {
        history.clear()
        val initialState = CDMSimState(
            day = 0,
            nodes = sim.graph.nodes.map { node ->
                CDMSimNodeState(
                    identifier = node.identifier,
                    compartments = mutableMapOf<String, Double>().also { map ->
                        sim.cdm.compartments.forEach { name ->
                            map[name] = if (name == sim.cdm.compartments.first()) node.population.toDouble() else 0.0
                        }
                    }
                )
            }
        )
        sim.initialConditions.forEach { initCond ->
            initialState.nodes
                .filter { it.identifier == initCond.node }
                .forEach {
                    it.compartments[initCond.compartment] = initCond.value.toDouble()
                }
        }
        history.add(initialState)
    }

    private fun stepNode(
        previousState: CDMSimNodeState,
        diseaseModel: CompartmentalDiseaseModel,
        subjectTo: List<UpdateVariables>
    ): CDMSimNodeState {
        val nextState = previousState.copy(compartments = previousState.compartments.toMutableMap())
        val variables = diseaseModel.variables.toMutableMap()

        // apply variable updates
        subjectTo.forEach { update ->
            update.variables.forEach {
                variables[it.first] = it.second
            }
        }

        // propagate the compartments
        diseaseModel.edges.forEach {
            var delta = (variables[it.multiple] ?: 0.0) * (previousState.compartments[it.of] ?: 0.0)
            if (delta > previousState.compartments[it.from] ?: 0.0) {
                delta = previousState.compartments[it.from] ?: 0.0
            }
            nextState.compartments[it.from] = (nextState.compartments[it.from] ?: 0.0) - delta
            nextState.compartments[it.to] = (nextState.compartments[it.to] ?: 0.0) + delta
        }
        return nextState
    }

    fun stepOneDay(): CDMSimState {
        // find all applicable actions
        val dayNumber = history.size
        val activeActions = sim.actions.filter { dayNumber > it.afterDays }

        // update nodes internally
        val nextState = CDMSimState(
            day = dayNumber,
            nodes = history.last().nodes.map { node ->
                val variableUpdates = activeActions.filter {
                    when (it) {
                        is UpdateVariables -> it.nodes.contains(node.identifier)
                        else -> false
                    }
                } as List<UpdateVariables>
                stepNode(node, sim.cdm, variableUpdates)
            }
        )

        // update nodes with mobility
        sim.graph.edges.forEach { edge ->
            // check for latest rule that would change this edge
            val action = activeActions.filter {
                it is UpdateMobilities
                        && it.from.contains(edge.from)
                        && it.to.contains(edge.to)
                        && it.modes.contains(edge.mode)
            }.lastOrNull() as UpdateMobilities?

            val rate = action?.withRate ?: edge.rate
            val constant = action?.withConstant ?: edge.constant
            val weights = action?.weights ?: edge.weights

            val source = nextState.nodes.filter { it.identifier == edge.from }.first()
            val dest = nextState.nodes.filter { it.identifier == edge.to }.first()

            sim.cdm.compartments.forEach { c ->
                val weight = weights[c] ?: 1.0
                source.compartments[c] = ((source.compartments[c] ?: 0.0) * (1 - rate*weight) - constant*weight)
                dest.compartments[c] = ((dest.compartments[c] ?: 0.0)
                        + ((source.compartments[c] ?: 0.0) * rate*weight)
                        + constant*weight)
            }
        }
        history.add(nextState)
        return nextState
    }

    fun runFor(days: Int) {
        for (i in 1..days) {
            stepOneDay()
        }
    }
}