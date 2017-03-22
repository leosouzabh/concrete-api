package br.concrete.api.config.auth;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.concrete.api.exception.SessaoInvalidaException;
import br.concrete.api.exception.TokenInexistenteException;
import br.concrete.api.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration.token}")
    private Integer tempoExpiracao;

    public Usuario parseToken(String token) throws SessaoInvalidaException, TokenInexistenteException {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            	
            Usuario u = new Usuario()
            	.setEmail(body.getSubject())
            	.setId((String) body.get("userId"));

            return u;
            
        } catch (ExpiredJwtException e1){
        	throw new SessaoInvalidaException();
        	
        } catch (MalformedJwtException | SignatureException e1){
        	throw new TokenInexistenteException();

        } catch (JwtException | ClassCastException e) {
        	throw new TokenInexistenteException();
        }
    }


    public String generateToken(Usuario u) {
        Claims claims = Jwts.claims().setSubject(u.getEmail());
        claims.put("userId", u.getId());

        
        
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration( this.getExpirationDate() )
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    
    private Date getExpirationDate(){
    	Calendar cl = Calendar.getInstance();
    	cl.add(Calendar.MINUTE, tempoExpiracao);
    	return cl.getTime();
    }
}