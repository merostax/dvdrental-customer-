package util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@ApplicationScoped
public class EntityManagerProvider {

    @PersistenceUnit(unitName = "persistent")
    private EntityManagerFactory emf;

    private EntityManager em;

    @PostConstruct
    public void init() {
        em = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    @PreDestroy
    public void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}

