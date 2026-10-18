package service;

import model.Livro;
import java.util.List;

public interface LivroService {

	void salvar(Livro livro);
	Livro buscarPorId(int id);
	List<Livro> listarTodos();
	void atualizar(Livro livro);
	void deletar(int id);
	
}
