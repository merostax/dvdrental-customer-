package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Address;
import util.EntityManagerProvider;

import java.util.List;

@ApplicationScoped
public class AddressRepository {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    @Transactional
    public List<Address> listAddresses(int page) {
        EntityManager em = entityManagerProvider.getEntityManager(); // Get EntityManager from the provider
        return em.createQuery("SELECT a FROM Address a", Address.class)
                .setFirstResult((page - 1) * 100)
                .setMaxResults(100)
                .getResultList();
    }

    @Transactional
    public Address getAddressById(int id) {
        EntityManager em = entityManagerProvider.getEntityManager(); // Get EntityManager from the provider
        return em.find(Address.class, id);
    }

    @Transactional
    public Address createAddress(Address address) {
        EntityManager em = entityManagerProvider.getEntityManager(); // Get EntityManager from the provider
        em.persist(address);
        return address;
    }

    @Transactional
    public void deleteAddress(int id) {
        EntityManager em = entityManagerProvider.getEntityManager(); // Get EntityManager from the provider
        Address address = em.find(Address.class, id);
        if (address != null) {
            em.remove(address);
        }
    }
}
