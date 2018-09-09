package br.com.gustavolaviola.cliente.servicos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.gustavolaviola.cliente.modelo.Permissao;
import br.com.gustavolaviola.cliente.modelo.Usuario;
import br.com.gustavolaviola.cliente.repositorio.PermissaoRepositorio;
import br.com.gustavolaviola.cliente.repositorio.UsuarioRepositorio;

@Component
public class UsuarioAuthenticacaoServicos implements UserDetailsService {

	private final UsuarioRepositorio usuarioRepositorio;
	private final PermissaoRepositorio permissaoRepositorio;
	@Autowired
	public UsuarioAuthenticacaoServicos(UsuarioRepositorio usuarioRepositorio,PermissaoRepositorio permissaoRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
		this.permissaoRepositorio = permissaoRepositorio;
	}
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepositorio.findByLogin(login);
		
		System.out.println(usuario.getNome());
		
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.addAll(permissaoRepositorio.getPermicaoPorUsuairo(login).stream().map(role->{
			return new SimpleGrantedAuthority("ROLE_"+role.getNome());
		}).collect(Collectors.toList()));

		grantedAuthorities.addAll(permissaoRepositorio.getPermissaoPorGrupoDoUduario(login).stream().map(role->{
			return new SimpleGrantedAuthority("ROLE_"+role.getNome());
		}).collect(Collectors.toList()));


		UserDetails user = new org.springframework.security.core.userdetails.User(usuario.getLogin(),
				usuario.getSenha(), grantedAuthorities);
		return user;
	}

}
