package controller;

import jakarta.validation.Valid;
import model.Autor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serviceSpring.AutorServiceSpring;

import java.util.List;


@RestController
@RequestMapping("/autores")
public class AutorController {

	private final AutorServiceSpring autorService;
	
	public AutorController(AutorServiceSpring autorService) {
		this.autorService = autorService;
	}
	
	@GetMapping
	public List<Autor> listarTodos() {
		return autorService.listarTodos();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Autor> buscarPorId(@PathVariable int id) {
		Autor autor = autorService.buscarPorId(id);
		return ResponseEntity.ok(autor);
	}
	
	@PostMapping
	public ResponseEntity<Autor> salvar(@Valid @RequestBody Autor autor) {
		Autor salvo = autorService.salvar(autor);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Autor> atualizar(@PathVariable int id, @Valid @RequestBody Autor autor) {
		Autor atualizado = autorService.atualizar(id, autor);
		return ResponseEntity.ok(atualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable int id) {
		autorService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
