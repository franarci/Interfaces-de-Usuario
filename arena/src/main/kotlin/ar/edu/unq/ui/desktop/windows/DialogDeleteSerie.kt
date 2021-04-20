package ar.edu.unq.ui.desktop.windows

import ar.edu.unq.ui.desktop.appModel.SerieAppModel
import ar.edu.unq.ui.desktop.appModel.UnqflixAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner


class DialogDeleteSerie(owner: WindowOwner, serie: SerieAppModel?, val system: UnqflixAppModel): Dialog<SerieAppModel>(owner, serie) {
    override fun createFormPanel(p0: Panel?) {
        title = "Borrar Serie"
        Label(p0) with {
            text = "¿Está seguro de borrar permanentemente ${modelObject.title}?"
        }

        Panel(p0) with {
            asHorizontal()
            Button(it) with {
                caption = "Aceptar"
                onClick {
                    system.eliminarSerie()
                    accept() }
            }
            Button(it) with {
                caption = "Cancelar"
                onClick { cancel() }
            }
        }
    }

}
