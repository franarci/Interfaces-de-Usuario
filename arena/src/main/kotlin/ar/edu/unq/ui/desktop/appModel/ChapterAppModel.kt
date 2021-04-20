package ar.edu.unq.ui.desktop.appModel

import data.idGenerator
import domain.Chapter
import domain.Season
import org.uqbar.commons.model.annotations.Observable

@Observable
class ChapterAppModel(var ch: Chapter?) {
        var description = ""
        var duration = 0
        var id = ""
        var thumbnail = ""
        var title = ""
        var video = ""
    var number=0

    fun model()= ch

    init {
        if(ch != null){
             description = model()!!.description
             duration = model()!!.duration
             id = model()!!.id
             thumbnail = model()!!.thumbnail
             title = model()!!.title
             video = model()!!.video
        }
    }

    fun createChapter(): Chapter {
        var chapter =
            Chapter(idGenerator.nextChapterId(), this.title, this.description, this.duration, this.video, this.thumbnail)
        return chapter
    }


    fun actualizar(){
        title = model()!!.title
        description = model()!!.description
        duration =model()!!.duration
        video = model()!!.video
        thumbnail = model()!!.thumbnail
    }

    fun actualizarModelo() {
        model()?.description=this.description
        model()?.title=this.title
        model()?.duration=this.duration
        model()?.video = this.video
        model()?.thumbnail = this.thumbnail
    }

}
