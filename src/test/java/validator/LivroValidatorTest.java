package validator;

import exception.ValidacaoException;
import model.Livro;
import model.Autor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LivroValidatorTest {
	
	private Autor autorPadrao = new Autor(1, "Machado de Assis", "Brasileiro");

	@Test
	void deveValidarLivroComDadosCorretos() {
		Livro livro = new Livro("Dom Casmurro", autorPadrao, 29.90, 1899);
		
		assertDoesNotThrow(() -> LivroValidator.validar(livro));
	}
	
	@Test
	void deveLancarExcecaoQuandoTituloForVazio() {
		Livro livro = new Livro("", autorPadrao, 29.00, 1899);
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> LivroValidator.validar(livro));
		
		assertEquals("O título do livro não pode ser vazio!", exception.getMessage());
	}
	
	@Test
	void deveLancarExcecaoQuandoTituloForNulo() {
		Livro livro = new Livro(null, autorPadrao, 29.90, 1899);
		
		assertThrows(ValidacaoException.class, () -> LivroValidator.validar(livro));
	}
	
	@Test
	void deveLancarExcecaoQuandoAutorForNulo() {
		Livro livro = new Livro("Dom Casmurro", null, 29.90, 1899);
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> LivroValidator.validar(livro));
		
		assertEquals("O livro deve ter um autor associado!", exception.getMessage());
	}
	
	@Test
	void deveLancarExcecaoQuandoPrecoForNegativo() {
		Livro livro = new Livro("Dom Casmurro", autorPadrao, -10, 1899);
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> LivroValidator.validar(livro));
		
		assertEquals("O preço do livro deve ser maior que zero!", exception.getMessage());
	}
	
	@Test
	void deveLancarExcecaoQuandoPrecoForZero() {
		Livro livro = new Livro("Dom Casmurro", autorPadrao, 0, 1899);
		
		assertThrows(ValidacaoException.class, () -> LivroValidator.validar(livro));
	}
	
	@Test
	void develancarExcecaoQuandoAnoForNegativoOuZero() {
		Livro livro = new Livro("Dom Casmurro", autorPadrao, 29.90, 0);
		
		assertThrows(ValidacaoException.class, () -> LivroValidator.validar(livro));
	}
	
	@Test
	void develancarExcecaoQuandoAnoForNoFuturo() {
		Livro livro = new Livro("Dom Casmurro", autorPadrao, 29.90, 2030);
		
		ValidacaoException exception = assertThrows(ValidacaoException.class, () -> LivroValidator.validar(livro));
		
		assertEquals("O ano de publicação não deve ser no futuro!", exception.getMessage());
	}
	
}
