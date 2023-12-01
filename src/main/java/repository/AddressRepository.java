package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import model.Address;
import model.Customer;

import java.util.List;

@ApplicationScoped
@Transactional
public class AddressRepository {

    @PersistenceContext
    EntityManager em;

    public List<Address> listAddresses(int page) {
        return em.createQuery("SELECT a FROM Address a", Address.class)
                .setFirstResult((page - 1) * 100)
                .setMaxResults(100)
                .getResultList();
    }
    public Address getAddressById(int id) {
        return em.find(Address.class, id);
    }

    public Address createAddress(Address address) {
        em.persist(address);
        return address;
    }

    public void deleteAddress(int id) {
        Address address = em.find(Address.class, id);
        if (address != null) {
            em.remove(address);
        }
    }
    public long getAddressCount() {
        return em.createQuery("SELECT COUNT(a) FROM Address a", Long.class).getSingleResult();
    }
    public boolean isAddressDeletable(Address address) {
        List<Customer> customersWithSameAddress = em.createQuery(
                        "SELECT c FROM Customer c WHERE c.address = :address", Customer.class)
                .setParameter("address", address)
                .getResultList();

        return customersWithSameAddress.size()==0;
    }
}
