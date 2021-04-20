package ar.edu.unq.ui

import domain.User
import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.NotFoundResponse
import io.javalin.http.UnauthorizedResponse

class JWTAccessManager(val tokenJWT: TokenJWT,val users: MutableList<User>) : AccessManager {
    fun validarToken(token: String): String {
        try {
            val userId = tokenJWT.validate(token)
            return userId
        } catch(e: NotFoundToken){
            throw UnauthorizedResponse("Token not found")
        }
    }
    fun validarUsuario(token: String) {
        val userId =  validarToken(token)
        users.find { it.id==userId } ?: throw NotFoundResponse("User not found")
    }

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authentication")

        when{
            token == null && roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            token == null -> throw UnauthorizedResponse("Token not found")
            roles.contains(Roles.USER) -> {
                    validarUsuario(token)
                    handler.handle(ctx)
            }
        }
    }

}
