package br.com.gustavolaviola.cliente.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.gustavolaviola.cliente.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>  {
	
    Usuario findByLogin(String login);
    
}
