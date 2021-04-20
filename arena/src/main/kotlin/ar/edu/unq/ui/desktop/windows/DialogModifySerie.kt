package ar.edu.unq.ui.desktop.windows
import ar.edu.unq.ui.desktop.appModel.CategoryAppModel
import ar.edu.unq.ui.desktop.appModel.ContentAppModel
import ar.edu.unq.ui.desktop.appModel.SerieAppModel
import ar.edu.unq.ui.desktop.appModel.UnqflixAppModel
import domain.Content
import org.uqbar.arena.bindings.ObservableProperty
import org.uqbar.arena.bindings.PropertyAdapter
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.widgets.List
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.ControlBuilder

class DialogModifySerie(owner: WindowOwner, model: SerieAppModel, val system: UnqflixAppModel): Dialog<SerieAppModel>(owner, model) {
    override fun createFormPanel(mainPanel: Panel?) {
        title = "Editar Serie"
        thisWindow.modelObject.actualizarContenidos(system)
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
                    onClick {
                       var modelo = thisWindow.modelObject
                        if (modelo.selectedAddContent != null) {
                        var c: Content? = system.findContent(modelo.selectedAddContent!!)
                           modelo.agregarContenido(c!!)
                           system.quitarContenido(modelo.selectedAddContent!!)
                        }
                    }
                }
                asVertical()
                Button(p) with {
                    caption = ">"
                    onClick {
                        var modelo = thisWindow.modelObject
                        if (modelo.selectedRemoveContent != null) {
                            var c: Content? =system.findContent(modelo.selectedRemoveContent!!)
                            system.agregarContenido(modelo.selectedRemoveContent!!)
                            modelo.quitarContenido(modelo.selectedRemoveContent!!.id)
                        }
                    }
                }
            }
            asHorizontal()
            List<ContentAppModel>(it) with {
                width = 50
                height = 150
                val propiedadModelos = bindItems(ObservableProperty<UnqflixAppModel>(this@DialogModifySerie.system,"contenido"))
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
                        val categoria = thisWindow.modelObject.selectedAddCategory
                        if (categoria != null) {
                            thisWindow.modelObject.agregarCategoria()
                            system.quitarCategoria(categoria)
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
                val propiedadModelos = bindItems(ObservableProperty<UnqflixAppModel>(this@DialogModifySerie.system,"categories"))
                propiedadModelos.setAdapter(PropertyAdapter(thisWindow.system.javaClass, "categories")).adaptWithProp<CategoryAppModel>("name")
                bindSelectedTo("selectedAddCategory")
            }
        }


        Panel(mainPanel) with { r ->
            Button(r) with {
                caption = "Aceptar"
                onClick {
                    thisWindow.modelObject.actualizarModelo()
                    system.actualizar()
                    accept()
                }
            }
            asHorizontal()
            Button(r) with {
                caption = "Cancelar"
                onClick {
                   thisWindow.modelObject.actualizarDatosAppModel()
                    system.actualizar()
                    cancel() }
            }
        }
    }

    override fun addActions(p0: Panel?) {}
}