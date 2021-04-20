package ar.edu.unq.ui.desktop.windows

import ar.edu.unq.ui.desktop.appModel.CategoryAppModel
import ar.edu.unq.ui.desktop.appModel.ContentAppModel
import ar.edu.unq.ui.desktop.appModel.SerieAppModel
import ar.edu.unq.ui.desktop.appModel.UnqflixAppModel
import domain.Content
import domain.ExistsException
import org.uqbar.arena.bindings.ObservableProperty
import org.uqbar.arena.bindings.PropertyAdapter
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.widgets.List
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import org.uqbar.lacar.ui.model.ControlBuilder

class DialogAddSerie(owner: WindowOwner, model: SerieAppModel, val system: UnqflixAppModel): Dialog<SerieAppModel>(owner, model) {
    override fun createFormPanel(mainPanel: Panel?) {
        title = "Nueva Serie"
        //COMENTARIO: hago GroupPanel porque por ahora es la unica forma en la que se puede leer el titulo de la ventana y el titulo de cada panel
        GroupPanel(mainPanel) with {
            title = "Titulo"
            width = 250
            asHorizontal()
            TextBox(it) with {
                fontSize = 10
                width = 250
                bindTo("title")
            }
            asHorizontal()
            Label(it) with {
                text = "Esta disponible:"
            }
            CheckBox(it) with {

                bindValueToProperty<Any, ControlBuilder>("state")

            }
        }
        GroupPanel(mainPanel) with {
            title = "Portada"
            asVertical()
            TextBox(it) with {
                width = 250
                bindTo("poster")
            }
        }
        GroupPanel(mainPanel) with {
            title = "Descripcion"
            asVertical()
            TextBox(it) with {
                fontSize = 10
                width = 210
                height = 125
                bindTo("description")
            }
        }
        GroupPanel(mainPanel) with { it ->
            title = "Agregar Contenido Relacionado y Categorias"

            List<SerieAppModel>(it) with {
                width = 50
                height = 150
                bindItemsToProperty("relatedContent").adaptWithProp<SerieAppModel>("title")
                bindSelectedTo("selectedRemoveContent")
            }
            Panel(it) with { p ->
                asHorizontal()
                Button(p) with {
                    caption = "<"
                    onClick {   if (thisWindow.modelObject.selectedAddContent != null) {
                        var c: Content? = system.findContent(thisWindow.modelObject.selectedAddContent!!)
                                 thisWindow.modelObject.agregarContenido(c!!)
                         }
                    }
                }
                asVertical()
                Button(p) with {
                    caption = ">"
                    onClick {
                        if (thisWindow.modelObject.selectedRemoveContent != null) {
                         var c: Content? = system.findContent(thisWindow.modelObject.selectedRemoveContent!!)
                            thisWindow.modelObject.quitarContenido((thisWindow.modelObject.selectedRemoveContent!!.id))
                        }
                    }
                }
            }
            asHorizontal()
            List<ContentAppModel>(it) with {
                width = 50
                height = 150
                val propiedadModelos = bindItems(ObservableProperty<UnqflixAppModel>(this@DialogAddSerie.system,"contenido"))
                propiedadModelos.setAdapter(PropertyAdapter(thisWindow.system.javaClass, "contenido")).adaptWithProp<ContentAppModel>("title")
                bindSelectedTo("selectedAddContent")
            }


            List<CategoryAppModel>(it) with {
                //En esta lista se van a ver todas las categorias de la serie
                width = 50
                height = 150
                bindItemsToProperty("categories").adaptWithProp<CategoryAppModel>("name")
                bindSelectedTo("selectedRemoveCategory")
            }
            Panel(it) with { q ->
                asHorizontal()
                Button(q) with {
                    caption = "<"
                    onClick {
                        if (thisWindow.modelObject.selectedAddCategory != null) {
                            thisWindow.modelObject.agregarCategoria()
                           system.quitarCategoria(thisWindow.modelObject.selectedAddCategory!!)
                        }
                    }
                }
                asVertical()
                Button(q) with {
                    caption = ">"
                    onClick {
                        val categoria = thisWindow.modelObject.selectedRemoveCategory
                        if (categoria != null) {
                            thisWindow.modelObject.quitarCategoria()
                            system.agregarCategoria(categoria)
                        }
                    }
                }
            }
                asHorizontal()
               List<CategoryAppModel>(it) with {
                   //En esta lista se van a ver todas las categorias que hay en el unqfix
                    width = 50
                   height = 150
                    val propiedadModelos = bindItems(ObservableProperty<UnqflixAppModel>(this@DialogAddSerie.system,"categories"))
                   propiedadModelos.setAdapter(PropertyAdapter(thisWindow.system.javaClass, "categories")).adaptWithProp<CategoryAppModel>("name")
                    bindSelectedTo("selectedAddCategory")
                }
            }


        Panel(mainPanel) with { r ->
            Button(r) with {
                caption = "Aceptar"
                onClick {
                    try {
                        thisWindow.modelObject.crearSerie()
                        system.agregarSerie(thisWindow.modelObject)
                        accept()} catch(e: ExistsException) {
                        throw UserException(e.message)
                    }
                }
                asHorizontal()
                Button(r) with {
                    caption = "Cancelar"
                    onClick { cancel() }
                }
            }
        }
    }
    override fun addActions(p0: Panel?) {}
}