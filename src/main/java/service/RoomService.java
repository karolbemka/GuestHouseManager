package service;

import dao.RoomDao;
import model.Room;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RoomService {

    @Inject
    private RoomDao roomDao;

    public boolean isRoomNameFree(Room givenRoom) {
        List<Room> rooms = roomDao.findByName(givenRoom.getRoomName());

        return rooms.isEmpty();
    }
}
