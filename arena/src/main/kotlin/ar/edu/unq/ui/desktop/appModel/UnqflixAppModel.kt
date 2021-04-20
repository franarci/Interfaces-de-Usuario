package ar.edu.unq.ui.desktop.appModel
import data.getUNQFlix
import domain.*
import org.eclipse.xtend.lib.annotations.Accessors
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException

@Observable
class UnqflixAppModel {


    var serieSeleccionada: SerieAppModel? = null
    val system : UNQFlix = getUNQFlix()
    var series: MutableList<SerieAppModel> = initSeries()
    var peliculas: MutableList<MovieAppModel> = initPeliculas()
    var contenido: MutableList<ContentAppModel> = mutableListOf()
    var busqueda: String = ""
        set(value) {
            field = value.toLowerCase()
            buscar()
        }
    var categories = initCategorias()



    init {
        contenido.addAll(series)
        contenido.addAll(peliculas)
     //   initAllRelatedContent()
    }

    /* fun initAllRelatedContent() {
        series.forEach { initRelatedContent(it) }
    }*/

    private fun initRelatedContent(serie: SerieAppModel) {

        var contents = system.series.find { it.id == serie.id }!!.relatedContent
        contents.forEach { contentModel->
          var contentToAdd = contenido.find {contentModel.title==it.title}
            serie.contents.add(contentModel)
            serie.relatedContent.add(contentToAdd!!)
        }
    }

    private fun initSeries() = system.series.map { SerieAppModel(it) }.toMutableList()

    private fun initPeliculas() = system.movies.map{MovieAppModel(it)}.toMutableList()

    private fun initCategorias() = system.categories.map { CategoryAppModel(it) }.toMutableList()

    fun actualizar(){
        initSeries()
        initPeliculas()
        initContent()
        initCategorias()
        //initAllRelatedContent()
    }

    private fun initContent() {
        contenido = mutableListOf()
        contenido.addAll(series)
        contenido.addAll(peliculas)
    }

    fun agregarSerie(serie: SerieAppModel){
        if(serie.title == "" ||  serie.description == "") {throw UserException("Faltan completar datos")}
        else {
          system.addSerie(serie.modelo()!!)
            actualizar()
        }
    }

    fun eliminarSerie() {
        system.deleteSerie(serieSeleccionada!!.id)
        actualizar()
    }

    fun buscar() {
        series = initSeries().filter{it.title.toLowerCase().contains(busqueda)}.toMutableList()
    }

    fun findContent(contentAM: ContentAppModel): Content? {
        if(contentAM::class.java == SerieAppModel::class.java) {
            return system.series.find { it.title == contentAM.title }
        } else return system.movies.find{it.title == contentAM.title}
    }

    fun quitarCategoria(category: CategoryAppModel) {
        categories.remove(category)
        actualizarCategorias()
    }

    fun agregarCategoria(category: CategoryAppModel) {
        if(!categories.map{it.name}.contains(category.name)) {
            categories.add(category)
            actualizarCategorias()
        }
    }


    fun agregarContenido(c: ContentAppModel) {
        if(!contenido.map { it.title }.contains(c.title))
        contenido.add(c)
        actualizarContenido()
    }

    fun quitarContenido(c: ContentAppModel) {
        contenido.remove(contenido.find { c.title ==it.title })
        actualizarContenido()
    }

    private fun actualizarContenido() {
        var temp = contenido
        contenido = mutableListOf()
        contenido= temp
    }

    private fun actualizarCategorias() {
        var temp = categories
        categories = mutableListOf()
        categories= temp
    }

}