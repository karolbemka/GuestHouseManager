package dao;

import model.Admin;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class AdminDao {

    @PersistenceContext
    private EntityManager entityManager;

    public int save(Admin admin) {
        entityManager.persist(admin);
        return admin.getAdminId();
    }

    public List<Admin> findByLogin(String login) {
        final Query query = entityManager.createQuery("SELECT a FROM Admin a WHERE a.adminLogin = :login");
        query.setParameter("login", login);

        return (List<Admin>) query.getResultList();
    }
}