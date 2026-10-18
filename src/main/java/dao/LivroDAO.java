package dao;

import model.Livro;
import java.util.List;

public interface LivroDAO {

	//Create - Salvar um novo livro
	void salvar(Livro livro);
	
	//Read - Buscar um livro pelo ID
	Livro buscarPorId(int id);

	//Read - Listar todos os livros
	List<Livro> listarTodos();
	
	//Update - Atualizador um livro existente
	void atualizar(Livro livro);
	
	//Delete - Deletar um livro pelo ID 
	void deletar(int id);

}
