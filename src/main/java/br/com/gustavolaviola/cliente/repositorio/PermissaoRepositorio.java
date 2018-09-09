package br.com.gustavolaviola.cliente.repositorio;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.gustavolaviola.cliente.modelo.Permissao;

public interface PermissaoRepositorio extends CrudRepository<Permissao, Long> {
	
	@Query(value = "SELECT p.id, p.nome as nome_permissao FROM usuario_permissao up"
			+ " JOIN usuario u ON u.id = up.usuario_id"
			+ " JOIN permissao p ON p.id = up.permissao_id"
			+ " WHERE u.login = :login", nativeQuery = true)
	public Collection<Permissao> getPermicaoPorUsuairo(@Param("login") String login);
	
	@Query(value = "SELECT p.id, p.nome FROM grupo_permissao gp"
			+ " JOIN grupo g ON g.id = gp.grupo_id"
			+ " JOIN permissao p ON p.id = gp.permissao_id"
			+ " JOIN usuario_grupo ug ON ug.grupo_id = g.id"
			+ " JOIN usuario u ON u.id = ug.usuario_id"
			+ " WHERE u.login = :login", nativeQuery = true)
	public Collection<Permissao> getPermissaoPorGrupoDoUduario(@Param("login") String login);
}
