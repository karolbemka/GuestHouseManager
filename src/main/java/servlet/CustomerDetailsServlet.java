package servlet;

import dao.CustomerDao;
import dao.ReservationDao;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Customer;
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
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/customer-details")
public class CustomerDetailsServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationDetailsServlet.class);
    private static final String TEMPLATE_NAME = "customer-details";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private CustomerDao customerDao;
    @Inject
    private ReservationDao reservationDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        Customer customer = customerDao.findById(Integer.parseInt(req.getParameter("id")));
        List<Reservation> reservationList = reservationDao.findAllByUser(customer);

        model.put("customer", customer);
        model.put("reservationList", reservationList);

        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process template due to {}", e.getMessage());
        }
    }
}
