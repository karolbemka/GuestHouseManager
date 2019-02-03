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

    @Column(name = "ROOM_NAME")
    private String roomName;

    @OneToMany(mappedBy = "reservedRoom", fetch = FetchType.EAGER)
    private List<Reservation> roomReservations;


    public Room() {

    }

    public Room(String roomName, List<Reservation> roomReservations) {
        this.roomName = roomName;
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
}
