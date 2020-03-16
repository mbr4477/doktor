package dev.mruss.doktor.app.api.yaml

import com.charleskorn.kaml.Yaml
import dev.mruss.doktor.app.api.yaml.entity.SimYaml
import dev.mruss.doktor.domain.interfaces.ICDMImporter
import dev.mruss.doktor.domain.interfaces.ICDMSimImporter
import dev.mruss.doktor.domain.interfaces.IMobilityGraphImporter
import dev.mruss.doktor.model.graph.MobilityMode
import dev.mruss.doktor.model.sim.CDMSim
import dev.mruss.doktor.model.sim.CDMSimInitialCondition
import dev.mruss.doktor.model.sim.UpdateMobilities
import dev.mruss.doktor.model.sim.UpdateVariables
import java.io.File

class CDMSimImporter(
    private val cdmImporter: ICDMImporter,
    private val mobilityImporter: IMobilityGraphImporter
) : ICDMSimImporter {
    override fun import(from: File): CDMSim {
        val input = from.readText()
        val raw = Yaml.default.parse(SimYaml.serializer(), input)
        val directory = from.parentFile
        return CDMSim(
            identifier = raw.metadata.identifier,
            title = raw.metadata.title,
            cdm = cdmImporter.import(File(directory, raw.compartmental_disease_model.plus(".yml"))),
            graph = mobilityImporter.import(File(directory, raw.mobility_graph.plus(".yml"))),
            initialConditions = raw.initial_conditions.map {
                CDMSimInitialCondition(
                    value = it.set_initial_value,
                    compartment = it.of_compartment,
                    node = it.in_node
                )
            },
            actions = raw.actions.map {
                if (it.update_mobilities != null) {
                    UpdateMobilities(
                        title = it.title,
                        afterDays = it.after,
                        modes = it.update_mobilities.map { MobilityMode.valueOf(it.toUpperCase()) },
                        from = it.from,
                        to = it.to,
                        withConstant = it.with_constant,
                        withRate = it.with_rate,
                        weights = it.weights
                    )
                } else {
                    UpdateVariables(
                        title = it.title,
                        afterDays = it.after,
                        variables = it.update_variables?.mapIndexed { index, v ->
                            Pair(v, it.to[index].toDoubleOrNull() ?: 0.0)
                        } ?: emptyList(),
                        nodes = it.of
                    )
                }
            }
        )
    }
}