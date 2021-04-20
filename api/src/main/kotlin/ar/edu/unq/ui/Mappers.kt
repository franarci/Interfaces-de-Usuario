package ar.edu.unq.ui

import domain.*

data class UserViewMapper(val id: String, val name: String, val email: String)
data class UserRegisterMapper(val name: String? = null,
                              val email: String? = null,
                              val password: String? = null,
                              val image: String? = null,
                              val creditCard: String? = null)
data class UserContentViewMapper(val name: String,
                                 val image:String,
                                 val favourites: MutableCollection<ContentViewMapper>,
                                 val lastSeen: MutableCollection<ContentViewMapper>)

data class UserLoginMapper(val email: String?,val password: String?)

data class  SerieViewMapper(val id: String, val title:String, val description:String, val poster: String, val relatedContent: List<ContentViewMapper>, val seasons: List<Season>)
data class MovieViewMapper(val id: String, val title:String, val description:String, val poster: String, val video: String, val duration: Int, val actors: List<String>, val directors:  List<String>, val categories: List<Category>, var relatedContent: List<ContentViewMapper> )
data class IdMapper(val id: String)

class ContentViewMapper(val id: String, val description: String, val title: String,  state: ContentState, val poster: String){
    val state = stateToBool(state)
    fun stateToBool(state: ContentState): Boolean{
        return state::class.java== Available()::class.java
    }
}
