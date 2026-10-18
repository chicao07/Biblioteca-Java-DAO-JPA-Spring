package dao;

import connection.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Autor;

import java.util.List;

public class AutorDAOJPAImpl implements AutorDAO {

	@Override
	public void salvar(Autor autor) {
		
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.persist(autor);
			tx.commit();
			
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new RuntimeException("Erro ao salvar autor!", e);
			
		} finally {
			em.close();
		}
	}
	
	@Override
	public Autor buscarPorId(int id) {
		
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			return em.find(Autor.class, id);
			
		} finally {
			em.close();
		}
	}
	
	@Override 
	public List<Autor> listarTodos() {
		
		EntityManager em = JPAUtil.getEntityManager();
		
		try {
			return em.createQuery("SELECT a FROM Autor a", Autor.class).getResultList();
			
		} finally {
			em.close();
		}
	}
	
	@Override
	public void atualizar(Autor autor) {
		
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.merge(autor);
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
	
	@Override
	public void deletar(int id) {
		
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Autor autor = em.find(Autor.class, id);
			if(autor != null) {
				em.remove(autor);
			}
			
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
