package dev.mruss.doktor.app.api.yaml.entity
import kotlinx.serialization.*

@Serializable
data class CDMYaml(
    val metadata: CDMYamlMetadata,
    val variables: Map<String, Double>,
    val compartments: List<String>,
    val edges: List<CDMYamlEdge>
)

@Serializable
data class CDMYamlMetadata(
    val type: String,
    val identifier: String,
    val source: String = "",
    val title: String
)

@Serializable
data class CDMYamlEdge(
    val from: String,
    val to: String,
    val multiple: String,
    val of: String? = null
)