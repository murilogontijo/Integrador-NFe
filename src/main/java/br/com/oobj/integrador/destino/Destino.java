package br.com.oobj.integrador.destino;

import java.util.List;

import br.com.oobj.integrador.model.NotaFiscal;

public interface Destino {
	
	void escreveRegistros(List<NotaFiscal> notas);
	
}
