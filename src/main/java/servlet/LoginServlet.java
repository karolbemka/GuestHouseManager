package servlet;

import Auth.Auth;
import dao.AdminDao;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "login";
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private AdminDao adminDao;
    @Inject
    private Auth auth;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        model.put("error", session.getAttribute("error"));
        session.removeAttribute("error");

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

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        List<Admin> adminList = adminDao.findByLogin(login);

        if (!adminList.isEmpty()) {

            if (auth.checkCredensials(login, password)) {
                Admin admin = adminList.get(0);
                session.setAttribute("admin", admin);
                LOG.info("User with login ({}) logged in", admin.getAdminLogin());
                resp.sendRedirect("/all-rooms");
            } else {
                session.setAttribute("error", "Błędny login lub hasło");
                LOG.warn("User entered wrong password");
                doGet(req, resp);
            }
        } else {
            session.setAttribute("error", "Błędny login lub hasło");
            LOG.warn("User entered login that doesnt exist in DB: {}", login);
            doGet(req, resp);
        }
    }
}