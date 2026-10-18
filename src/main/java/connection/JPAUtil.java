package connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("biblioteca-pu");
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public static void closeFactory() {
		if (factory.isOpen()) {
			factory.close();
		}
	}
	
}
