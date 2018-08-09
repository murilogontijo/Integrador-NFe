package br.com.oobj.integrador.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.model.NotaFiscal;

@Repository
public class NotaFiscalNamedSpringJDBCDAO implements NotaFiscalDAO {
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public NotaFiscalNamedSpringJDBCDAO(DataSource dataSource) {
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
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
	
	
	@Override
	public void inserirNotaFiscal(NotaFiscal nota) {
		String sqlInsert = "insert into nfe (nome, conteudo, data_hora_emissao) values (:nomeArquivo, :conteudoArquivo, :dataHoraEmissao)";
		
		//nome, conteudo, data_hora_emissao
//		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//		
//		namedParameters.addValue("nome", nota.getNomeArquivo()); 
//		namedParameters.addValue("conteudo", nota.getConteudoArquivo());
//		namedParameters.addValue("data", nota.getDataHoraEmissao());
		
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(nota);
			
		int linhasAfetadas = namedJdbcTemplate.update(sqlInsert, namedParameters);
		
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
		String sqlUpdate = "update nfe set nome = :nomeArquivo, conteudo = :conteudoArquivo, data_hora_emissao = :dataHoraEmissao where id = :id";
		
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(notaFiscal);
		int linhasAtualizadas = namedJdbcTemplate.update(sqlUpdate, namedParameters);

		return linhasAtualizadas;
	}

	@Override
	public NotaFiscal buscarPeloId(Long id) {
		String sqlSelectId = "select * from nfe where id = :id";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("id",id);
		
		return namedJdbcTemplate.queryForObject(sqlSelectId, namedParameters, new NotaFiscalRowMapper());
	}

}
