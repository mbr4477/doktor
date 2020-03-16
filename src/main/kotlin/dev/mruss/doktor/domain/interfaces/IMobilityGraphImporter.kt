package dev.mruss.doktor.domain.interfaces

import dev.mruss.doktor.model.graph.MobilityGraph
import java.io.File

interface IMobilityGraphImporter {
    fun import(from: File): MobilityGraph
}