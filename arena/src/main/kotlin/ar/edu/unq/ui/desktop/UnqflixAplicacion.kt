package ar.edu.unq.ui.desktop

import ar.edu.unq.ui.desktop.appModel.UnqflixAppModel
import ar.edu.unq.ui.desktop.windows.WindowUnqflix
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window

class UnqflixAplicacion: Application() {
    override fun createMainWindow(): Window<*> {
        return WindowUnqflix(this, UnqflixAppModel())
    }
}

fun main(){
    UnqflixAplicacion().start()
}