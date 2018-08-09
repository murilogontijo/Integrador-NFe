package br.com.oobj.integrador;

import java.util.List;

import br.com.oobj.integrador.destino.Destino;
import br.com.oobj.integrador.model.NotaFiscal;
import br.com.oobj.integrador.origem.Origem;

public class IntegradorNotaFiscal {
	
	private Origem origem;
	private Destino destino;
	
	public void setOrigem(Origem origem) {
		this.origem = origem;
	}
	
	public void setDestino(Destino destino) {
		this.destino = destino;
	}
	
	public void integraNotaFiscal() {
		System.out.println("Iniciando integracao de NF...");
		
		destino.escreveRegistros(origem.obterNotas());
		
		System.out.println("Integracao de NF finalizada.");
	}

}
