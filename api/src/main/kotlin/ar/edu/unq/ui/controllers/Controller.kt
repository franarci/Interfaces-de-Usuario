package ar.edu.unq.ui.controllers

import ar.edu.unq.ui.TokenJWT
import domain.Content
import domain.UNQFlix
import domain.User
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import java.lang.Exception

open class Controller(val back: UNQFlix, val tokenJWT: TokenJWT) {
    fun getUserById(id: String): User {
        val user = back.users.find { it.id ==id }
        if(user==null) throw NotFoundResponse()
        else return user
    }

    fun getTokenId(ctx: Context): String {
        val token = ctx.header("Authentication")
        return tokenJWT.validate(token!!)
    }
    fun searchContent(ctx: Context) {
        var content: Content? = null
        if (ctx.pathParam("contentId").startsWith("mov")) {
            content = back.movies.find { ctx.pathParam("contentId") == it.id }
        }
        if (ctx.pathParam("contentId").startsWith("ser")) {
            content = back.series.find { ctx.pathParam("contentId") == it.id }
        }
        if (content == null) {
           throw InvalidIdException("Invalid content Id")
        }
    }

    class InvalidIdException(e: String) : Exception(e)
}