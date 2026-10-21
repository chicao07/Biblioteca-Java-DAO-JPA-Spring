package exception;

public class AutorNaoEncontradoException extends RuntimeException {

	public AutorNaoEncontradoException(int id){
		super("Autor com ID " + id + " não foi encontrado!");
	}
	
}
