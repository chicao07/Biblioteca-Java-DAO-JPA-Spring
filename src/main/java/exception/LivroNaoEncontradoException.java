package exception;

public class LivroNaoEncontradoException extends RuntimeException {

	public LivroNaoEncontradoException(int id) {
		super("Livro com ID " + id + " não foi encontrado!");
	}
	
}
