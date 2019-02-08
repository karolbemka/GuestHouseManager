package errorHandling;

import service.ReservationService;

import javax.ejb.Stateless;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class ServletErrorsService {

    public Map<String, Object> createErrorsMap(HttpSession session) {
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put(ReservationService.WRONG_DATES, session.getAttribute(ReservationService.WRONG_DATES));
        errorMap.put(ReservationService.RESERVATION_DATE_TAKEN, session.getAttribute(ReservationService.RESERVATION_DATE_TAKEN));
        errorMap.put(ReservationService.WRONG_NUMBER_OF_PERSONS, session.getAttribute(ReservationService.WRONG_NUMBER_OF_PERSONS));
        session.removeAttribute(ReservationService.WRONG_DATES);
        session.removeAttribute(ReservationService.RESERVATION_DATE_TAKEN);
        session.removeAttribute(ReservationService.WRONG_NUMBER_OF_PERSONS);

        return errorMap;
    }
}
