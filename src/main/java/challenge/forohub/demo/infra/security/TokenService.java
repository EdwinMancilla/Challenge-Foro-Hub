package challenge.forohub.demo.infra.security;

import challenge.forohub.demo.domain.usuarios.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${forohub.security.token.secret}") //@Value es de org.springframework.beans.factory.annotation.Value
    private String secret;

    //METODO PARA GENERAR EL TOKEN JWT
    public String generarToken(Usuario usuario){


        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("forohub")
                    .withSubject(usuario.getEmail()) // se usa el email del usuario como sujeto del token
                    .withExpiresAt(fechaExpiracion())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }


    //METODO PARA ESTABLECER LA FECHA DE EXPIRACION DEL TOKEN
    private Instant fechaExpiracion() {
        return LocalDateTime.now().plusHours(7).toInstant(ZoneOffset.of("-06:00"));
    }


    //METODO PARA OBTENER EL SUJETO DEL TOKEN JWT
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("forohub")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT invalido o expirado");
        }
    }


}
