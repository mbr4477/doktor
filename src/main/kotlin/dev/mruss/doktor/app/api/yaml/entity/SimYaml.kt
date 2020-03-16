package dev.mruss.doktor.app.api.yaml.entity
import kotlinx.serialization.*

@Serializable
data class SimYaml(
    val metadata: SimYamlMetadata,
    val compartmental_disease_model: String,
    val mobility_graph: String,
    val initial_conditions: List<SimYamlInitCond>,
    val actions: List<SimYamlAction> = emptyList()
)

@Serializable
data class SimYamlMetadata(
    val type: String,
    val identifier: String,
    val title: String
)

@Serializable
data class SimYamlInitCond(
    val set_initial_value: Int,
    val of_compartment: String,
    val in_node: String
)

@Serializable
data class SimYamlAction(
    val title: String,
    val after: Int,
    val update_variables: List<String>? = null,
    val update_mobilities: List<String>? = null,
    val from: List<String> = emptyList(),
    val of: List<String> = emptyList(),
    val to: List<String>,
    val with_rate: Double = 0.0,
    val with_constant: Int = 0,
    val weights: Map<String, Double> = emptyMap()
)