package dao;

import connection.ConnectionFactory;
import exception.BancoDeDadosException;
import model.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAOImpl implements AutorDAO {

	@Override
	public void salvar(Autor autor) {
		
		String sql = "INSERT INTO autor (nome, nacionalidade) VALUES (?, ?)";
		
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setString(1, autor.getNome());
			stmt.setString(2, autor.getNacionalidade());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new BancoDeDadosException("Erro ao salvar autor!", e);
		}
	}
	
	@Override
	public Autor buscarPorId(int id) {
		
		String sql = "SELECT * FROM autor WHERE id = ?";
		Autor autor = null;
		
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					autor = new Autor();
					autor.setId(rs.getInt("id"));
					autor.setNome(rs.getString("nome"));
					autor.setNacionalidade(rs.getString("nacionalidade"));
				}
			}
			
		} catch (SQLException e) {
			throw new BancoDeDadosException("Erro ao buscar autor por ID!", e);
		}
		
		return autor;
	}
	
	@Override
	public List<Autor> listarTodos() {
		
		String sql = "SELECT * FROM autor";
		List<Autor> autores = new ArrayList<>();
		
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			
			while (rs.next()) {
				Autor autor = new Autor();
				autor.setId(rs.getInt("id"));
				autor.setNome(rs.getString("nome"));
				autor.setNacionalidade(rs.getString("nacionalidade"));
				
				autores.add(autor);
			}
			
		} catch (SQLException e) {
			throw new BancoDeDadosException("Erro ao listar autores!", e);
		}
		
		return autores;
	}
	
	@Override
	public void atualizar(Autor autor) {
		
		String sql = "UPDATE autor SET nome = ?, nacionalidade = ? WHERE id = ?";
		
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setString(1, autor.getNome());
			stmt.setString(2, autor.getNacionalidade());
			stmt.setInt(3, autor.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new BancoDeDadosException("Erro ao atualizar autor!", e);
		}
	}
	
	@Override
	public void deletar(int id) {
		
		String sql = "DELETE FROM autor WHERE id = ?";
		
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new BancoDeDadosException("Erro ao deletar autor!", e);
		}
	}
}
