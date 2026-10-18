package service;

import model.Autor;
import java.util.List;

public interface AutorService {

	void salvar(Autor autor);
	Autor buscarPorId(int id);
	List<Autor> listarTodos();
	void atualizar(Autor autor);
	void deletar(int id);
	
}
