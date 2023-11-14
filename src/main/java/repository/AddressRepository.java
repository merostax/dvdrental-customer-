package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Address;
import model.Customer;
import util.EntityManagerProvider;

import java.util.List;

@ApplicationScoped
@Transactional
public class AddressRepository {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    public List<Address> listAddresses(int page) {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.createQuery("SELECT a FROM Address a", Address.class)
                .setFirstResult((page - 1) * 100)
                .setMaxResults(100)
                .getResultList();
    }
    public Address getAddressById(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.find(Address.class, id);
    }

    public Address createAddress(Address address) {
        EntityManager em = entityManagerProvider.getEntityManager();
        em.persist(address);
        return address;
    }

    public void deleteAddress(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        Address address = em.find(Address.class, id);
        if (address != null) {
            em.remove(address);
        }
    }
    public long getAddressCount() {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.createQuery("SELECT COUNT(a) FROM Address a", Long.class).getSingleResult();
    }
    public boolean isAddressDeletable(Address address) {
        EntityManager em = entityManagerProvider.getEntityManager();
        List<Customer> customersWithSameAddress = em.createQuery(
                        "SELECT c FROM Customer c WHERE c.address = :address", Customer.class)
                .setParameter("address", address)
                .getResultList();

        return customersWithSameAddress.size()==0;
    }
}
