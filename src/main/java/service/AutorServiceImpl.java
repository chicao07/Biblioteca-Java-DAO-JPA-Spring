package service;

import dao.AutorDAO;
import dao.AutorDAOImpl;
import model.Autor;

import java.util.List;

public class AutorServiceImpl implements AutorService {

	private AutorDAO autorDAO;
	
	public AutorServiceImpl() {
		this.autorDAO = new AutorDAOImpl();
	}
	
	public AutorServiceImpl(AutorDAO autorDAO) {
		this.autorDAO = autorDAO;
	}
	
	@Override
	public void salvar(Autor autor) {
		autorDAO.salvar(autor);
	}
	
	@Override
	public Autor buscarPorId(int id) {
		return autorDAO.buscarPorId(id);
	}
	
	@Override
	public List<Autor> listarTodos() {
		return autorDAO.listarTodos();
	}
	
	@Override
	public void atualizar(Autor autor) {
		autorDAO.atualizar(autor);
	}
	
	@Override
	public void deletar(int id) {
		autorDAO.deletar(id);
	}
	
}
