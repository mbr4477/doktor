package dev.mruss.doktor.domain.interfaces

import dev.mruss.doktor.model.sim.CDMSim
import java.io.File

interface ICDMSimImporter {
    fun import(from: File): CDMSim
}