package br.com.oobj.integrador.origem.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.com.oobj.integrador.model.NotaFiscal;
import br.com.oobj.integrador.origem.Origem;

public class LeitorDiretorio implements Origem {
	
	private String path;
	private String pathProcessados;
	
	public LeitorDiretorio(String path) {
		this.path = path;
	}
	
	@Override
	public List<NotaFiscal> obterNotas() {
		System.out.println("Obtendo as notas do diretorio: " + path);
		
		List<NotaFiscal> listaDeNotas = new ArrayList<>();
		
		File diretorio = new File(path);
		
		for (File arquivoEncontrado : diretorio.listFiles()) {
			String nomeArquivo = arquivoEncontrado.getName();
			
			System.out.println("Arquivo encontrado: " + nomeArquivo);
			
			NotaFiscal nota = new NotaFiscal();
			nota.setNomeArquivo(nomeArquivo);
			
			// Usando try-with-resources (Java 7+), onde o proprio Java dá o close() automatico
			try (FileInputStream fis = new FileInputStream(arquivoEncontrado)) {
				nota.setConteudoArquivo(IOUtils.toString(fis, "UTF-8"));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// Usando commons-io
//				IOUtils.closeQuietly(fis);
				
				// Usando API nativa do Java.
//				try {
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
			
			listaDeNotas.add(nota);
	
			moverArquivoParaProcessados(arquivoEncontrado);
		}
		
		return listaDeNotas;
	}

	private void moverArquivoParaProcessados(File arquivoEncontrado) {
		File parentFile = arquivoEncontrado.getParentFile().getParentFile();
		System.out.println("getParent() retorna " + parentFile.getAbsolutePath());
		
		File diretorioProcessados = new File(parentFile.getAbsolutePath(), "processados");
		
		System.out.println("Objeto diretorioProcessados " + diretorioProcessados.getAbsolutePath());
		
		if (!diretorioProcessados.exists()) {
			diretorioProcessados.mkdir();
		}
		
		try {
			FileUtils.moveFileToDirectory(arquivoEncontrado, diretorioProcessados, true);
		} catch (IOException e) {
			System.err.println("Falha ao mover o arquivo para o diretorio de processados " 
								+ e.getMessage());
			arquivoEncontrado.delete();
		}
		
	}
	
	



	

}
