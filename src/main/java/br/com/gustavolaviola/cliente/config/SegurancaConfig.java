package br.com.gustavolaviola.cliente.config;

import static br.com.gustavolaviola.cliente.config.SegurancaConstantes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.gustavolaviola.cliente.servicos.UsuarioAuthenticacaoServicos;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SegurancaConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioAuthenticacaoServicos usuarioAuthenticacaoServicos;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.GET,SING_UP_URL).permitAll()
		.antMatchers("/usuario/**").hasRole("USER")
		.antMatchers("/permissao/**").hasRole("ADMIN")
		.and()
	 	.sessionManagement()
     	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JWTAtuhenticationFilter(authenticationManager()))
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), usuarioAuthenticacaoServicos));
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
	 		.userDetailsService(usuarioAuthenticacaoServicos)
			.passwordEncoder(new BCryptPasswordEncoder());
	}

}