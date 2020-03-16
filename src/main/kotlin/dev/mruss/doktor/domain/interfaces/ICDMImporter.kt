package dev.mruss.doktor.domain.interfaces

import dev.mruss.doktor.model.disease.CompartmentalDiseaseModel
import java.io.File

interface ICDMImporter {
    fun import(from: File): CompartmentalDiseaseModel
}