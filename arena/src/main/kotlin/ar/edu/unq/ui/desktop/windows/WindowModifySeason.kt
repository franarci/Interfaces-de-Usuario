package ar.edu.unq.ui.desktop.windows

import ar.edu.unq.ui.desktop.appModel.SerieAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.KeyWordTextArea
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.Action

class WindowModifySeason(owner: WindowOwner?, modelObject: SerieAppModel?): Dialog<SerieAppModel>(owner,modelObject) {

    override fun createFormPanel(mainPanel: Panel) {

        var seasonToModify= modelObject.selectSeason!!
        title = modelObject.title

        Label(mainPanel) with {
                text = "Editando temporada: "+ seasonToModify.title

            fontSize = 12}


        Label(mainPanel) with {
            align= "left"
            text = "Title:"

        }
        KeyWordTextArea(mainPanel) with {
                bindToModel(seasonToModify, "title")

            width =200
        }
        Label(mainPanel) with {
            align="left"
            text = "Description:"
        }
        KeyWordTextArea(mainPanel) with {
                bindToModel(seasonToModify, "description")

            width =200
        }
        Label(mainPanel) with {
            align= "left"
            text = "Poster:"
        }
        KeyWordTextArea(mainPanel) with {
                bindToModel(seasonToModify, "poster")

            width =200
        }
        Button(mainPanel) with {
            text = "Aceptar"
            onClick {
                modelObject.modifySeason(seasonToModify)
                close()

            }
        }

        Button(mainPanel) with {
            text = "Cancelar"
            onClick {
                seasonToModify.actualizar()
                close()
            }
            }
        }
    }

