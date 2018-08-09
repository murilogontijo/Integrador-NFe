package br.com.oobj.integrador.model;

import java.util.Date;

public class NotaFiscal {

	private Long id;
	private String nomeArquivo;
	private String conteudoArquivo;
	// java.util.Date (NAO EH O SQL!)
	private Date dataHoraEmissao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getConteudoArquivo() {
		return conteudoArquivo;
	}

	public void setConteudoArquivo(String conteudoArquivo) {
		this.conteudoArquivo = conteudoArquivo;
	}

	public Date getDataHoraEmissao() {
		return dataHoraEmissao;
	}

	public void setDataHoraEmissao(Date dataHoraEmissao) {
		this.dataHoraEmissao = dataHoraEmissao;
	}
	
	@Override
	public String toString() {
		return "[nome: " + nomeArquivo + 
				"; data/hora emissao: " + dataHoraEmissao +
				"; conteudo: " + conteudoArquivo + "]";
	}

}
