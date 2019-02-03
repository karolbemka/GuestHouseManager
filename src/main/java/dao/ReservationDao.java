package dao;

import model.Customer;
import model.Room;
import model.Reservation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ReservationDao {

    @PersistenceContext
    private EntityManager entityManager;

    public int save(Reservation reservation) {
        entityManager.persist(reservation);
        return reservation.getReservationId();
    }

    public Reservation update(Reservation reservation) {
        return entityManager.merge(reservation);
    }

    public void delete(int id) {
        final Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation != null) {
            entityManager.remove(reservation);
        }
    }

    public Reservation findById(int id) {
        return entityManager.find(Reservation.class, id);
    }

    public List<Reservation> findAllByRoom(Room room) {
        final Query query = entityManager.createQuery("SELECT r FROM Reservation r WHERE r.reservedRoom = :room");
        query.setParameter("room", room);

        return (List<Reservation>) query.getResultList();
    }

    public List<Reservation> findAllByUser(Customer customer) {
        final Query query = entityManager.createQuery("SELECT r FROM Reservation r WHERE r.reservationCustomer = :customer");
        query.setParameter("customer", customer);

        return (List<Reservation>) query.getResultList();
    }

    public List<Reservation> findAll() {
        final Query query = entityManager.createQuery("SELECT r FROM Reservation r");

        return (List<Reservation>) query.getResultList();
    }
}
