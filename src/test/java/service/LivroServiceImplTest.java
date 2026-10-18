package service;

import dao.LivroDAO;
import exception.ValidacaoException;
import model.Livro;
import model.Autor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

public class LivroServiceImplTest {

	@Mock
	private LivroDAO livroDAO;
	
	private LivroService livroService;
	private Autor autorPadrao;
	
	@BeforeEach
	void setUp() {
		livroService = new LivroServiceImpl(livroDAO);
		autorPadrao = new Autor(1, "Machado de Assis", "Brasileiro");
	}
	
	@Test
	void deveSalvarLivroQuandoDadosForemValidos() {
		Livro livro = new Livro("Dom Casmurro", autorPadrao, 29.90, 1899);
		
		livroService.salvar(livro);
		
		verify(livroDAO, times(1)).salvar(livro);
	}
	
	@Test
	void naoDeveChamarDaoQuandoLivroForInvalido() {
		Livro livroInvalido = new Livro("", autorPadrao, 29.90, 1899);
		
		assertThrows(ValidacaoException.class, () -> livroService.salvar(livroInvalido));
		
		verify(livroDAO, never()).salvar(any(Livro.class));
	}
	
	@Test
	void deveBuscarLivroPorId() {
		Livro livroEsperado = new Livro(1, "1984", autorPadrao, 39.90, 1949);
		
		when(livroDAO.buscarPorId(1)).thenReturn(livroEsperado);
		
		Livro resultado = livroService.buscarPorId(1);
		
		assertEquals("1984", resultado.getTitulo());
		verify(livroDAO, times(1)).buscarPorId(1);
	}
	
}
