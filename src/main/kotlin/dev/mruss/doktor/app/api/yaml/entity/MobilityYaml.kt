package dev.mruss.doktor.app.api.yaml.entity
import kotlinx.serialization.*

@Serializable
data class MobilityYaml(
    val metadata: MobilityYamlMetadata,
    val nodes: List<MobilityYamlNode>,
    val edges: List<MobilityYamlEdge>
)

@Serializable
data class MobilityYamlMetadata(
    val type: String,
    val identifier: String,
    val title: String
)

@Serializable
data class MobilityYamlEdge(
    val from: String,
    val to: String,
    val constant: Int = 0,
    val rate: Double = 0.0,
    val weights: Map<String,Double> = emptyMap(),
    val mode: String
)

@Serializable
data class MobilityYamlNode(
    val identifier: String,
    val population: Int,
    val x: Double,
    val y: Double
)