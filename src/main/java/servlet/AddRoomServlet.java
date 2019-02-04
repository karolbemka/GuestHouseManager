package servlet;

import dao.RoomDao;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.RoomService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/add-room")
public class AddRoomServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "add-room";
    private static final Logger LOG = LoggerFactory.getLogger(AddRoomServlet.class);
    public static final String ADD_ROOM_FAILURE = "addRoomFailure";

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private RoomDao roomDao;
    @Inject
    private RoomService roomService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();
        HttpSession session = req.getSession();

        model.put("error", session.getAttribute(ADD_ROOM_FAILURE));
        session.removeAttribute(ADD_ROOM_FAILURE);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final Room newRoom = new Room();
        HttpSession session = req.getSession();

        newRoom.setRoomName(req.getParameter("roomName"));
        newRoom.setRoomSlots(Integer.valueOf(req.getParameter("roomSlots")));

        if (roomService.isRoomNameFree(newRoom)) {
            try {
                roomDao.save(newRoom);
                LOG.info("Added new room to DB with name {} and slots {}", newRoom.getRoomName(), newRoom.getRoomSlots());
                resp.sendRedirect("/all-rooms");
            } catch (Exception e) {
                LOG.error("Failed to add new room due to {}", e.getMessage());
                resp.sendRedirect("/add-room");
            }
        } else {
            session.setAttribute(ADD_ROOM_FAILURE, "Taka nazwa pokoju już istnieje");
            resp.sendRedirect("/add-room");
        }
    }
}
