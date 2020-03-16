package dev.mruss.doktor.model.graph

data class MobilityGraphEdge(
    val mode: MobilityMode,
    val from: String,
    val to: String,
    val rate: Double,
    val constant: Int,
    val weights: Map<String, Double>
)