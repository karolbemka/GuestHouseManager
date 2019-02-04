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

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        int roomId = Integer.parseInt(req.getParameter("id"));

        Room room = roomDao.findById(roomId);

        model.put("room", room);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to add new room due to {}", e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int roomId = Integer.parseInt(req.getParameter("roomId"));
        Room room = roomDao.findById(roomId);

        final Reservation newReservation = new Reservation();
        newReservation.setStartDate(LocalDate.parse(req.getParameter("startDate")));
        newReservation.setEndDate(LocalDate.parse(req.getParameter("endDate")));
        newReservation.setNumberOfPersons(Integer.parseInt(req.getParameter("numberOfPersons")));
        final Customer newCustomer = new Customer();
        newCustomer.setCustomerName(req.getParameter("customerName"));
        newCustomer.setCustomerSurname(req.getParameter("customerSurname"));
        newCustomer.setCustomerPhone(Integer.parseInt(req.getParameter("customerPhone")));
        newCustomer.setCustomerEmail(req.getParameter("customerEmail"));
        newReservation.setReservationCustomer(newCustomer);
        newReservation.setReservedRoom(room);

        customerDao.save(newCustomer);

        reservationDao.save(newReservation);

        resp.sendRedirect("/reservations?id=" + roomId);
    }
}
