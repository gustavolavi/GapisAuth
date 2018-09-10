package br.com.gustavolaviola.cliente.modelo;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Usuario{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String login;
	private String senha;
	private boolean ativo;

	@ManyToMany
	@JoinTable(name = "usuario_permissao", joinColumns = { @JoinColumn(name = "usuario_id") }, inverseJoinColumns = {
			@JoinColumn(name = "permissao_id") })
	private Collection<Permissao> permissoes;

	public Usuario() {
	}

	public Usuario(String nome, String login, String senha, boolean ativo) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(senha);
		
		this.nome = nome;
		this.login = login;
		this.senha = hashedPassword;
		this.ativo = ativo;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public Collection<Permissao> getPermissoes() {
		return permissoes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public String getLogin() {
		return login;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setSenha(String senha) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(senha);
		
		this.senha = hashedPassword;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void setPermissoes(Collection<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	
}
