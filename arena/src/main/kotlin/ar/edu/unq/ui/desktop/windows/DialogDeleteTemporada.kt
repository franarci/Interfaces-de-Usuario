package ar.edu.unq.ui.desktop.windows

import ar.edu.unq.ui.desktop.appModel.SeasonAppModel
import ar.edu.unq.ui.desktop.appModel.SerieAppModel
import org.uqbar.arena.kotlin.extensions.text
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class DialogDeleteTemporada(owner: WindowOwner, temporada: SeasonAppModel?,var serie: SerieAppModel?):
    Dialog<SeasonAppModel>(owner,temporada) {
    override fun createFormPanel(p0: Panel?) {
        title = "Borrar Temporada"
        Label(p0) with {
            text = "¿Está seguro de borrar permanentemente ${modelObject.title}?"
        }

        Panel(p0) with {
            asHorizontal()
            Button(it) with {
                caption = "Aceptar"
                onClick {
                    serie!!.eliminarTemporada(thisWindow.modelObject)
                    accept()
                }
            }
            Button(it) with {
                caption = "Cancelar"
                onClick { cancel() }
            }
        }
    }

}
