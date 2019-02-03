package dao;

import model.Room;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    public Room findById(int id) {
        return entityManager.find(Room.class, id);
    }
}
