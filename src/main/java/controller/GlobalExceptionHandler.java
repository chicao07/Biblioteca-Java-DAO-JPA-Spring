package controller;

import exception.AutorNaoEncontradoException;
import exception.LivroNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Dispara quando o @Valid encontra alguma violação (ex: @NotBlank, @Positive, @Max)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidacao(MethodArgumentNotValidException ex) {
		Map<String, String> erros = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
	}
	
	// Recurso especifico não encontrado: Autor
	@ExceptionHandler(AutorNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> handleAutorNaoEncontrado(AutorNaoEncontradoException ex) {
		Map<String, String> erro = new HashMap<>();
		erro.put("erro", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	// Recurso especifico não encontrado: Livro
		@ExceptionHandler(LivroNaoEncontradoException.class)
		public ResponseEntity<Map<String, String>> handleLivroNaoEncontrado(LivroNaoEncontradoException ex) {
			Map<String, String> erro = new HashMap<>();
			erro.put("erro", ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
		}
	
	// Dispara qualquer erro inesperado (fallback genérico)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
		Map<String, String> erro = new HashMap<>();
		erro.put("erro", "Erro interno inesperado: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}
}
