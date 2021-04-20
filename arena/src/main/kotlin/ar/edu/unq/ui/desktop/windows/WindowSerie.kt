package ar.edu.unq.ui.desktop.windows

import ar.edu.unq.ui.desktop.appModel.SeasonAppModel
import ar.edu.unq.ui.desktop.appModel.SerieAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.layout.HorizontalLayout
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException


//en esta ventana se está en la serie que fue seleccionada en la ventana anterior (WindowUnqflix),
// acá se ven las temporadas de esa serie.

class WindowSerie (owner: WindowOwner, serie: SerieAppModel): SimpleWindow<SerieAppModel>(owner, serie){

    override fun addActions(actionsPanel: Panel) {

    }

    override fun createFormPanel(mainPanel: Panel) {
        title = "UNQflix"
        setMinWidth(500)

        Panel(mainPanel) with {

        Label(mainPanel) with {
            text = thisWindow.modelObject.title
            fontSize = 16
            align = "left"
        }
        Label(mainPanel) with {
            this.setWidth(350)
            text = thisWindow.modelObject.description
            align = "left"
            //como hago para que el texto se ajuste a la pantalla?
        }}


        Label(mainPanel) with {
            text = "seasons:"
        }

        table<SeasonAppModel>(mainPanel) with {
            this.width = 500
            bindItemsTo("seasons")//muestra todos los componentes que estan almacenados en la variable con el nombre"items"
            bindSelectionTo("selectSeason")//cuando se haga click e un elemento se va a bindear al item
            column {
                title = "#"
                fixedSize = 50
                bindContentsTo("number")//bindea al campo number de SeasonAppModel
            }
            column {
                title = "Title"
                fixedSize = 100
                align("center")
                bindContentsTo("title")
            }
            column {
                title = "#Chapters"
                fixedSize = 100
                align("center")
                bindContentsTo("chaptersNumber")
            }

        }


        Panel(mainPanel) with {
            title = ""
            this.layout= HorizontalLayout()
            Button(mainPanel) with {

                text = "Agregar temporada"
                onClick {
                    WindowAddSeason(owner, thisWindow.modelObject).open()
                }
            }
            Button(mainPanel) with {
                text = "Editar temporada"
                onClick {
                    if (thisWindow.modelObject.selectSeason != null) {
                        WindowModifySeason(owner, thisWindow.modelObject).open()
                    }
                    else {
                        throw UserException("No se seleccionó ninguna temporada")
                    }
                }
            }

            Button(mainPanel) with {
                text = "Eliminar temporada"
                onClick {
                    if (thisWindow.modelObject.selectSeason != null) {
                        DialogDeleteTemporada(
                            thisWindow,
                            thisWindow.modelObject.selectSeason,
                            thisWindow.modelObject
                        ).open()
                    } else {
                        throw UserException("No se seleccionó ninguna temporada")
                    }
                }

                Button(mainPanel) with {
                    text = "ver capítulos"
                    onClick {
                        if (thisWindow.modelObject.selectSeason != null) {
                            WindowTemporada(owner, thisWindow.modelObject.selectSeason).open()
                        } else {
                            throw UserException("No se seleccionó ninguna temporada")
                        }


                    }
                }
            }
        }
    }
}

