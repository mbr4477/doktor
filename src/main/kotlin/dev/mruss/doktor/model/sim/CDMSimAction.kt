package dev.mruss.doktor.model.sim

import dev.mruss.doktor.model.graph.MobilityMode

abstract class CDMSimAction {
    abstract val title: String
    abstract val afterDays: Int
}

data class UpdateMobilities(
    override val title: String,
    override val afterDays: Int,
    val modes: List<MobilityMode>,
    val from: List<String>,
    val to: List<String>,
    val withRate: Double,
    val withConstant: Int,
    val weights: Map<String, Double>
) : CDMSimAction()

data class UpdateVariables(
    override val title: String,
    override val afterDays: Int,
    val variables: List<Pair<String, Double>>,
    val nodes: List<String>
) : CDMSimAction()