package ar.edu.unq.ui.desktop.windows

import ar.edu.unq.ui.desktop.appModel.SerieAppModel
import ar.edu.unq.ui.desktop.appModel.UnqflixAppModel
import ar.edu.unq.ui.desktop.transformers.ColorStateTransformer
import ar.edu.unq.ui.desktop.transformers.TextStateTransformer
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class WindowUnqflix(owner: WindowOwner, unqflix: UnqflixAppModel): SimpleWindow<UnqflixAppModel>(owner, unqflix) {

    override fun createMainTemplate(mainPanel: Panel) {
        title = "Unqflix"
        mainPanel.asVertical()
        createFormPanel(mainPanel)
    }

    override fun createFormPanel(mainPanel: Panel) {
        title = "Unqflix"
        GroupPanel(mainPanel) with {
            title = "Buscar Serie"
            width = 250
            asHorizontal()
            TextBox(it) with {
                fontSize = 10
                width = 250
                bindTo("busqueda")
            }
        }
        Panel(mainPanel) with{
        table<SerieAppModel>(it) {
            height= 190
            width=190
            setHeight(190)
            setWidth(100)
            bindSelectionTo("serieSeleccionada")
            bindItemsTo("series")
            column {
                title = "Titulo"
                fixedSize = 80
                bindContentsTo("title")
            }
            column {
                title = "Temporadas"
                fixedSize = 150
                bindContentsTo("seasonsNumber")
            }
            column {
                title = "Descripcion"
                fixedSize = 150
                bindContentsTo("description")
            }
            column {
                title = "Estado"
                fixedSize = 150
                bindContentsTo("state").setTransformer(TextStateTransformer())
                bindBackground("state").setTransformer(ColorStateTransformer())
                }
            }
        }
        Panel(mainPanel) with {
            asHorizontal()
            title = ""
            Button(it) with {
                caption = "Agregar Serie"
                onClick {
                    DialogAddSerie(thisWindow, SerieAppModel(null), thisWindow.modelObject).open()
                }
            }
            Button(it) with {
                caption = "Eliminar Serie"
                onClick {
                    DialogDeleteSerie(
                        thisWindow,
                        thisWindow.modelObject.serieSeleccionada,
                        thisWindow.modelObject
                    ).open()
                }
            }
            Button(it) with {
                caption = "Mostrar"
                onClick {
                    if(thisWindow.modelObject.serieSeleccionada!=null){
                        (WindowSerie(owner, thisWindow.modelObject.serieSeleccionada!!)).open()}
                        else throw UserException("No se selecciono ninguna serie")}
            }

            Button(it) with {
                caption = "Editar"
                onClick {if(thisWindow.modelObject.serieSeleccionada == null) throw UserException("No se selecciono ninguna serie")
                   // thisWindow.modelObject.initAllRelatedContent()
                    val serie = thisWindow.modelObject.serieSeleccionada
                    thisWindow.modelObject.actualizar()
                   serie!!.iniciarDatosAppModel()
                    DialogModifySerie(owner, serie, thisWindow.modelObject).open()
                }
            }
        }
    }
    override fun addActions(p0: Panel?) {}
}