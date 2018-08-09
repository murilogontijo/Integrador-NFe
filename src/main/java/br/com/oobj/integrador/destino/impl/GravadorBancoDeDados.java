package br.com.oobj.integrador.destino.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.destino.Destino;
import br.com.oobj.integrador.model.NotaFiscal;

public class GravadorBancoDeDados implements Destino {
	
	private NotaFiscalDAO notaFiscalDao;
	
	public GravadorBancoDeDados(NotaFiscalDAO notaFiscalDao) {
		this.notaFiscalDao = notaFiscalDao;
	}
	
	@Override
	public void escreveRegistros(List<NotaFiscal> notas) {
		System.out.println("Gravando registros no banco de dados...");
		
		for (NotaFiscal nota : notas) {
			System.out.println("Fazendo INSERT no banco para nota " + nota.getNomeArquivo());
			
			notaFiscalDao.inserirNotaFiscal(nota);
		}
	}

}
