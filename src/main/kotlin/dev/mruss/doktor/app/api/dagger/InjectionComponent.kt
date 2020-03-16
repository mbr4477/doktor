package dev.mruss.doktor.app.api.dagger

import dagger.Component
import dev.mruss.doktor.domain.interfaces.ICDMImporter
import dev.mruss.doktor.domain.interfaces.ICDMSimImporter
import dev.mruss.doktor.domain.interfaces.IMobilityGraphImporter

@Component(modules = [InjectionModule::class])
interface InjectionComponent {
    fun cdmImporter(): ICDMImporter
    fun mobilityImporter(): IMobilityGraphImporter
    fun cdmSimImporter(): ICDMSimImporter
}