package br.com.gustavolaviola.cliente.controlhe;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<Object> get() {
		return ResponseEntity.ok(usuarioRepositorio.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> get(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioRepositorio.findById(id));
	}

	@GetMapping("/login/{login}")
	public ResponseEntity<Object> get(@PathVariable String login) {
		return ResponseEntity.ok(usuarioRepositorio.findByLogin(login));
	}

	@PostMapping("/cadastro")
	public ResponseEntity<Object> post(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(usuarioRepositorio.save(usuario));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> put(@RequestBody Usuario usuario, @PathVariable Long id) {

		Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(id);

		if (!usuarioOptional.isPresent())
			return ResponseEntity.notFound().build();

		usuario.setId(id);

		return ResponseEntity.ok(usuarioRepositorio.save(usuario));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<Usuario> usuario = usuarioRepositorio.findById(id);
		if (!usuario.isPresent())
			return ResponseEntity.notFound().build();

		usuarioRepositorio.delete(usuario.get());

		return ResponseEntity.ok(usuario.get());
	}
}
