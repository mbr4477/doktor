package dev.mruss.doktor.app.api.yaml

import com.charleskorn.kaml.Yaml
import dev.mruss.doktor.app.api.yaml.entity.MobilityYaml
import dev.mruss.doktor.domain.interfaces.IMobilityGraphImporter
import dev.mruss.doktor.model.graph.MobilityGraph
import dev.mruss.doktor.model.graph.MobilityGraphEdge
import dev.mruss.doktor.model.graph.MobilityGraphNode
import dev.mruss.doktor.model.graph.MobilityMode
import java.io.File

class MobilityGraphImporter : IMobilityGraphImporter {
    override fun import(from: File): MobilityGraph {
        val input = from.readText()
        val raw = Yaml.default.parse(MobilityYaml.serializer(), input)
        return MobilityGraph(
            identifier = raw.metadata.identifier,
            title = raw.metadata.title,
            nodes = raw.nodes.map {
                MobilityGraphNode(
                    identifier = it.identifier,
                    population = it.population,
                    position = Pair(it.x, it.y)
                )
            },
            edges = raw.edges.map {
                MobilityGraphEdge(
                    from = it.from,
                    to = it.to,
                    mode = MobilityMode.valueOf(it.mode.toUpperCase()),
                    rate = it.rate,
                    constant = it.constant,
                    weights = it.weights
                )
            }
        )
    }
}