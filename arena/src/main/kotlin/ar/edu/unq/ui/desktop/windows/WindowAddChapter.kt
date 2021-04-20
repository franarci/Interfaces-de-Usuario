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

class WindowAddChapter(owner: WindowOwner?, season: SeasonAppModel?) : Dialog<SeasonAppModel>(owner, season) {

    val target = ChapterAppModel(null)

    override fun createFormPanel(mainPanel: Panel?) {

        Label(mainPanel) with {
            text = "Añadir nuevo capitulo"
            fontSize = 12
        }

        Label(mainPanel) with {
            align = "left"
            text = "Titulo: "
        }



        KeyWordTextArea(mainPanel) with {
            bindToModel(target, "title")
            width = 200
        }

        Label(mainPanel) with {
            align = "left"
            text = "Description:"
        }

        KeyWordTextArea(mainPanel) with {
            bindToModel(target, "description")
            width = 200
        }

        Label(mainPanel) with {
            align = "left"
            text = "Duration:"
        }

        KeyWordTextArea(mainPanel) with {
            bindToModel(target, "duration")
            width = 200
        }

        Label(mainPanel) with {
            align = "left"
            text = "Thumbnail:"

        }

        KeyWordTextArea(mainPanel) with {
            bindToModel(target, "thumbnail")
            width = 200
        }

        Label(mainPanel) with {
            align = "left"
            text = "Video:"

        }

        KeyWordTextArea(mainPanel) with {
            bindToModel(target, "video")
            width = 200
        }

        Button(mainPanel) with {
            text = "Agregar"
            onClick {
                modelObject.addChapter(target)// serieappmdel modelob
                accept()

            }
        }

        Button(mainPanel) with {
            text = "Cancelar"
            onClick{
                close()
            }
        }

    }

}