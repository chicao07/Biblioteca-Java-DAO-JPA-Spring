package dao;

import connection.ConnectionFactory;
import exception.LivroNaoEncontradoException;
import model.Livro;
import model.Autor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LivroDAOImplTest {

	private LivroDAO livroDAO;
	private AutorDAO autorDAO;
	private Autor autorPadrao;
	
	@BeforeEach
	void setUp() {
		livroDAO = new LivroDAOImpl();
		autorDAO = new AutorDAOImpl();
		limparTabelas();
		
		autorPadrao = new Autor("Autor Teste", "Brasileiro");
		autorDAO.salvar(autorPadrao);
		autorPadrao = autorDAO.listarTodos().get(0);
	}
	
	@AfterEach
	void tearDown() {
		limparTabelas();
	}
	
	private void limparTabelas() {
		try (Connection connection = ConnectionFactory.getConnection();
			 Statement stmt = connection.createStatement()) {
			
			stmt.executeUpdate("TRUNCATE TABLE livro");
			stmt.executeUpdate("TRUNCATE TABLE autor");
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao limpar tabela de testes!", e);
		}
	}
	
	@Test
	void deveSalvarLivroComSucesso() {
		Livro livro = new Livro("Dom Casmurro", autorPadrao, 29.90, 1899);
		
		livroDAO.salvar(livro);
		
		List<Livro> livros = livroDAO.listarTodos();
		
		assertEquals(1, livros.size());
		assertEquals("Dom Casmurro", livros.get(0).getTitulo());
		assertEquals("Autor Teste", livros.get(0).getAutor().getNome());
	}
	
	@Test
	void deveBuscarLivroPorId() {
		Livro livro = new Livro("1984", autorPadrao, 39.90, 1949);
		livroDAO.salvar(livro);
		
		Livro livroSalvo = livroDAO.listarTodos().get(0);
		
		Livro livroEncontrado = livroDAO.buscarPorId(livroSalvo.getId());
		
		assertNotNull(livroEncontrado);
		assertEquals("1984", livroEncontrado.getTitulo());
		assertEquals("Autor Teste", livroEncontrado.getAutor().getNome());
	}
	
	@Test
	void deveLancarExceptionAoBuscarIdInexistente() {
		assertThrows( LivroNaoEncontradoException.class, () -> livroDAO.buscarPorId(999));
	}
	
	@Test
	void deveListarTodosOsLivros() {
		livroDAO.salvar(new Livro("Livro A", autorPadrao, 10.0, 2000));
		livroDAO.salvar(new Livro("Livro B", autorPadrao, 20.0, 2010));
		
		List<Livro> livros = livroDAO.listarTodos();
		
		assertEquals(2, livros.size());
	}
	
	@Test
	void deveRetomarListaVaziaQuandoNaoHouverLivros() {
		List<Livro> livros = livroDAO.listarTodos();
		
		assertTrue(livros.isEmpty());
	}
	
	@Test
	void deveAtualizarLivroComSucesso() {
		Livro livro = new Livro("Livro Original", autorPadrao, 15.0, 2015);
		livroDAO.salvar(livro);
		
		Livro livroSalvo = livroDAO.listarTodos().get(0);
		livroSalvo.setTitulo("Livro Atualizado");
		livroSalvo.setPreco(25.0);
		
		livroDAO.atualizar(livroSalvo);
		
		Livro livroAtualizado = livroDAO.buscarPorId(livroSalvo.getId());
		
		assertEquals("Livro Atualizado", livroAtualizado.getTitulo());
		assertEquals(25.0, livroAtualizado.getPreco());
	}
	
	@Test
	void deveLancarExcecaoAoAtualizarLivroInexistente() {
		Livro livroFake = new Livro(999, "Fake", autorPadrao, 10.0, 2000);
		
		assertThrows(LivroNaoEncontradoException.class, () -> livroDAO.atualizar(livroFake));
	}
	
	@Test
	void deveDeletarLivroComSucesso() {
		Livro livro = new Livro("Livro para deletar", autorPadrao, 10.0, 2000);
		livroDAO.salvar(livro);
		
		Livro livroSalvo = livroDAO.listarTodos().get(0);
		
		livroDAO.deletar(livroSalvo.getId());
		
		List<Livro> livros = livroDAO.listarTodos();
		assertTrue(livros.isEmpty());
	}
	
	@Test
	void deveLancarexcecaoAoDeletarLivroInexistente() {
		assertThrows(LivroNaoEncontradoException.class, () -> livroDAO.deletar(999));
	}
	
}
