package dev.mruss.doktor.model.sim

data class CDMSimState(
    val day: Int,
    val nodes: List<CDMSimNodeState>
)

data class CDMSimNodeState(
    val identifier: String,
    val compartments: MutableMap<String, Double>
)