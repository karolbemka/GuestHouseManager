package servlet;

import Auth.PasswordHashing;
import dao.AdminDao;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Admin;
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

@WebServlet(urlPatterns = "/add-admin")
public class AddAdminServlet extends HttpServlet {

    private static final String TEMPLATE_NAME = "add-admin";
    private static final Logger LOG = LoggerFactory.getLogger(AddAdminServlet.class);

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private AdminDao adminDao;
    @Inject
    private PasswordHashing passwordHashing;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process template due to {}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        String adminLogin = req.getParameter("adminLogin");
        String adminPassword = req.getParameter("adminPassword");
        String hashedPassword = passwordHashing.generateHash(adminPassword);

        Admin admin = new Admin();

        admin.setAdminLogin(adminLogin);
        admin.setAdminPassword(hashedPassword);

        try {
            adminDao.save(admin);
            LOG.info("Added new admin account with login: {}", admin.getAdminLogin());
        } catch (Exception e) {
            LOG.error("Failed to add new admin account due to {}", e.getMessage());
        }
    }
}
