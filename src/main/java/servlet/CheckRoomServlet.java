package servlet;

import dao.RoomDao;
import errorHandling.ServletErrorsService;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Reservation;
import model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@WebServlet(urlPatterns = "/check-room")
public class CheckRoomServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "check-room";
    private static final Logger LOG = LoggerFactory.getLogger(CheckRoomServlet.class);

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private ReservationService reservationService;
    @Inject
    private RoomDao roomDao;
    @Inject
    private ServletErrorsService servletErrorsService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();
        model.put("admin", session.getAttribute("admin"));

        int roomId = Integer.parseInt(req.getParameter("id"));
        Room room = roomDao.findById(roomId);

        model.put("newReservation", session.getAttribute("newReservation"));
        session.removeAttribute("newReservation");
        model.put("free", session.getAttribute("free"));
        session.removeAttribute("free");
        model.put("errors", servletErrorsService.createErrorsMap(session));
        model.put("room", room);
        model.put("roomReservations", room.getRoomReservations());


        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process template due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        int roomId = Integer.parseInt(req.getParameter("roomId"));
        Room room = roomDao.findById(roomId);

        Reservation newReservation = reservationService.createReservationFromHttpRequest(req, session);
        session.setAttribute("newReservation", newReservation);

        if (newReservation.getStartDate() != null) {
            newReservation.setReservedRoom(room);
            LOG.info("Customer checked room {} for dates {} - {}", room.getRoomName(),
                    newReservation.getStartDate(), newReservation.getEndDate());
            if (reservationService.checkIfReservationDateIsFree(newReservation, session)) {
                session.setAttribute("free", true);
            } else {
                session.setAttribute("free", false);
            }
        }
        resp.sendRedirect("/check-room?id=" + roomId);
    }
}
