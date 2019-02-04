package servlet;

import dao.RoomDao;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Reservation;
import model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/reservations")
public class ReservationsServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "reservations";
    private static final Logger LOG = LoggerFactory.getLogger(ReservationsServlet.class);

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private RoomDao roomDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        int roomId = Integer.parseInt(req.getParameter("id"));

        Room room = roomDao.findById(roomId);
        List<Reservation> roomReservations = room.getRoomReservations();

        model.put("room", room);
        model.put("roomReservations", roomReservations);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to add new room due to {}", e.getMessage());
        }

    }
}
