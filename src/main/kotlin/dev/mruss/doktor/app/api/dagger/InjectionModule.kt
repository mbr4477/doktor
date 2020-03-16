package dev.mruss.doktor.app.api.dagger

import dagger.Module
import dagger.Provides
import dev.mruss.doktor.app.api.yaml.CDMImporter
import dev.mruss.doktor.app.api.yaml.CDMSimImporter
import dev.mruss.doktor.app.api.yaml.MobilityGraphImporter
import dev.mruss.doktor.domain.interfaces.ICDMImporter
import dev.mruss.doktor.domain.interfaces.ICDMSimImporter
import dev.mruss.doktor.domain.interfaces.IMobilityGraphImporter

@Module
class InjectionModule {
    @Provides
    fun providesCDMImporter(): ICDMImporter = CDMImporter()

    @Provides
    fun providesMobilityGraphImporter(): IMobilityGraphImporter = MobilityGraphImporter()

    @Provides
    fun providesCDMSimImporter(cdmImporter: ICDMImporter, mobilityImporter: IMobilityGraphImporter): ICDMSimImporter =
        CDMSimImporter(cdmImporter, mobilityImporter)
}