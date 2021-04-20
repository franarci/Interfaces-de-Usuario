package ar.edu.unq.ui

import ar.edu.unq.ui.controllers.ContentController
import ar.edu.unq.ui.controllers.UserController
import data.getUNQFlix
import domain.*
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.Role
import io.javalin.core.util.RouteOverviewPlugin


enum class Roles: Role {
    ANYONE,USER
}

    fun main() {
        val back = getUNQFlix()
        val tokenJWT = TokenJWT()
        val userController = UserController(back, tokenJWT)
        val contentController = ContentController(back, tokenJWT)
        val accessManager = JWTAccessManager(tokenJWT, back.users)
        val app = Javalin.create {
            it.accessManager(accessManager)
            it.defaultContentType = "application/json"
            it.registerPlugin(RouteOverviewPlugin("/routes"))
            it.enableCorsForAllOrigins()
        }
        app.before {
            it.header("Access-Control-Expose-Headers", "*")
            it.header("access-control-allow-origin", "*")
        }
        app.start(7000)

        app.routes {
            path("/users") {
                get(userController::getUsers, mutableSetOf<Role>(Roles.ANYONE))
            }
            //POST/register
            path("/register") {
                post(userController::register, mutableSetOf<Role>(Roles.ANYONE))
            }

            //POST/login
            path("/login"){
               post(userController::login, mutableSetOf<Role>(Roles.ANYONE))
           }

            //GET/user
            path("/user"){
                get(userController::getUser, mutableSetOf<Role>(Roles.USER))
                //POST /user/lastSeen
                path("/lastSeen") {
                    post(userController::addLastSeen, mutableSetOf<Role>(Roles.USER))
                }
                //POST/user/fav/{:contentId}
                path("/fav/:contentId"){
                    post(userController::favoritos, mutableSetOf<Role>(Roles.USER))
                }
            }

                //GET /content
                path("/content") {
                    get(contentController::getContent, mutableSetOf<Role>(Roles.USER))
                    //GET /content/:id
                path(":contentId") {
                    get(contentController::getContentByID, mutableSetOf<Role>(Roles.USER))
                }
            }
            //GET/search?text={text}
            path("/search"){
                get(contentController::search, mutableSetOf<Role>(Roles.USER))
            }

                //GET /banners
                path("/banners") {
                    get(contentController::getBanners, mutableSetOf<Role>(Roles.USER))
                }
        }
    }




