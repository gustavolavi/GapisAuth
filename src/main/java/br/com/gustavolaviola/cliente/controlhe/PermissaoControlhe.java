package br.com.gustavolaviola.cliente.controlhe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gustavolaviola.cliente.modelo.Permissao;
import br.com.gustavolaviola.cliente.repositorio.PermissaoRepositorio;

@RestController
@RequestMapping("/permissao")
public class PermissaoControlhe {

	@Autowired
	private PermissaoRepositorio permissaoRepositorio;
	
	@GetMapping
	public List<Permissao> get(){
		return (List<Permissao>) permissaoRepositorio.findAll();
	}
}
