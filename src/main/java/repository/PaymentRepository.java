package repository;

import dtos.PaymentDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Payment;
import util.EntityManagerProvider;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@ApplicationScoped
@Transactional
public class PaymentRepository {

    @Inject
    private EntityManagerProvider entityManagerProvider;
    @Inject CustomerRepository customerRepository;
    @Transactional
    public Payment createPayment(PaymentDTO paymentDTO) {
        EntityManager em = entityManagerProvider.getEntityManager();
        Payment payment = new Payment();
        payment.setCustomerByCustomerId(customerRepository.getCustomerById( paymentDTO.getCustomer()));
        payment.setAmount(paymentDTO.getAmount());
        payment.setStaffId(paymentDTO.getStaff());
        payment.setRentalId(paymentDTO.getRental());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parsedDate = dateFormat.parse(paymentDTO.getDate());
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            payment.setPaymentDate(timestamp);
        } catch (ParseException e) {
        }

        em.persist(payment);
        return payment;
    }

    @Transactional
    public Payment getPaymentById(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        return em.find(Payment.class, id);
    }
    @Transactional
    public void deletePayment(int id) {
        EntityManager em = entityManagerProvider.getEntityManager();
        Payment payment = em.find(Payment.class, id);
        if (payment != null) {
            em.detach(payment);
        }
    }
}
