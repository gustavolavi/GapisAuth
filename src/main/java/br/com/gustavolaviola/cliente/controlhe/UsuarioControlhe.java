package br.com.gustavolaviola.cliente.controlhe;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavolaviola.cliente.modelo.Usuario;
import br.com.gustavolaviola.cliente.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlhe {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@GetMapping()
	public List<Usuario> get() {
		return (List<Usuario>) usuarioRepositorio.findAll();
	}
}
