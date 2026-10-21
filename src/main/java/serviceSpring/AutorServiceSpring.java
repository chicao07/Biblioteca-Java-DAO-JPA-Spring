package serviceSpring;

import exception.AutorNaoEncontradoException;
import model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AutorRepository;

import java.util.List;

@Service
public class AutorServiceSpring {

	private final AutorRepository autorRepository;
	
	@Autowired
	public AutorServiceSpring(AutorRepository autorRepository) {
		this.autorRepository = autorRepository;
	}
	
	public Autor salvar(Autor autor) {
		return autorRepository.save(autor);
	}
	
	public Autor buscarPorId(int id) {
		return autorRepository.findById(id).orElseThrow(() -> new AutorNaoEncontradoException(id));
	}
	
	public List<Autor> listarTodos() {
		return autorRepository.findAll();
	}
	
	public Autor atualizar(int id, Autor autorAtualizado) {
		Autor existente = buscarPorId(id);
		existente.setNome(autorAtualizado.getNome());
		existente.setNacionalidade(autorAtualizado.getNacionalidade());
		return autorRepository.save(existente);
	}
	
	public void deletar(int id) {
		Autor existente = buscarPorId(id);
		autorRepository.delete(existente);
	}
}
