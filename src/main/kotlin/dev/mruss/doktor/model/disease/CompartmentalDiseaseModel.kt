package dev.mruss.doktor.model.disease

data class CompartmentalDiseaseModel(
    val identifier: String,
    val extends: String,
    val title: String,
    val variables: MutableMap<String, Double>,
    val compartments: List<String>,
    val edges: List<CDMEdge>
)