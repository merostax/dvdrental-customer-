package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Payment;
import util.EntityManagerProvider;

@ApplicationScoped
@Transactional
public class PaymentRepository {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    public Payment createPayment(Payment payment) {
        EntityManager em = entityManagerProvider.getEntityManager();
        em.persist(payment);
        return payment;
    }

    public Payment getPaymentById(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.find(Payment.class, id);
    }

    public void deletePayment(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        Payment payment = em.find(Payment.class, id);
        if (payment != null) {
            em.remove(payment);
        }
    }
}
