package dev.mruss.doktor.app.ui

import dev.mruss.doktor.app.api.dagger.DaggerInjectionComponent
import dev.mruss.doktor.domain.SimulationRunner
import dev.mruss.doktor.model.sim.CDMSimState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MainPresenter(private val view: View) {
    private var simFile: File? = null
    private var duration: Int = 1
    private val dagger = DaggerInjectionComponent.builder().build()

    fun pickedSimFile(file: File) {
        simFile = file
    }

    fun changedDuration(days: Int) {
        duration = days
    }

    fun runClicked() {
        GlobalScope.launch(Dispatchers.IO) {
            val importer = dagger.cdmSimImporter()
            simFile?.let {
                val sim = importer.import(it)
                val runner = SimulationRunner(sim)
                withContext(Dispatchers.Unconfined) {
                    runner.runFor(duration)
                    withContext(Dispatchers.Main) {
                        println(runner.history)
                        view.showResults(runner.history, sim.cdm.compartments)
                    }
                }
            }
        }
    }

    interface View {
        fun showResults(results: List<CDMSimState>,  compartments: List<String>)
    }
}