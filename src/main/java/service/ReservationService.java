package service;

import dao.ReservationDao;
import dao.RoomDao;
import model.Reservation;
import model.Room;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Stateless
public class ReservationService {

    public static final String RESERVATION_DATE_TAKEN = "dateTaken";
    public static final String WRONG_DATES = "wrongDates";
    public static final String WRONG_NUMBER_OF_PERSONS = "wrongNumberOfPersons";
    private static final double TAX_RATE = 1.6;

    @Inject
    private ReservationDao reservationDao;
    @Inject
    private RoomDao roomDao;

    public Reservation createReservationFromHttpRequest(HttpServletRequest req, HttpSession session) {
        Reservation newReservation = new Reservation();
        int roomId = Integer.parseInt(req.getParameter("roomId"));
        Room room = roomDao.findById(roomId);

        LocalDate startDate = LocalDate.parse(req.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(req.getParameter("endDate"));
        int numberOfPersons = Integer.parseInt(req.getParameter("numberOfPersons"));

        if (checkIfEndDateIsAfterStartDate(startDate, endDate, session) && checkIfPersonNoLessThanRoomSlots(numberOfPersons, room, session)) {
            newReservation.setStartDate(startDate);
            newReservation.setEndDate(endDate);
            newReservation.setNumberOfPersons(numberOfPersons);
        }
        return newReservation;
    }

    public boolean checkIfReservationDateIsFree(Reservation givenReservation, HttpSession session) {

        List<Reservation> reservationList = reservationDao.findAllByRoom(givenReservation.getReservedRoom());

        LocalDate newStartDate = givenReservation.getStartDate();
        LocalDate newEndDate = givenReservation.getEndDate();

        for (Reservation reservation : reservationList) {
            LocalDate reservedStartDate = reservation.getStartDate();
            LocalDate reservedEndDate = reservation.getEndDate();
            if (checkIfNewReservationOverlapsExisting(newStartDate, newEndDate, reservedStartDate, reservedEndDate)) {
                session.setAttribute(RESERVATION_DATE_TAKEN, "Podany ternim jest już zajęty!");
                return false;
            }
        }
        return true;
    }

    private boolean checkIfNewReservationOverlapsExisting(LocalDate startDateToCheck, LocalDate endDateToCheck, LocalDate startDate, LocalDate endDate) {
        boolean startDateOverlapsExisting = checkIfDateIsBetween(startDateToCheck, startDate, endDate) || startDateToCheck.isEqual(startDate);
        boolean endDateOverlapsExisting = checkIfDateIsBetween(endDateToCheck, startDate, endDate) || endDateToCheck.isEqual(endDate);

        boolean existingReservationInsideNew = checkIfDateIsBetween(startDate, startDateToCheck, endDateToCheck) &&
                checkIfDateIsBetween(endDate, startDateToCheck, endDateToCheck);

        return startDateOverlapsExisting || endDateOverlapsExisting || existingReservationInsideNew;
    }

    private boolean checkIfDateIsBetween(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return dateToCheck.isAfter(startDate) && dateToCheck.isBefore(endDate);
    }

    private boolean checkIfEndDateIsAfterStartDate(LocalDate startDate, LocalDate endDate, HttpSession session) {
        if (!endDate.isAfter(startDate)) {
            session.setAttribute(WRONG_DATES, "Dzień wyjazdu nie może być wcześniej od dnia przyjazdu!");
        }
        return endDate.isAfter(startDate);
    }

    private boolean checkIfPersonNoLessThanRoomSlots(int numberOfPersons, Room room, HttpSession session) {
        int roomSlots = room.getRoomSlots();
        if (numberOfPersons > roomSlots) {
            session.setAttribute(WRONG_NUMBER_OF_PERSONS, "Liczba gości przewyższa liczbę dostępnych miejsc w pokoju!");
        }
        return numberOfPersons <= roomSlots;
    }

    public double calculateTax(Reservation reservation) {
        int numberOfPersons = reservation.getNumberOfPersons();

        long reservationDuration = DAYS.between(reservation.getStartDate(), reservation.getEndDate());

        return (double) reservationDuration * numberOfPersons * TAX_RATE;
    }
}
