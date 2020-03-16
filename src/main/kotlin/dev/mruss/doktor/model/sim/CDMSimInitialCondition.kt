package dev.mruss.doktor.model.sim

data class CDMSimInitialCondition(
    val node: String,
    val compartment: String,
    val value: Int
)