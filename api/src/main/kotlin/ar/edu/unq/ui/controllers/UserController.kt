package ar.edu.unq.ui.controllers

import ar.edu.unq.ui.*
import data.idGenerator
import domain.*
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse

class UserController( back: UNQFlix, tokenJWT: TokenJWT): Controller(back,tokenJWT){

    fun register(ctx: Context){
        val userRP = ctx.bodyValidator<UserRegisterMapper>()
            .check(
                { it.name != null && it.email != null && it.password != null && it.creditCard != null && it.image != null },
                "Registro fallido: Faltan datos de registro"
            ).get()
        val id = idGenerator.nextUserId()
        val user = User(
            id, userRP.name!!, userRP.creditCard!!, userRP.image!!, userRP.email!!, userRP.password!!,
            mutableListOf(), mutableListOf()
        )
        try{ back.addUser(user)
            ctx.status(201)
            ctx.header("Authentication",tokenJWT.generateToken(user))
            ctx.json(mapOf("result:" to "ok"))

        }catch(e: ExistsException){
            ctx.status(400)
            ctx.json(mapOf("result:" to "error", "message:" to "User already exists"))
        }
    }
    fun login(ctx: Context) {
        val login = ctx.bodyValidator(UserLoginMapper::class.java)
            .check({ it.email != null && it.password != null }, "Error: email y contraseña son necesarios")
            .get()
        try{
            val user = validateUser(login.email!!, login.password!!)
            ctx.status(200)
            ctx.header("Authentication",tokenJWT.generateToken(user!!))
            ctx.json(mapOf("result:" to "ok"))
        } catch(e: NotFoundResponse){
            ctx.status(404)
            ctx.json(mapOf("result:" to "error","message" to "User not found"))
        } catch(e: Exception) {
            ctx.status(401)
            ctx.json(mapOf("result:" to "error","message" to "Wrong password"))
        }
    }

    fun getUsers(ctx: Context){
        val users = back.users.map { UserViewMapper(it.id, it.name, it.email) }
        ctx.json(users)
    }
    fun getUser(ctx: Context) {
        try {
            val user = getUserById(getTokenId(ctx))
            ctx.status(200)
            ctx.json(UserContentViewMapper(user.name, user.image,
                user.favorites.map { ContentViewMapper(it.id, it.description, it.title, it.state,it.poster) }.toMutableList(),
                user.lastSeen.map { ContentViewMapper(it.id, it.description, it.title, it.state,it.poster) }.toMutableList()))
        } catch(e: NotFoundResponse){
            ctx.status(401)
            ctx.json(mapOf("result:" to "Unauthorized"))
        }catch(e: NotFoundToken){
            ctx.status(401)
            ctx.json(mapOf("result:" to e.message))
        }
    }
    fun favoritos(ctx: Context) {
        try {
            getUserById(getTokenId(ctx))
            back.addOrDeleteFav(getTokenId(ctx), ctx.pathParam("contentId"))
            ctx.status(200)
            ctx.json(mapOf("result:" to "ok"))
        } catch (e: NotFoundResponse) {
            ctx.status(401)
            ctx.json(mapOf("result:" to "Unauthorized"))
        } catch (e: NotFoundToken) {
            ctx.status(401)
            ctx.json(mapOf("result:" to e.message))
        } catch(e: NotFoundException){
            ctx.status(404)
            ctx.json(mapOf("result:" to e.message))
        }
    }



    fun validateUser(email : String,password : String): User {
        if( !back.users.any{ it.email == email }) throw NotFoundResponse()
        val user = back.users.find{it.email==email && it.password ==password }
        if(user==null) throw Exception("Invalid password")
        else return user
    }

    fun addLastSeen(ctx: Context) {
        try {
            val token = ctx.header("Authentication")
            val userId = tokenJWT.validate(token!!)
            val contentId = ctx.bodyValidator(IdMapper::class.java).check({it.id!=null}, "ingrese un ID").get().id

            back.addLastSeen(userId, contentId)
            ctx.status(200)
            ctx.json(mapOf("result:" to "ok"))
        } catch (e: NotFoundException) {
            ctx.status(404)
            ctx.json(mapOf("result:" to e.message))
        } catch (e: NotFoundToken) {
            ctx.status(401)
            ctx.json(mapOf("result:" to e.message))
        }

    }
}
