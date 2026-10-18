package dao;

import connection.ConnectionFactory;
import exception.BancoDeDadosException;
import exception.LivroNaoEncontradoException;
import model.Livro;
import model.Autor;
import validator.LivroValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class LivroDAOImpl  implements LivroDAO {

	@Override
	public void salvar(Livro livro) {
		
		String sql = "INSERT INTO livro (titulo, preco, ano_publicacao, autor_id) VALUES (?, ?, ?, ?)";
		
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setString(1, livro.getTitulo());
			stmt.setDouble(2, livro.getPreco());
			stmt.setInt(3, livro.getAnoPublicacao());
			stmt.setInt(4, livro.getAutor().getId());
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new BancoDeDadosException("Erro ao salvar livro!", e);
		}		
	}

	@Override
    public Livro buscarPorId(int id) {
        
		String sql = "SELECT l.*, a.nome AS autor_nome, a.nacionalidade AS autor_nacionalidade FROM livro l JOIN autor a ON l.autor_id = a.id WHERE l.id = ?";
		Livro livro = null;
		
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			
			stmt.setInt(1, id);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					livro = montarLivroComAutor(rs);
				}
			}
			
		} catch (SQLException e) {
			throw new BancoDeDadosException("Erro ao buscar livro por ID!", e);
		}
		
		if (livro == null) {
			throw new LivroNaoEncontradoException(id);
		}
		
		return livro;
    }

    @Override
    public List<Livro> listarTodos() {

    	String sql = "SELECT l.*, a.nome AS autor_nome, a.nacionalidade AS autor_nacionalidade FROM Livro l JOIN autor a ON l.autor_id = a.id";
    	List<Livro> livros = new ArrayList<>();
    	
    	try (Connection connection = ConnectionFactory.getConnection();
    		 PreparedStatement stmt = connection.prepareStatement(sql);
    		 ResultSet rs = stmt.executeQuery()) {
    		
    		while (rs.next()) {
    			livros.add(montarLivroComAutor(rs));
    		}
    		
    	} catch (SQLException e) {
    		throw new BancoDeDadosException("Erro ao listar livros!", e);
    	}
    	
    	return livros;
    }

    @Override
    public void atualizar(Livro livro) {
    	
    	String sql = "UPDATE livro SET titulo = ?, preco = ?, ano_publicacao = ?, autor_id = ? WHERE id = ?";
    	
    	try (Connection connection = ConnectionFactory.getConnection();
    		 PreparedStatement stmt = connection.prepareStatement(sql)) {
    		
    		stmt.setString(1, livro.getTitulo());
    		stmt.setDouble(2, livro.getPreco());
    		stmt.setInt(3, livro.getAnoPublicacao());
    		stmt.setInt(4, livro.getAutor().getId());
    		stmt.setInt(5, livro.getId());
    		
    		int linhasAfetadas = stmt.executeUpdate();
    		
    		if (linhasAfetadas == 0) {
    			throw new LivroNaoEncontradoException(livro.getId());
    		}
    		
    	} catch (SQLException e) {
    		throw new BancoDeDadosException("Erro ao atualizar livro!", e);
    	}
    }

    @Override
    public void deletar(int id) {

    	String sql = "DELETE FROM livro WHERE id = ?";
    	
    	try (Connection connection = ConnectionFactory.getConnection();
    		 PreparedStatement stmt = connection.prepareStatement(sql)) {
    		
    		stmt.setInt(1, id);
    		
    		int linhasAfetadas = stmt.executeUpdate();
    		
    		if (linhasAfetadas == 0) {
    			throw new LivroNaoEncontradoException(id);
    		}
    		
    	} catch (SQLException e) {
    		throw new BancoDeDadosException("Erro ao deletar livro!", e);
    	}
    } 
    
    //Método auxiliar para não repetir código (DRY)
    private Livro montarLivroComAutor(ResultSet rs) throws SQLException {
    	
    	Autor autor = new Autor();
    	autor.setId(rs.getInt("autor_id"));
    	autor.setNome(rs.getString("autor_nome"));
    	autor.setNacionalidade(rs.getString("autor_nacionalidade"));
    	
    	Livro livro = new Livro();
    	livro.setId(rs.getInt("id"));
    	livro.setTitulo(rs.getString("titulo"));
    	livro.setPreco(rs.getDouble("preco"));
    	livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
    	livro.setAutor(autor);
    	
    	return livro;
    }
}
