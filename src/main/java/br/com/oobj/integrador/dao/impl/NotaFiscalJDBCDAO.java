package br.com.oobj.integrador.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.oobj.integrador.dao.NotaFiscalDAO;
import br.com.oobj.integrador.model.NotaFiscal;

public class NotaFiscalJDBCDAO implements NotaFiscalDAO {
	
	private DataSource dataSource;
	
	public NotaFiscalJDBCDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void inserirNotaFiscal(NotaFiscal nota) {
		try (Connection connection = this.dataSource.getConnection()) {
			// Crio a query SQL de INSERT
			// insert into nfe (nome, conteudo) values (?, ?)
			// O '?' significa wildcard, ou seja, um valor que eu vou substituir depois...
			String sqlInsert = "insert into nfe (nome, conteudo) values (?, ?)";
			
			// Pe�o a Connection que crie um PreparedStatement, com a query...
			PreparedStatement psInsert = connection.prepareStatement(sqlInsert);
			
			// Substituo os '?' pelo valores reais que serao mandados pro banco.
			psInsert.setString(1, nota.getNomeArquivo());
			psInsert.setString(2, nota.getConteudoArquivo());
			
			// Mando o Java executar a query e salvo em uma variavel.
			int linhasAfetadas = psInsert.executeUpdate();
			System.out.println("Linhas afetadas: " + linhasAfetadas);
		} catch (SQLException e) {
			System.err.println("Falha ao realizar o SQL INSERT");
		}

	}
	
	@Override
	public int contar() {
		int quantidadeTotal = 0;
		
		String sqlCount = "select count(*) from nfe";
		
		try (Connection connection = this.dataSource.getConnection()) {
			// rodar a query
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlCount);
			
			// pula o cursor do ResultSet pra primeira posicao
			resultSet.next();
			
			// retorna o conteudo da primeira coluna
			quantidadeTotal = resultSet.getInt(1);
		} catch (SQLException e) {
			System.err.println("Falha ao contar a quantidade de notas no banco: " + e.getMessage());
		}
		
		return quantidadeTotal;
	}

	@Override
	public List<NotaFiscal> listarTodas() {
		List<NotaFiscal> todasAsNotas = new ArrayList<>();
		
		String sqlSelect = "select * from nfe";
		
		try (Connection connection = this.dataSource.getConnection()) {
			
			// rodar a query
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlSelect);
			
			// iterar sobre os resultados
			while (resultSet.next()) {
				// montar objeto do tipo NotaFiscal com base no ResultSet...
				Long id = resultSet.getLong("id");
				String nome = resultSet.getString("nome");
				String conteudo = resultSet.getString("conteudo");
				
				NotaFiscal nf = new NotaFiscal();
				nf.setId(resultSet.getLong("id"));
				nf.setNomeArquivo(nome);
				nf.setConteudoArquivo(conteudo);
				
				// adicionar em uma lista
				todasAsNotas.add(nf);
			}
		} catch (SQLException e) {
			System.err.println("Falha ao listar todas as notas do banco" + e.getMessage());
		}
		
		System.out.println("Tamanho da lista: " + todasAsNotas.size());
		
		// retornar a lista
		return todasAsNotas;
	}

	@Override
	public int removerNota(Long id) {
		int quantidadeDeRegistrosAlterados = 0;
		
		// cria a query de SQL DELETE (COM WHERE!)
		String sqlDelete = "delete from nfe where id = ?";
		
		// abrir a conexao
		try (Connection connection = this.dataSource.getConnection()) {
			PreparedStatement psDelete = connection.prepareStatement(sqlDelete);
			
			// substituir o ? pelo valor de 'id'
			psDelete.setLong(1, id);
			
			// executa e atribui a quantidade de registros alterados
			quantidadeDeRegistrosAlterados = psDelete.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Falha ao deletar a nota fiscal " + id + " do banco: " + e.getMessage());
		}
		
		// retornar a quantidade de registros alterados
		return quantidadeDeRegistrosAlterados;
	}

	@Override
	public int atualizar(NotaFiscal notaFiscal) {
		// criar uma instancia local de NotaFiscal
		int linhasAfetadas = 0;
		
		// checar se a nota de ID existe na base?
		NotaFiscal notaFiscalNoBanco = buscarPeloId(notaFiscal.getId());
		if (notaFiscalNoBanco != null) {
			// se existir, rodar a query no banco
			// criar conexao
			try (Connection connection = this.dataSource.getConnection()) {
				// definir a query SQL UPDATE
				String sqlUpdate = "update nfe set nome = ?, conteudo = ? where id = ?";
				
				// criar o PreparedStatement
				PreparedStatement psUpdate = connection.prepareStatement(sqlUpdate);
				
				// substituir os valores de ?
				psUpdate.setString(1, notaFiscal.getNomeArquivo());
				psUpdate.setString(2, notaFiscal.getConteudoArquivo());
				psUpdate.setLong(3, notaFiscal.getId());
				
				// executa a query
				linhasAfetadas = psUpdate.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Falha ao atualizar a nota fiscal na base " + e.getMessage());
			}
		} else {
			// se nao existir, printar um log dizendo que nao existe...
			System.err.println("A nota a ser atualizada nao existe na base");
		}
		// retornar a instancia local de NotaFiscal
		return linhasAfetadas;
	}

	@Override
	public NotaFiscal buscarPeloId(Long id) {
		NotaFiscal notaFiscal = null;
		
		// definir a query SQL SELECT
		String sqlSelect = "select id, nome, conteudo, data_hora_emissao from nfe where id = ?";
		
		// abrir a conexao
		try (Connection connection = this.dataSource.getConnection()) {
			// criar o PreparedStatment
			PreparedStatement psSelect = connection.prepareStatement(sqlSelect);
			
			// passar o ID
			psSelect.setLong(1,  id);
			
			// executar
			ResultSet resultSet = psSelect.executeQuery();
			
			// checa se existe registro
			if (resultSet.next()) {
				// se existir, cria uma nova instancia de NotaFiscal
				notaFiscal = new NotaFiscal();
				
				// popular a instancia com os valores do banco...
				notaFiscal.setId(resultSet.getLong("id"));
				notaFiscal.setNomeArquivo(resultSet.getString("nome"));
				notaFiscal.setConteudoArquivo(resultSet.getString("conteudo"));
				notaFiscal.setDataHoraEmissao(resultSet.getTimestamp("data_hora_emissao"));
			}
			// se nao existir, retorna nulo
		} catch (SQLException e) {
			System.err.println("Falha ao consular pelo ID: " + e.getMessage());
		}
		// retorna instancia de NotaFiscal
		return notaFiscal;
	}

	

}
