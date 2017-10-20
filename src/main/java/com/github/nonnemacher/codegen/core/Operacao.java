package com.github.nonnemacher.codegen.core;

import com.sun.xml.internal.ws.util.StringUtils;

/**
 * @author Carlos Henrique Nonnemacher
 * @version 1.0.0.0
 */
public class Operacao {

	public static final String TEMPLATE_IN = "/operacaoIn.template";
	public static final String TEMPLATE_OUT = "/operacaoOut.template";

	private String entrada;
	private String nome;
	private String saida;

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public String getEntradaCase() {
		return "in" + StringUtils.capitalize(nome);
	}

	public String getSaidaCase() {
		return "out" + StringUtils.capitalize(nome);
	}

}
