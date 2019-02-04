package dao;

import model.Room;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class RoomDao {

    @PersistenceContext
    private EntityManager entityManager;

    public int save(Room room) {
        entityManager.persist(room);
        return room.getRoomId();
    }

    public Room update(Room room) {
        return entityManager.merge(room);
    }

    public void delete(int id) {
        final Room room = entityManager.find(Room.class, id);
        if (room != null) {
            entityManager.remove(room);
        }
    }

    public List<Room> findByName(String roomName) {
        final Query query = entityManager.createQuery("SELECT r FROM Room r WHERE r.roomName = :roomName");
        query.setParameter("roomName", roomName);

        return (List<Room>) query.getResultList();
    }

    public Room findById(int id) {
        return entityManager.find(Room.class, id);
    }

    public List<Room> findAll() {
        final Query query = entityManager.createQuery("SELECT r FROM Room r");

        return (List<Room>) query.getResultList();
    }
}
