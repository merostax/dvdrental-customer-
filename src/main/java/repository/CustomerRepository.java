package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import model.Address;
import model.Customer;
import model.Payment;

import java.util.List;

@ApplicationScoped
public class CustomerRepository {


    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<Customer>listCustomers(int page) {
        return em.createQuery("SELECT c FROM Customer c", Customer.class)
                .setFirstResult((page - 1) * 20)
                .setMaxResults(20)
                .getResultList();
    }
    @Transactional
    public Customer getCustomerById(int id) {
        return em.find(Customer.class, id);
    }
    @Transactional
    public Customer createCustomer(Customer customer, int addressId, int storeId) {
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
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
        }
    }
    @Transactional
    public long getCustomerCount() {
        return em.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).getSingleResult();
    }
    public List<Payment> getPaymentsByCustomerId(int id) {
        TypedQuery<Payment> query = em.createQuery(
                "SELECT p FROM Payment p WHERE p.customerByCustomerId.customerId = :customerId", Payment.class);
        query.setParameter("customerId", id);

        return query.getResultList();
    }
}
