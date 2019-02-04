package servlet;

import dao.RoomDao;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/add-room")
public class AddRoomServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "add-room";
    private static final Logger LOG = LoggerFactory.getLogger(AddRoomServlet.class);

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

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final Room newRoom = new Room();

        newRoom.setRoomName(req.getParameter("roomName"));
        newRoom.setRoomSlots(Integer.valueOf(req.getParameter("roomSlots")));

        try {
            roomDao.save(newRoom);
            LOG.info("Added new room to DB with name {} and slots {}", newRoom.getRoomName(), newRoom.getRoomSlots());
            resp.sendRedirect("/all-rooms");
        } catch (Exception e) {
            LOG.error("Failed to add new room due to {}", e.getMessage());
            resp.sendRedirect("/add-room");
        }
    }
}
