package validator;

import exception.ValidacaoException;
import model.Livro;

public class LivroValidator {

	public static void validar(Livro livro) {
		
		if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
			throw new ValidacaoException("O título do livro não pode ser vazio!");
		}
		
		if (livro.getAutor() == null) {
			throw new ValidacaoException("O livro deve ter um autor associado!");
		}
		
		if (livro.getPreco() <= 0) {
			throw new ValidacaoException("O preço do livro deve ser maior que zero!");
		}
		
		if (livro.getAnoPublicacao() <= 0) {
			throw new ValidacaoException("O ano de publicação deve ser válido!");
		}
		
		if (livro.getAnoPublicacao() > 2026) {
			throw new ValidacaoException("O ano de publicação não deve ser no futuro!");
		}
		
	}
	
}
