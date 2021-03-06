package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private int roomId;

    @Column(name = "ROOM_NAME", unique = true)
    private String roomName;

    @Column(name = "ROOM_SLOTS")
    private int roomSlots;

    @OneToMany(mappedBy = "reservedRoom", fetch = FetchType.EAGER)
    private List<Reservation> roomReservations;


    public Room() {

    }

    public Room(String roomName, int roomSlots, List<Reservation> roomReservations) {
        this.roomName = roomName;
        this.roomSlots = roomSlots;
        this.roomReservations = roomReservations;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Reservation> getRoomReservations() {
        return roomReservations;
    }

    public void setRoomReservations(List<Reservation> roomReservations) {
        this.roomReservations = roomReservations;
    }

    public int getRoomSlots() {
        return roomSlots;
    }

    public void setRoomSlots(int roomSlots) {
        this.roomSlots = roomSlots;
    }
}
