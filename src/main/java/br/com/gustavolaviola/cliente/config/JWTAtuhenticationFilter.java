package br.com.gustavolaviola.cliente.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import static br.com.gustavolaviola.cliente.config.SegurancaConstantes.*;

import br.com.gustavolaviola.cliente.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAtuhenticationFilter extends  UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	@Autowired
	public JWTAtuhenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			
			return this.authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String token = Jwts.builder()
				.setSubject(((User) authResult.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		 
		response.addHeader(HEADER_STRING,TOKEN_PREFIX+token);

	}
}
