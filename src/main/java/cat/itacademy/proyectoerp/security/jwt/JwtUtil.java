package cat.itacademy.proyectoerp.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private int expiration;
	
	
	/**
	 * Method for generate token.
	 * Mehtod call doGenerateToken for create de token
	 * 
	 * @param userDetails  User for generate token
	 * @return String Token
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	public String getNameOfUser(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
	//Some informatiion from token  will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public boolean validateToken(String token) {
		
		try {

			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
        	      
        } catch (ExpiredJwtException e) {
            System.out.println("Tiempo expirado del token : "+token+" Fallo : "+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("No soportado : "+token+" Fallo : "+ e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Inválido : "+token+" Fallo : "+ e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Vacio o nulo: "+token+" Fallo : "+ e.getMessage());
        } 
  

		return false;
	}
	
	
	
	/**
	 * Method for create token
	 * 
	 * subject is de username
	 * Algorithm HS512 and secret key
	 * 
	 * 
	 * @param claims
	 * @param subject
	 * @return Token created
	 */
	 private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
					.signWith(SignatureAlgorithm.HS512, secret).compact();
		}

	 public void setSecret(String value) {
	        this.secret = value;
	    }	

}
