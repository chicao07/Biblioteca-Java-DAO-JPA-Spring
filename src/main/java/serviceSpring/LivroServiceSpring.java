package serviceSpring;

import exception.LivroNaoEncontradoException;
import model.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.LivroRepository;

import java.util.List;

@Service
public class LivroServiceSpring {

	private final LivroRepository livroRepository;
	
	@Autowired
	public LivroServiceSpring(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}
	
	public Livro salvar(Livro livro) {
		return livroRepository.save(livro);
	}
	
	public Livro buscarPorId(int id) {
		return livroRepository.findById(id).orElseThrow(() -> new LivroNaoEncontradoException(id));
	}
	
	public List<Livro> listarTodos() {
		return livroRepository.findAll();
	}
	
	public Livro atualizar(int id, Livro livroAtualizado) {
		Livro existente = buscarPorId(id);
		existente.setTitulo(livroAtualizado.getTitulo());
		existente.setAutor(livroAtualizado.getAutor());
		existente.setPreco(livroAtualizado.getPreco());
		existente.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
		return livroRepository.save(existente);
	}
	
	public void deletar(int id) {
		Livro existente = buscarPorId(id);
		livroRepository.delete(existente);
	}
}
