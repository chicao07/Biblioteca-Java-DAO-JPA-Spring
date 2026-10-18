package controller;

import jakarta.validation.Valid;
import model.Livro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serviceSpring.LivroServiceSpring;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

	private final LivroServiceSpring livroService;
	
	public LivroController(LivroServiceSpring livroService) {
		this.livroService = livroService;
	}
	
	@GetMapping
	public List<Livro> listarTodos() {
		return livroService.listarTodos();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Livro> buscarPorId(@PathVariable int id) {
		Livro livro = livroService.buscarPorId(id);
		return ResponseEntity.ok(livro);
	}
	
	@PostMapping
	public ResponseEntity<Livro> salvar(@Valid @RequestBody Livro livro) {
		Livro salvo = livroService.salvar(livro);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Livro> atualizar(@PathVariable int id, @Valid @RequestBody Livro livro) {
		Livro atualizado = livroService.atualizar(id, livro);
		return ResponseEntity.ok(atualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable int id) {
		livroService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
