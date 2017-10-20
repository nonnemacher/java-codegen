package com.github.nonnemacher.codegen.core;

import java.util.List;

/**
 * @author Carlos Henrique Nonnemacher
 * @version 1.0.0.0
 */
public class Dao {

	public static final String TEMPLATE = "/dao.template";

	private String nome;
	private List<Operacao> operacaoes;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Operacao> getOperacaoes() {
		return operacaoes;
	}

	public void setOperacaoes(List<Operacao> operacaoes) {
		this.operacaoes = operacaoes;
	}

}
