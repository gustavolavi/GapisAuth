package br.com.gustavolaviola.cliente.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import static br.com.gustavolaviola.cliente.config.SegurancaConstantes.*;

import br.com.gustavolaviola.cliente.servicos.UsuarioAuthenticacaoServicos;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	private final UsuarioAuthenticacaoServicos usuarioAuthenticacaoServicos;
	
	@Autowired
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,UsuarioAuthenticacaoServicos usuarioAuthenticacaoServicos) {
		super(authenticationManager);
		this.usuarioAuthenticacaoServicos = usuarioAuthenticacaoServicos;				
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(HEADER_STRING);
		
		if(header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToker(request);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthenticationToker(HttpServletRequest request) {
		String token =  request.getHeader(HEADER_STRING);
		if(token == null) return null;
		
		String login = Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody().
				getSubject();
				
		UserDetails userDetails = usuarioAuthenticacaoServicos.loadUserByUsername(login);
		
		return login!= null? new UsernamePasswordAuthenticationToken(login, null,userDetails.getAuthorities()): null;
	}

}
