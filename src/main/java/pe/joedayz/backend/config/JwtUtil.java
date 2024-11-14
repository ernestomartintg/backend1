package pe.joedayz.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/*
    Validez del Token
    Token no duren mucho

 */
public class JwtUtil {
    private static final long servialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY=5*60*60; //5 Horas = 18,000 segundos

    @Value("${jwt.secret}")
    private String secret;

    // 3 metodos
    /*
        Subject
        Issue
        cuando fue generado el token, cuando expira
     */

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        // Crear una clave secreta usando el valor de la clave secreta
        Key key= Keys.hmacShaKeyFor(secret.getBytes());

        //Usar el JWTParserBuilder en lugar de parser() directamente
        return Jwts.parser()
                .setSigningKey(key) //Establezco clave secreta
                .build() //Construir el parser
                .parseClaimsJws(token) //Parsear el token
                .getBody(); // Obtener las Claims
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private boolean ignoreExpirationDate(String token) {
        return false;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        return Jwts.builder().setClaims(claims).setSubject(secret)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*100000))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    private boolean canTokenBeRefreshed(String token){
        return !isTokenExpired(token) || ignoreExpirationDate(token);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
