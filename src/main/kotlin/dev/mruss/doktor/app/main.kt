package dev.mruss.doktor.app

import dev.mruss.doktor.app.ui.MainView
import tornadofx.App
import tornadofx.launch

class DoktorApp: App(MainView::class)

fun main(args: Array<String>) {
    launch<DoktorApp>()
}