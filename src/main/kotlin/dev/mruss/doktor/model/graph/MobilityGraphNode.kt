package dev.mruss.doktor.model.graph

data class MobilityGraphNode(
    val identifier: String,
    val population: Int,
    val position: Pair<Double, Double>
)