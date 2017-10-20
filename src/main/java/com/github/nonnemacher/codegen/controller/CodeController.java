package com.github.nonnemacher.codegen.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import com.github.nonnemacher.codegen.core.Helper;
import com.github.nonnemacher.codegen.core.Local;
import com.github.nonnemacher.codegen.core.Operacao;
import com.github.nonnemacher.codegen.core.Servico;
import com.github.nonnemacher.codegen.dto.Operation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * @author Carlos Henrique Nonnemacher
 * @version 1.0.0.0
 */
public class CodeController implements Initializable {

	private static final Logger LOGGER = Logger.getLogger(CodeController.class.getName());

	public static final String VIEW = "/fxml/Code.fxml";

	@FXML
	private TextField tfNomeDoServico;

	@FXML
	private TextField tfNamespace;

	@FXML
	private TextField tfPacote;

	@FXML
	private TextField tfOperacao;

	@FXML
	private TextArea taServico;

	@FXML
	private TextArea taInterface;

	@FXML
	private TextArea taHelper;

	@FXML
	private TableView<Operation> operations;

	@FXML
	private TableColumn<Operation, String> tcNome;

	@FXML
	private TableColumn<Operation, String> tcEntrada;

	@FXML
	private TableColumn<Operation, String> tcSaida;

	@FXML
	private TableColumn<Button, Operation> tcButton;

	private final ObservableList<Operation> operationsData = FXCollections.observableArrayList();

	private Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		operations.setItems(operationsData);

		tcNome.setCellValueFactory(cellData -> cellData.getValue().getName());
		tcEntrada.setCellValueFactory(cellData -> cellData.getValue().getIn());
		tcSaida.setCellValueFactory(cellData -> cellData.getValue().getOut());

		operations.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				if (KeyCode.DELETE.equals(keyEvent.getCode())) {
					operationsData.removeAll(operations.getSelectionModel().getSelectedItems());
					operations.getSelectionModel().clearSelection();
					onKeyReleasedFields();
				}
			}
		});

		tfOperacao.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent keyEvent) {
				if (KeyCode.ENTER.equals(keyEvent.getCode())) {
					onKeyPressedAdicionar();
				}
			}
		});

	}

	public void onKeyReleasedFields() {

		Servico servico = getServicoDto();

		final Map<String, Object> map;
		map = new HashMap();
		map.put("autor", "Code Gen - Em desenvolvimento");
		map.put("versao", "0.0.0");
		map.put("servico", servico);

		String template;
		template = this.read(Servico.TEMPLATE);
		taServico.setText(this.parse(template, map));

		template = this.read(Local.TEMPLATE);
		taInterface.setText(this.parse(template, map));

		template = this.read(Helper.TEMPLATE);
		taHelper.setText(this.parse(template, map));

	}

	private Servico getServicoDto() {
		final List<Operacao> list;
		list = new ArrayList();
		operationsData.stream().map((operation) -> {
			Operacao operacao;
			operacao = new Operacao();
			operacao.setNome(operation.getName().get());
			operacao.setEntrada(operation.getIn().get());
			operacao.setSaida(operation.getOut().get());
			return operacao;
		}).forEach((operacao) -> {
			list.add(operacao);
		});
		final Local local;
		local = new Local();
		local.setNome(this.getText(tfNomeDoServico));
		local.setOperacaoes(list);
		final Helper helper;
		helper = new Helper();
		helper.setNome(this.getText(tfNomeDoServico));
		helper.setOperacoes(list);
		final Servico servico;
		servico = new Servico();
		servico.setNameSpace(this.getText(tfNamespace) == null ? this.getText(tfPacote) : this.getText(tfNamespace));
		servico.setServiceName(this.getText(tfNomeDoServico));
		servico.setNome(servico.getServiceName());
		servico.setPacote(this.getText(tfPacote));
		servico.setLocal(local);
		servico.setHelper(helper);
		servico.setOperacoes(list);
		return servico;
	}

	private String getText(final TextField textField) {
		return textField == null || textField.getText().isEmpty() ? null : textField.getText();
	}

	private void setText(final TextField textField, final String string) {
		textField.setText(string);
	}

	public void onKeyPressedAdicionar() {
		if (this.getText(tfOperacao) != null) {
			final Operation operation;
			operation = new Operation(this.getText(tfOperacao));
			operationsData.add(operation);
			this.setText(tfOperacao, null);
			this.onKeyReleasedFields();
		}
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
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public void onKeyPressedInTable() {
		LOGGER.info("ok");
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void onClickSave() throws IOException {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Resource File");
//        fileChooser.getExtensionFilters().addAll(
//                new ExtensionFilter("Text Files", "*.txt"),
//                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
//                new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
//                new ExtensionFilter("All Files", "*.*"));
//
//        File selectedFile = fileChooser.showOpenDialog(stage);
		final DirectoryChooser directoryChooser;
		directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("JavaFX Projects");
		File selectedDirectory = directoryChooser.showDialog(stage);

		if (selectedDirectory != null) {

			final Servico servico;
			servico = this.getServicoDto();

			FileUtils.writeStringToFile(new File(FilenameUtils.concat(selectedDirectory.getAbsolutePath(), servico.getNome() + "Service.java")), taServico.getText());
			FileUtils.writeStringToFile(new File(FilenameUtils.concat(selectedDirectory.getAbsolutePath(), servico.getHelper().getNome() + "Helper.java")), taHelper.getText());
			FileUtils.writeStringToFile(new File(FilenameUtils.concat(selectedDirectory.getAbsolutePath(), servico.getLocal().getNome() + "Local.java")), taInterface.getText());

			final Map<String, Object> map;
			map = new HashMap();
			map.put("autor", "Code Gen - Em desenvolvimento");
			map.put("packageName", servico.getPacote());
			map.put("versao", "0.0.0");

			for (Operacao operacao : servico.getOperacoes()) {
				String template = this.read(Operacao.TEMPLATE_IN);
				map.put("operacao", operacao);
				FileUtils.writeStringToFile(new File(FilenameUtils.concat(selectedDirectory.getAbsolutePath(), operacao.getEntrada() + ".java")), this.parse(template, map));
				template = this.read(Operacao.TEMPLATE_OUT);
				FileUtils.writeStringToFile(new File(FilenameUtils.concat(selectedDirectory.getAbsolutePath(), operacao.getSaida() + ".java")), this.parse(template, map));
			}

		}

	}

}
