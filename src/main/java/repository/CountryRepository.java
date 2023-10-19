package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import model.Country;

@ApplicationScoped
@Transactional
public class CountryRepository {

    @PersistenceContext
    private EntityManager em;
    public Country findCountryByName(String countryName) {
        try {
            return em.createQuery("SELECT c FROM Country c WHERE c.country = :countryName", Country.class)
                    .setParameter("countryName", countryName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Land wurde nicht gefunden
        }
    }

    // Weitere Methoden für das Erstellen, Aktualisieren und Löschen von Ländern
}
