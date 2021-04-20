package ar.edu.unq.ui.desktop.transformers

import org.apache.commons.collections15.Transformer
import java.awt.Color

class ColorStateTransformer() : Transformer<Boolean, Color> {

    override fun transform(p0: Boolean): Color {
        var ret = Color(200,90,72)
        if(p0) {
            ret= Color(72,156,57)
        }
        return ret
    }

}

class TextStateTransformer(): Transformer<Boolean, String> {
    override fun transform(p0: Boolean): String {
        var ret = "No Disponible"
            if(p0) {
                ret= "Disponible"
            }
        return ret
    }


}