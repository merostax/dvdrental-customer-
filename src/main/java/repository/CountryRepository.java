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
            return em.createQuery("SELECT c FROM Country c WHERE LOWER(c.country) = LOWER(:countryName)", Country.class)
                    .setParameter("countryName", countryName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}
