package ar.edu.unq.ui.desktop.appModel

import domain.Content
import org.uqbar.commons.model.annotations.Observable

@Observable
open class ContentAppModel(val c: Content?) {
 var id: String = ""
 var title: String = ""
 var description: String = ""
 var poster: String = ""
 var state: Boolean = false
 init{
  if(c != null){
  id = c.id
  title = c.title
  description = c.description
  poster = c.poster
  }
 }
}
