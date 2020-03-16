package dev.mruss.doktor.model.sim

import dev.mruss.doktor.model.disease.CompartmentalDiseaseModel
import dev.mruss.doktor.model.graph.MobilityGraph

data class CDMSim(
    val identifier: String,
    val title: String,
    val cdm: CompartmentalDiseaseModel,
    val graph: MobilityGraph,
    val initialConditions: List<CDMSimInitialCondition>,
    val actions: List<CDMSimAction>
)