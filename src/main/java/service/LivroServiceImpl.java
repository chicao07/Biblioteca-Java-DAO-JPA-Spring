package service;

import dao.LivroDAO;
import dao.LivroDAOImpl;
import model.Livro;
import validator.LivroValidator;

import java.util.List;

public class LivroServiceImpl implements LivroService {

	private LivroDAO livroDAO = new LivroDAOImpl();
	
	public LivroServiceImpl() {
		this.livroDAO = new LivroDAOImpl();
	}
	
	public LivroServiceImpl(LivroDAO livroDAO) {
		this.livroDAO = livroDAO;
	}
	
	@Override
	public void salvar(Livro livro) {
		LivroValidator.validar(livro);
		livroDAO.salvar(livro);
	}
	
	@Override
    public Livro buscarPorId(int id) {
        return livroDAO.buscarPorId(id);
    }
	
	@Override
	public List<Livro> listarTodos() {
		return livroDAO.listarTodos();
	}
	
	@Override
	public void atualizar(Livro livro) {
		LivroValidator.validar(livro);
		livroDAO.atualizar(livro);
	}
	
	@Override
	public void deletar(int id) {
		livroDAO.deletar(id);
	}
	
}
