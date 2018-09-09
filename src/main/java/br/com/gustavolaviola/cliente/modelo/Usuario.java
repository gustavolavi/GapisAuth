package br.com.gustavolaviola.cliente.modelo;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import org.springframework.security.core.userdetails.UserDetails;

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
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.ativo = ativo;
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

}
