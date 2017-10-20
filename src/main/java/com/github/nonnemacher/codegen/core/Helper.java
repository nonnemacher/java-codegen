package com.github.nonnemacher.codegen.core;

import java.util.List;

/**
 * @author Carlos Henrique Nonnemacher
 * @version 1.0.0.0
 */
public class Helper {

	public static final String TEMPLATE = "/helper.template";

	private String nome;
	private List<Operacao> operacoes;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Operacao> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(List<Operacao> operacoes) {
		this.operacoes = operacoes;
	}

	@Override
	public String toString() {
		return this.nome;
	}

}
