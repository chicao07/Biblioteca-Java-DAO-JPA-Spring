package dao;

import connection.JPAUtil;
import exception.LivroNaoEncontradoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Livro;

import java.util.List;

public class LivroDAOJPAImpl implements LivroDAO{

	@Override
	public void salvar(Livro livro) {
		
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.persist(livro);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new RuntimeException("Erro ao salvar livro!", e);
		} finally {
			em.close();
		}
	}
	
	@Override
	public Livro buscarPorId(int id) {
		
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			Livro livro = em.find(Livro.class, id);
			
			if (livro == null) {
				throw new LivroNaoEncontradoException(id);
			}
			
			return livro;
			
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<Livro> listarTodos() {
		
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			return em.createQuery("SELECT l FROM Livro l", Livro.class).getResultList();
			
		} finally {
			em.close();
		}
	}
	
	@Override
	public void atualizar(Livro livro) {
		
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Livro livroExistente = em.find(Livro.class, livro.getId());
			if (livroExistente == null) {
				throw new LivroNaoEncontradoException(livro.getId());
			}
			
			em.merge(livro);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			
			throw e;
			
		} finally {
			em.close();
		}
	}
	
	@Override
	public void deletar(int id) {
		
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Livro livro = em.find(Livro.class, id);
			if (livro == null) {
				throw new LivroNaoEncontradoException(id);
			}
			
			em.remove(livro);
			tx.commit();
			
		} catch (Exception e) {
			if(tx.isActive()) {
				tx.rollback();
			}
			throw e;
			
		} finally {
			em.close();
		}
	}
}
