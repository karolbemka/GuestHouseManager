package servlet;

import dao.CustomerDao;
import dao.ReservationDao;
import dao.RoomDao;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Customer;
import model.Reservation;
import model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CustomerService;
import service.ReservationService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/add-reservation")
public class AddReservationServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "add-reservation";
    private static final Logger LOG = LoggerFactory.getLogger(AddReservationServlet.class);

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private RoomDao roomDao;
    @Inject
    private ReservationDao reservationDao;
    @Inject
    private CustomerDao customerDao;
    @Inject
    private CustomerService customerService;
    @Inject
    private ReservationService reservationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        int roomId = Integer.parseInt(req.getParameter("id"));
        Room room = roomDao.findById(roomId);

        model.put("errors", createErrorsMap(session));
        model.put("room", room);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to add new room due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        req.setCharacterEncoding("UTF-8");
        int roomId = Integer.parseInt(req.getParameter("roomId"));
        Room room = roomDao.findById(roomId);

        Reservation newReservation = reservationService.createReservationFromHttpRequest(req, session);
        Customer newCustomer = customerService.createCustomerFromHttpRequest(req);

        if (customerService.checkIfCustomerExist(newCustomer)) {
            newCustomer = customerDao.findByPhone(Integer.parseInt(req.getParameter("customerPhone"))).get(0);
        } else {
            customerDao.save(newCustomer);
            LOG.info("Addend new customer ({}) to DB", newCustomer.getCustomerSurname());
        }

        if (newReservation.getStartDate()!=null) {
            newReservation.setReservationCustomer(newCustomer);
            newReservation.setReservedRoom(room);
            if (reservationService.checkIfReservationDateIsFree(newReservation, session)) {
                try {
                    reservationDao.save(newReservation);
                    LOG.info("Added new reservation for room {} with start date {}, end date {}, customer surename {}",
                            room.getRoomName(), newReservation.getStartDate(), newReservation.getEndDate(),
                            newCustomer.getCustomerSurname());
                    resp.sendRedirect("/reservations?id=" + roomId);
                } catch (Exception e) {
                    LOG.error("Failed to add new reservation for room {} due to {}", room.getRoomName(), e.getMessage());
                }
            } else {
                resp.sendRedirect("/add-reservation?id=" + roomId);
            }
        } else {
            resp.sendRedirect("/add-reservation?id=" + roomId);
        }
    }

    private Map<String, Object> createErrorsMap(HttpSession session) {
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
