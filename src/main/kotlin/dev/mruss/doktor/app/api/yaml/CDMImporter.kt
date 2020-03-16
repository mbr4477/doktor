package dev.mruss.doktor.app.api.yaml

import com.charleskorn.kaml.Yaml
import dev.mruss.doktor.app.api.yaml.entity.CDMYaml
import dev.mruss.doktor.domain.interfaces.ICDMImporter
import dev.mruss.doktor.model.disease.CDMEdge
import dev.mruss.doktor.model.disease.CompartmentalDiseaseModel
import java.io.File

class CDMImporter : ICDMImporter {
    override fun import(from: File): CompartmentalDiseaseModel {
        val input = from.readText()
        val raw = Yaml.default.parse(CDMYaml.serializer(), input)
        return CompartmentalDiseaseModel(
            identifier = raw.metadata.identifier,
            extends = raw.metadata.source,
            title = raw.metadata.title,
            variables = raw.variables.toMutableMap(),
            compartments = raw.compartments,
            edges = raw.edges.map {
                CDMEdge(
                    from = it.from,
                    to = it.to,
                    multiple = it.multiple,
                    of = it.of ?: it.from
                )
            }
        )
    }
}