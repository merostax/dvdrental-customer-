package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import model.Address;
import model.Customer;
import util.EntityManagerProvider;

import java.util.List;

@ApplicationScoped
public class CustomerRepository {


    @Inject
    private EntityManagerProvider entityManagerProvider;

    @Transactional
    public List<Customer> listCustomers(int page) {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.createQuery("SELECT c FROM Customer c", Customer.class)
                .setFirstResult((page - 1) * 20)
                .setMaxResults(20)
                .getResultList();
    }
    @Transactional
    public Customer getCustomerById(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.find(Customer.class, id);
    }
    @Transactional
    public Customer createCustomer(Customer customer, int addressId, int storeId) {
        EntityManager em = entityManagerProvider.getEntityManager();
        Address address = em.find(Address.class, addressId);
        if (address == null) {
            throw new NotFoundException("Address with ID " + addressId + " not found.");
        }
        customer.setAddress(address);
        customer.setStoreId(storeId);
        em.persist(customer);
        return customer;
    }

    @Transactional
    public void deleteCustomer(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
        }
    }
}
