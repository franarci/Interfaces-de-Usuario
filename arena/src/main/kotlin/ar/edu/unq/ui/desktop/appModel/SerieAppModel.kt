package ar.edu.unq.ui.desktop.appModel

import data.idGenerator
import domain.*
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException

@Observable
class SerieAppModel(var serie: Serie?): ContentAppModel(serie) {


    var relatedContent: MutableList<ContentAppModel> = mutableListOf()

    //Estas listas se usaran para pasar como parametro al crear una Serie
    val contents: MutableList<Content> = mutableListOf()
    var seasons: MutableList<SeasonAppModel> = mutableListOf()
    var categories: MutableList<CategoryAppModel> = mutableListOf()

    var selectedRemoveCategory: CategoryAppModel? = null
    var selectedAddCategory: CategoryAppModel? = null
    var selectedAddContent: ContentAppModel? = null
    var selectedRemoveContent: ContentAppModel? = null
    var selectSeason: SeasonAppModel? = null

    var seasonsNumber = seasons.size
    fun modelo() = serie

    init {
        if (serie != null) {
            id = serie!!.id
            title = serie!!.title
            description = serie!!.description
            poster = serie!!.poster
            categories = initCategories()
            seasons = initSeasons()
            seasonsNumber = seasons.size
            state = serie!!.state::class.java == Available::class.java
            this.numerarTemporadas()
        }
    }

    fun initCategories(): MutableList<CategoryAppModel> {
        return serie!!.categories.map { CategoryAppModel(it) }.toMutableList()
    }

    fun initSeasons(): MutableList<SeasonAppModel> {
        return serie!!.seasons.map { SeasonAppModel(it) }.toMutableList()
    }

    fun initContents() {
        serie!!.relatedContent.forEach { contents.add(it) }
    }

    fun agregarCategoria() {
    if(!categories.map { it.name }.contains(selectedAddCategory!!.name))
            categories.add(selectedAddCategory!!)
        actualizarCategorias()
    }

    fun quitarCategoria() {
        categories.remove(selectedRemoveCategory)
        actualizarCategorias()
    }


    fun agregarContenido(c: Content) {
        if(!relatedContent.map {it.title}.contains(c.title)) {
            relatedContent.add(selectedAddContent!!)    //add(AppModel)
            contents.add(c)                           //add(model)
            actualizarDatosAppModel()
            actualizarRelatedContent()
        }
    }

    fun quitarContenido(id: String) {
       //Saca de la lista de contenidoRelacionado el contenido seleccionado, sin importar si estoy creando una serie o editandola
        relatedContent.remove(selectedRemoveContent)
        contents.remove(contents.find{it.id == id})
        actualizarDatosAppModel()
        actualizarRelatedContent()
    }

    fun mapCategories(): MutableList<Category> {
        return categories.map { Category(it.id, it.name) }.toMutableList()
    }

    fun mapSeasons(): MutableList<Season> {
        return seasons.map { Season(it.id, it.title, it.description, it.poster, it.mapChapters()) }
            .toMutableList()
    }


    fun crearSerie() {
        if (title == "" || description == "") {
            throw UserException("Faltan completar datos")
        }

        if(id == "") {
            idGenerator.nextSerieId()
        }
        serie = Serie(
            id, title, description, poster, this.boolToState(), mapCategories(),
            mapSeasons(), contents)
    }

    fun boolToState(): ContentState {
        if (state) {
            return Available()
        } else return Unavailable()
    }

    fun addSeason(season: SeasonAppModel) {
        if (season.title == "" || season.description == "" || season.poster == "") throw UserException("Falta completar algunos campos")
        else {
            try {
            seasonsNumber = seasons.size
            modelo()!!.addSeason(season.createSeason())
            this.actualizarDatosAppModel()
            }

            catch(e: ExistsException) {
                throw UserException(e.message)
            }

            catch(e: NotFoundException) {
                throw UserException(e.message)
            }
        }
    }

    fun eliminarTemporada(season: SeasonAppModel) {
        modelo()!!.deleteSeason(season.id)
        this.actualizarDatosAppModel()
    }

    fun modifySeason(season: SeasonAppModel){
        season.actualizarModelo()
        season.actualizar()
        this.actualizarDatosAppModel()
    }

    fun iniciarDatosAppModel(){
        actualizarDatosAppModel()
        relatedContent = serie!!.relatedContent.map { ContentAppModel(it) }.toMutableList()
       if(contents.isEmpty()) {
           initContents()
       }
        state = serie!!.state::class.java == Available::class.java
        numerarTemporadas()
    }

    fun actualizarDatosAppModel() {//ROLLBACK
        if(serie != null) {
            title = serie!!.title
            description = serie!!.description
            poster = serie!!.poster
            categories = initCategories()
            seasons = initSeasons()
            seasonsNumber = seasons.size
            state = serie!!.state::class.java == Available::class.java
            numerarTemporadas()
        }
    }


    private fun numerarTemporadas(){
        var i=1
        seasons.map{it.number=i; i++}
    }



    fun actualizarModelo() {
        serie!!.relatedContent = contents
        serie!!.title = title
       serie!!.description = description
        serie!!.poster = poster
        serie!!.categories = mapCategories()
        serie!!.seasons = mapSeasons()
        serie!!.state = boolToState()
    }

    private fun findContenido(c: ContentAppModel, system: UnqflixAppModel): Content {
        var ret: Content? = system.system.series.find { c.title==it.title }
        if (ret == null){
            ret = system.system.movies.find { c.title==it.title }!!
        }
        return ret
    }

    fun actualizarRelatedContent() {
        var content = relatedContent
        relatedContent = mutableListOf()
        relatedContent = content
    }

    private fun actualizarCategorias() {
        var cats = categories
        categories = mutableListOf()
        categories = cats
    }

    fun actualizarContenidos(system: UnqflixAppModel) {
        if (contents.size != relatedContent.size) {
            contents.clear()
            var actualizada = mutableListOf<Content>()
            var ids = relatedContent.map { it.id }
            ids.forEach {
                if ("ser" in it) {
                    system.system.series.find { ser -> ser.id == it }?.let { it1 -> actualizada.add(it1) }
                } else {
                    system.system.movies.find { mov -> mov.id == it }?.let { it1 -> actualizada.add(it1) }
                }
            }
            contents.addAll(actualizada)
        }
    }
}