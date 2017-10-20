package com.github.nonnemacher.codegen.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * @author Carlos Henrique Nonnemacher (CWI Software)
 * @version 1.0.0.0
 */
public class Core {

	private static final Logger LOGGER = Logger.getLogger(Core.class.getName());

	public Core() {
		initialize();
	}

	private void initialize() {
		final List<Operacao> list;
		list = new ArrayList();

		Operacao operacao;
		operacao = new Operacao();
		operacao.setNome("identificarProximoNivel");
		operacao.setEntrada("InIdentificarProximoNivel");
		operacao.setSaida("OutIdentificarProximoNivel");
		list.add(operacao);

		operacao = new Operacao();
		operacao.setNome("criarNivel");
		operacao.setEntrada("InCriarNivel");
		operacao.setSaida("OutCriarNivel");
		list.add(operacao);

		final Local local;
		local = new Local();
		local.setNome("LocalizarNivelLocal");
		local.setOperacaoes(list);

		final Helper helper;
		helper = new Helper();
		helper.setNome("LocalizarNivelHelper");
		helper.setOperacoes(list);

		final Servico servico;
		servico = new Servico();
		servico.setNameSpace("localizarnivel.ejb.alcadas.sicredi.com.br");
		servico.setNome("LocalizarNivelService");
		servico.setPacote("br.com.scredi.alcadas.ejb.localizarnivel");
		servico.setServiceName("LocalizarNivelService");
		servico.setLocal(local);
		servico.setHelper(helper);
		servico.setOperacoes(list);

		final Map<String, Object> map;
		map = new HashMap();

		map.put("autor", "Code Gen - Em desenvolvimento");
		map.put("versao", "0.0.0");
		map.put("servico", servico);

		String template;
		template = this.read(Servico.TEMPLATE);
		LOGGER.info(template);
		LOGGER.info(this.parse(template, map));
		template = this.read(Local.TEMPLATE);
		LOGGER.info(this.parse(template, map));
		template = this.read(Helper.TEMPLATE);
		LOGGER.info(this.parse(template, map));
	}

	/**
	 * Retorna o conteudo do arquivo em String.
	 *
	 * @param fileName
	 * @return toString
	 * @see retirar para um utilitário.
	 */
	public String read(final String fileName) {
		try {
			final InputStream inputStream;
			inputStream = this.getClass().getResourceAsStream(fileName);
			final String toString;
			toString = IOUtils.toString(inputStream, Charset.defaultCharset());
			inputStream.close();
			return toString;
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
		return null;
	}

	private String parse(final String template, final Map<String, Object> parameters) {
		try {
			final MustacheFactory mustacheFactory;
			mustacheFactory = new DefaultMustacheFactory();
			final File file;
			file = FileUtils.getFile("/tmp/service.output");
			final Writer writer;
			writer = new OutputStreamWriter(FileUtils.openOutputStream(file));
			final Mustache mustache;
			mustache = mustacheFactory.compile(new StringReader(template), "Service Template!!!");
			mustache.execute(writer, parameters);
			writer.flush();
			return FileUtils.readFileToString(file);
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static void main(String[] args) {
		Core core = new Core();
	}

}
