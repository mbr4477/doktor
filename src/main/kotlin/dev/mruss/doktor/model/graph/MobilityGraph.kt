package dev.mruss.doktor.model.graph

data class MobilityGraph(
    val identifier: String,
    val title: String,
    val nodes: List<MobilityGraphNode>,
    val edges: List<MobilityGraphEdge>
)