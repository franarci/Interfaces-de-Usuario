package ar.edu.unq.ui.desktop.windows

import ar.edu.unq.ui.desktop.appModel.ChapterAppModel
import ar.edu.unq.ui.desktop.appModel.SeasonAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.KeyWordTextArea
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.Action

class WindowModifyChapter(owner: WindowOwner, modelObject: SeasonAppModel?) : Dialog<SeasonAppModel>(owner, modelObject) {


    override fun createFormPanel(mainPanel: Panel?) {

        val chapterToModify = modelObject.selectedChapter!!

        title = modelObject.title

        Label(mainPanel) with {
            text = "Editando temporada: "+ chapterToModify.title

            fontSize = 12 }


        KeyWordTextArea(mainPanel) with {
            bindToModel(chapterToModify, "title")
            width =200
        }

        Label(mainPanel) with {
            align= "left"
            text = "Description:"
        }

        KeyWordTextArea(mainPanel) with {
            bindToModel(chapterToModify, "description")
            width =200
        }

        Label(mainPanel) with {
            align= "left"
            text = "Duration:"
        }

        KeyWordTextArea(mainPanel) with {
            bindToModel(chapterToModify, "duration")
            width =200
        }

        Label(mainPanel) with {
            align= "left"
            text = "Thumbnail:"

        }

        KeyWordTextArea(mainPanel) with {
            bindToModel(chapterToModify, "thumbnail")
            width =200
        }

        Label(mainPanel) with {
            align= "left"
            text = "Video:"

        }

        KeyWordTextArea(mainPanel) with {
            bindToModel(chapterToModify, "video")
            width =200
        }

        Button(mainPanel) with {
            text = "Aceptar"
            onClick {
                modelObject.modifyChapter(chapterToModify)
                close()

            }
        }

        Button(mainPanel) with {
            text = "Cancelar"
            onClick {
                chapterToModify.actualizar()
                close()
            }
        }

    }
    }

