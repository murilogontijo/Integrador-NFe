package br.com.oobj.integrador.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.model.NotaFiscal;

public class NotaFiscalSpringJDBCDAO implements NotaFiscalDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public NotaFiscalSpringJDBCDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void inserirNotaFiscal(NotaFiscal nota) {
		String sqlInsert = "insert into nfe (nome, conteudo, data_hora_emissao) values (?, ?, ?)";
		
		//nome, conteudo, data_hora_emissao
		int linhasAfetadas = jdbcTemplate.update(sqlInsert, 
				nota.getNomeArquivo(), 
				nota.getConteudoArquivo(), 
				nota.getDataHoraEmissao());
		
		System.out.println("Linhas afetadas : " + linhasAfetadas);

	}

	@Override
	public int contar() {
		return jdbcTemplate.queryForObject("select count(*) from nfe", Integer.class);
	}

	@Override
	public List<NotaFiscal> listarTodas() {
		return jdbcTemplate.query("select * from nfe", new NotaFiscalRowMapper());
	}

	@Override
	public int removerNota(Long id) {
		
		
		return jdbcTemplate.update("delete from nfe where id = ?", id);
	
	}

	@Override
	public int atualizar(NotaFiscal notaFiscal) {
		
		return this.jdbcTemplate.update(
		        "update nfe set nome = ?, conteudo = ?, data_hora_emissao = ? where id = ?",
		        notaFiscal.getNomeArquivo(), 
		        notaFiscal.getConteudoArquivo(), 
		        notaFiscal.getDataHoraEmissao(), 
		        notaFiscal.getId());
	}

	@Override
	public NotaFiscal buscarPeloId(Long id) {
		return jdbcTemplate.queryForObject("select * from nfe where id = ?", 
				new Object[] { id }, new NotaFiscalRowMapper());
	}
	
	private static class NotaFiscalRowMapper implements RowMapper<NotaFiscal>{
		@Override
		public NotaFiscal mapRow(ResultSet resultSet, int rowNum) throws SQLException{
			NotaFiscal nf = new NotaFiscal();
			
			nf.setId(resultSet.getLong("id"));
			nf.setNomeArquivo(resultSet.getString("nome"));
			nf.setConteudoArquivo(resultSet.getString("conteudo"));
			nf.setDataHoraEmissao(resultSet.getTimestamp("data_hora_emissao"));
			
			return nf;
		}
		
	}

}
