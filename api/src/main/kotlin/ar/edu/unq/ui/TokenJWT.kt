package ar.edu.unq.ui

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import data.idGenerator
import domain.User
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider



class TokenJWT {
   val algorithm = Algorithm.HMAC256("very_secret");
   val generator = UserGenerator()
   val verifier = JWT.require(algorithm).build();
   val provider = JWTProvider(algorithm, generator, verifier)

    fun generateToken(user: User): String{
    return provider.generateToken(user)
    }

    fun validate(token: String): String{
        val token = provider.validateToken(token)
        if(!token.isPresent) throw NotFoundToken()
       return token.get().getClaim("name").asString()
    }

}
class UserGenerator : JWTGenerator<User>{
    override fun generate(user: User?, algorithm: Algorithm?): String {
        val token = JWT.create().withClaim("name","u_${idGenerator.currentUserId}" )
        return token.sign(algorithm);
    }
}


class NotFoundToken : Throwable() {

}
