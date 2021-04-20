package ar.edu.unq.ui.desktop.windows

import ar.edu.unq.ui.desktop.appModel.SeasonAppModel
import ar.edu.unq.ui.desktop.appModel.SerieAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.KeyWordTextArea
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.Action


class WindowAddSeason(owner: WindowOwner?, modelObject: SerieAppModel?): Dialog<SerieAppModel>(owner,modelObject) {

    val target= SeasonAppModel(null)

    override fun createFormPanel(mainPanel: Panel?) {

        title = modelObject.title

        Label(mainPanel) with {
            text = " Añadir nueva temporada "
            fontSize = 12}


        Label(mainPanel) with {
            align= "left"
            text = "Titulo:"

        }
        KeyWordTextArea(mainPanel) with {
            bindToModel(target, "title")
            width =200
        }
        Label(mainPanel) with {
            align="left"
            text = "Descripción:"
        }
        KeyWordTextArea(mainPanel) with {
            bindToModel(target, "description")
            width =200
        }
        Label(mainPanel) with {
            align= "left"
            text = "Poster URL:"
        }
        KeyWordTextArea(mainPanel) with {
            bindToModel(target, "poster")
            width =200
        }

        Button(mainPanel) with {
            text = "Agregar"
            onClick {
                modelObject.addSeason(target)// serieappmdel modelob
                accept()

            }
        }
        Button(mainPanel) with {
            text = "Cancelar"
            onClick {
                close()
            }
        }


    }




}
