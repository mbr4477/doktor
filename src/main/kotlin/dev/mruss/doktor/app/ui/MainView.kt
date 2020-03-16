package dev.mruss.doktor.app.ui

import dev.mruss.doktor.model.sim.CDMSimState
import javafx.geometry.Pos
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.stage.FileChooser
import tornadofx.*
import kotlin.math.roundToInt

class MainView : View(), MainPresenter.View {
    private var chart by singleAssign<LineChart<Number, Number>>()
    private val presenter = MainPresenter(this)

    override val root = vbox {
        style {
            minHeight = 600.px
            minWidth = 800.px
        }
        chart = linechart("Simulation Results", NumberAxis(), NumberAxis())
        hbox(spacing = 20.0) {
            style {
                alignment = Pos.CENTER
            }
            button(text = "Choose Simulation File") {
                action {
                    val file = chooseFile(
                        "Select Simulation File",
                        arrayOf(FileChooser.ExtensionFilter("YAML", "*.yml"))
                    )
                    file.firstOrNull()?.also { simFile ->
                        presenter.pickedSimFile(simFile)
                    }
                }
            }
            button(text="Run") {
                action {
                    presenter.runClicked()
                }
            }
            textfield {
                style {
                    maxWidth = 50.px
                }
                promptText = "Duration"
                textProperty().onChange {
                    presenter.changedDuration(it?.toIntOrNull() ?: 0)
                }
            }
            label("Days")
        }
    }

    override fun showResults(results: List<CDMSimState>, compartments: List<String>) {
        chart.apply {
            data.clear()
            compartments.forEach { comp ->
                series(comp) {
                    results.forEach {
                        data(it.day, it.nodes.map { it.compartments[comp]?.roundToInt() ?: 0 }.sum())
                    }
                }
            }
        }
    }
}