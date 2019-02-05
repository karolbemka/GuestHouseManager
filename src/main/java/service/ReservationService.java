package service;

import dao.ReservationDao;
import model.Reservation;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

public class ReservationService {

    @Inject
    private ReservationDao reservationDao;

    public Reservation createReservationFromHttpRequest(HttpServletRequest req) {
        Reservation newReservation = new Reservation();
        newReservation.setStartDate(LocalDate.parse(req.getParameter("startDate")));
        newReservation.setEndDate(LocalDate.parse(req.getParameter("endDate")));
        newReservation.setNumberOfPersons(Integer.parseInt(req.getParameter("numberOfPersons")));
        return newReservation;
    }

    public boolean checkIfReservationDateIsFree(Reservation givenReservation) {

        List<Reservation> reservationList = reservationDao.findAllByRoom(givenReservation.getReservedRoom());

        LocalDate newStartDate = givenReservation.getStartDate();
        LocalDate newEndDate = givenReservation.getEndDate();

        for (Reservation reservation : reservationList) {
            LocalDate reservedStartDate = reservation.getStartDate();
            LocalDate reservedEndDate = reservation.getEndDate();
            if (checkIfNewReservationOverlapsExisting(newStartDate, newEndDate, reservedStartDate, reservedEndDate)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfNewReservationOverlapsExisting(LocalDate startDateToCheck, LocalDate endDateToCheck, LocalDate startDate, LocalDate endDate) {
        boolean startDateOverlapsExisting = checkIfDateIsBetween(startDateToCheck, startDate, endDate);
        boolean endDateOverlapsExisting = checkIfDateIsBetween(endDateToCheck, startDate, endDate);

        boolean existingReservationInsideNew = checkIfDateIsBetween(startDate, startDateToCheck, endDateToCheck) &&
                checkIfDateIsBetween(endDate, startDateToCheck, endDateToCheck);

        return startDateOverlapsExisting || endDateOverlapsExisting || existingReservationInsideNew;
    }

    private boolean checkIfDateIsBetween(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return dateToCheck.isAfter(startDate) && dateToCheck.isBefore(endDate);
    }
}
