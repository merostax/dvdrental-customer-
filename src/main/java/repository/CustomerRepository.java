package repository;

import laterUseTrash.dto.StoreDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import model.Address;
import model.Customer;
import services.StoreService;
import util.EntityManagerProvider;

import java.util.List;

@ApplicationScoped
public class CustomerRepository {

    @Inject
    private EntityManagerProvider entityManagerProvider;
    private StoreService storeService;

    public List<Customer> listCustomers(int page) {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.createQuery("SELECT c FROM Customer c", Customer.class)
                .setFirstResult((page - 1) * 20)
                .setMaxResults(20)
                .getResultList();
    }

    public Customer getCustomerById(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.find(Customer.class, id);
    }

    public Customer createCustomer(Customer customer, int addressId, int storeId) {
        EntityManager em = entityManagerProvider.getEntityManager();
        Address address = em.find(Address.class, addressId);
        if (address == null) {
            throw new NotFoundException("Address with ID " + addressId + " not found.");
        }
        customer.setAddress(address);
        int store_id = storeService.getStoreById(storeId);
        if (store_id == -1) {
            throw new NotFoundException("Store with ID " + storeId + " not found.");
        }
        customer.setStoreId(store_id);
        em.persist(customer);
        return customer;
    }


    public void deleteCustomer(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
        }
    }
}
