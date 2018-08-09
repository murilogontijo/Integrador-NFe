package br.com.oobj.integrador.dao;

import java.util.List;

import br.com.oobj.integrador.model.NotaFiscal;

public interface NotaFiscalDAO {
	
	// inserir
	void inserirNotaFiscal(NotaFiscal nota);
	
	// contar
	int contar();
	
	// listar todas as notas...
	List<NotaFiscal> listarTodas();
	
	// remover uma nota
	int removerNota(Long id);
	
	// atualizar
	int atualizar(NotaFiscal notaFiscal);
	
	// buscar pelo ID
	NotaFiscal buscarPeloId(Long id);

}
