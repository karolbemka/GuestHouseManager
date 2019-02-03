package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private int reservationId;

    @ManyToOne
    @JoinColumn(name = "RESERVED_ROOM")
    private Room reservedRoom;

    @ManyToOne
    @JoinColumn(name = "RESERVATION_CUSTOMER")
    private Customer reservationCustomer;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;



    public Reservation() {

    }

    public Reservation(Room reservedRoom, Customer reservationCustomer, LocalDate startDate, LocalDate endDate) {
        this.reservedRoom = reservedRoom;
        this.reservationCustomer = reservationCustomer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Room getReservedRoom() {
        return reservedRoom;
    }

    public void setReservedRoom(Room reservedRoom) {
        this.reservedRoom = reservedRoom;
    }

    public Customer getReservationCustomer() {
        return reservationCustomer;
    }

    public void setReservationCustomer(Customer reservationCustomer) {
        this.reservationCustomer = reservationCustomer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}


