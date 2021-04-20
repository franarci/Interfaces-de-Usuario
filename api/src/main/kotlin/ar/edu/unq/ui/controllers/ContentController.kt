package ar.edu.unq.ui.controllers

import ar.edu.unq.ui.*
import domain.Available
import domain.*
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

class ContentController( back: UNQFlix, tokenJWT: TokenJWT): Controller(back,tokenJWT) {

    fun getContent(ctx: Context) {
        try {
            getUserById(getTokenId(ctx))
            var content = arrayListOf<Content>()
            val seriesDisponibles = back.series.filter { it.state::class.java == Available()::class.java }
            val moviesDisponibles = back.movies.filter { it.state::class.java == Available()::class.java }

            content.addAll(seriesDisponibles)
            content.addAll(moviesDisponibles)
            content.sortBy { it.title }


            ctx.json(content.map { ContentViewMapper(it.id, it.description, it.title, it.state, it.poster) })
            ctx.status(200)

        } catch (e: NotFoundResponse) {
            ctx.status(401)
            ctx.json(mapOf("result:" to "Unauthorized"))
        } catch (e: NotFoundToken) {
            ctx.status(401)
            ctx.json(mapOf("result:" to e.message))
        }
    }

    fun getContentByID(ctx: Context) {
        try {
            getUserById(getTokenId(ctx))
            searchContent(ctx)
            val id = ctx.pathParam("contentId")
            if ("mov" in id){
                val movie= back.movies.find { it.id==id }
                ctx.json(
                    MovieViewMapper(
                        movie?.id!!,
                        movie.title,
                        movie.description,
                        movie.poster,
                        movie.video,
                        movie.duration,
                        movie.actors,
                        movie.directors,
                        movie.categories,
                        movie.relatedContent.map {ContentViewMapper(it.id,it.description,it.title,it.state,it.poster)})
                )
            } else {
                val serie= back.series.find { it.id==id }

                ctx.json(
                    SerieViewMapper(
                        serie?.id!!,
                        serie.title,
                        serie.description,
                        serie.poster,
                        serie.relatedContent.map { ContentViewMapper(it.id,it.description,it.title,it.state,it.poster)},
                        serie.seasons
                    )
                )
            }
            ctx.status(200)

        }
        catch (e: NotFoundResponse) {
            ctx.status(401)
            ctx.json(mapOf("result:" to "Unauthorized"))
        } catch (e: NotFoundToken) {
            ctx.status(401)
            ctx.json(mapOf("result:" to e.message))
        } catch (e: InvalidIdException){
            ctx.status(404)
            ctx.json(mapOf("result:" to e.message))
        }
    }

    fun search(ctx: Context) {
        try {
            getUserById(getTokenId(ctx))
            val search = ctx.queryParam("text").toString()
            var content: MutableList<Content> = back.series.filter {
                it.title.contains(search, true) ||
                        it.description.contains(search, true)
            }.toMutableList()
            content.addAll(back.movies.filter {
                it.title.contains(search, true) || it.description.contains(search, true) }
                .toMutableList())

            ctx.status(200)
            ctx.json(content.map { ContentViewMapper(it.id, it.description, it.title, it.state,it.poster) }.toMutableList())
        } catch (e: NotFoundResponse) {
            ctx.status(401)
            ctx.json(mapOf("result:" to "Unauthorized"))
        } catch (e: NotFoundToken) {
            ctx.status(401)
            ctx.json(mapOf("result:" to e.message))
        }
    }

    fun getBanners(ctx: Context) {
        try {
            getUserById(getTokenId(ctx))
            var banners = back.banners.map { ContentViewMapper(it.id, it.description, it.title, it.state,it.poster) }.toMutableList()
            ctx.json(banners)
            ctx.status(200)
        } catch (e: NotFoundResponse) {
            ctx.status(401)
            ctx.json(mapOf("result:" to "Unauthorized"))
        } catch (e: NotFoundToken) {
            ctx.status(401)
            ctx.json(mapOf("result:" to e.message))
        }
    }
}
