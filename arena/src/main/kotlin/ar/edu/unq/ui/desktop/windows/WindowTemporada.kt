package ar.edu.unq.ui.desktop.windows

import ar.edu.unq.ui.desktop.appModel.ChapterAppModel
import ar.edu.unq.ui.desktop.appModel.SeasonAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class WindowTemporada(owner: WindowOwner, modelObject: SeasonAppModel?) : SimpleWindow<SeasonAppModel>(owner, modelObject) {

    override fun addActions(actionsPanel: Panel) {
    }

    override fun createFormPanel(mainPanel : Panel) {
        title = "UNQflix"

        Label(mainPanel) with {
            text = modelObject?.title.toString()
            fontSize = 16
            align = "left"
        }


        Label(mainPanel) with {
            text = "Chapters:"
        }

        table<ChapterAppModel>(mainPanel) with {
            bindItemsTo("chapters")
            bindSelectionTo("selectedChapter")

            column {
                title = "#"
                fixedSize = 50
                bindContentsTo("number")
            }
            column {
                title = "Title"
                fixedSize = 100
                align("center")
                bindContentsTo("title")
            }
            column {
                title = "Duration"
                fixedSize = 50
                align("center")
                bindContentsTo("duration")
            }

        }

        Button(mainPanel) with {
            text = "Add new chapter"
            onClick {
                WindowAddChapter(owner, thisWindow.modelObject).open()
            }
        }

        Button(mainPanel) with {
            text = "Modify chapter"
            onClick{
                WindowModifyChapter(owner, thisWindow.modelObject).open()
            }
        }
    }


}
