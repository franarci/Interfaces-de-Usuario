package ar.edu.unq.ui.desktop.appModel

import domain.Category
import org.eclipse.xtend.lib.annotations.Accessors
import org.uqbar.commons.model.annotations.Observable

@Observable
@Accessors
class CategoryAppModel(c: Category?) {
    var id = ""
    var name = ""

    init {
        if(c != null){
            id = c.id
            name = c.name
        }
    }


}