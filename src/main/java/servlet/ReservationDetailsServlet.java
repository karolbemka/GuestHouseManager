package servlet;

import dao.ReservationDao;
import dao.RoomDao;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Reservation;
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
import java.util.Map;

@WebServlet(urlPatterns = "/reservation-details")
public class ReservationDetailsServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationDetailsServlet.class);
    private static final String TEMPLATE_NAME = "reservation-details";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private ReservationDao reservationDao;
    @Inject
    private RoomDao roomDao;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        Reservation reservation = reservationDao.findById(Integer.parseInt(req.getParameter("id")));

        model.put("reservation", reservation);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process model due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        int roomId = reservationDao.findById(Integer.parseInt(req.getParameter("id"))).getReservedRoom().getRoomId();
        reservationDao.delete(Integer.parseInt(req.getParameter("id")));
        LOG.warn("Deleted reservation for room {}", roomDao.findById(roomId).getRoomName() );
        resp.sendRedirect("/reservations?id=" + roomId);
    }
}
