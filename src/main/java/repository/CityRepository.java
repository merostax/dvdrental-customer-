package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import model.City;

@ApplicationScoped
public class CityRepository {

    @PersistenceContext
    private EntityManager em;

    public City findCityByName(String cityName) {
        try {
            return em.createQuery("SELECT c FROM City c WHERE c.city = :cityName", City.class)
                    .setParameter("cityName", cityName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Stadt wurde nicht gefunden
        }
    }

    // Weitere Methoden für das Erstellen, Aktualisieren und Löschen von Städten
}
