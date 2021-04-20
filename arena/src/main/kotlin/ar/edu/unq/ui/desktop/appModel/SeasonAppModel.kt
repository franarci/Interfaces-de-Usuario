package ar.edu.unq.ui.desktop.appModel

import data.idGenerator
import domain.Chapter
import domain.ExistsException
import domain.NotFoundException
import domain.Season
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException

@Observable
class SeasonAppModel(var s: Season?) {

    var id = ""
    var title = ""
    var description = ""
    var poster = ""
    var chapters: MutableList<ChapterAppModel> = mutableListOf()
    var chaptersNumber = 0
    var number: Int = 0

    var selectedChapter : ChapterAppModel? = null
    fun model() = s


    init {
        if (s != null) {
             id = model()!!.id
             title = model()!!.title
             description = model()!!.description
             poster =model()!!.poster
             chapters = model()!!.chapters.map {ChapterAppModel(it)}.toMutableList()
            chaptersNumber = model()!!.chapters.size

            numerarCapitulos()
        }
    }


    fun createSeason(): Season {
        var season =
            Season(idGenerator.nextSeasonId(), this.title, this.description, this.poster, mutableListOf())
        return season
    }

    fun addChapter(chapter: ChapterAppModel) {
        if (chapter.title == "" || chapter.description == "" || chapter.video == ""|| chapter.thumbnail == "") throw UserException("Falta completar algunos campos")
        else {
            try {
                chaptersNumber = chapters.size
                model()!!.addChapter(chapter.createChapter())
                this.actualizar()
            }

            catch(e: ExistsException) {
                throw UserException(e.message)
            }

            catch(e: NotFoundException) {
                throw UserException(e.message)
            }
        }
        }



    fun mapChapters(): MutableList<Chapter> {
        return chapters.map {Chapter(it.id,it.title,it.description,it.duration,it.video,it.thumbnail)}.toMutableList()
    }


    fun actualizar(){
        title = model()!!.title
        description = model()!!.description
        poster =model()!!.poster
        chapters = model()!!.chapters.map {ChapterAppModel(it)}.toMutableList()
        chaptersNumber = model()!!.chapters.size

        numerarCapitulos()
    }

    fun actualizarModelo() {
        model()?.description=this.description
        model()?.title=this.title
        model()?.poster=this.poster
        //model()?.chapters=this.mapChapters()
    }

    private fun numerarCapitulos(){
        var i=1
        chapters.map{it.number=i; i++}
    }

    fun modifyChapter(chapter: ChapterAppModel) {
            chapter.actualizarModelo()
            chapter.actualizar()
            this.actualizar()
        }



}

