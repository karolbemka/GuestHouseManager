package dao;

import model.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public int save(Customer customer) {
        entityManager.persist(customer);
        return customer.getCustomerId();
    }

    public Customer update(Customer customer) {
        return entityManager.merge(customer);
    }

    public void delete(int id) {
        final Customer customer = entityManager.find(Customer.class, id);
        if (customer != null) {
            entityManager.remove(customer);
        }
    }

    public Customer findById(int id) {
        return entityManager.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        final Query query = entityManager.createQuery("SELECT c FROM Customer c");

        return (List<Customer>) query.getResultList();
    }

    public List<Customer> findByPhone(int customerPhone) {
        final Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.customerPhone = :customerPhone");
        query.setParameter("customerPhone", customerPhone);

        return (List<Customer>) query.getResultList();
    }

    public List<Customer> findBySurname(String customerSurname) {
        final Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.customerSurname = :customerSurname");
        query.setParameter("customerSurname", customerSurname);

        return (List<Customer>) query.getResultList();
    }

    public List<Customer> findByEmail(String customerEmail) {
        final Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.customerEmail = :customerEmail");
        query.setParameter("customerEmail", customerEmail);

        return (List<Customer>) query.getResultList();
    }

}
