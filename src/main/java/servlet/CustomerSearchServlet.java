package servlet;

import dao.CustomerDao;
import freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CustomerService;

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

@WebServlet(urlPatterns = "/customer-search")
public class CustomerSearchServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerSearchServlet.class);
    private static final String TEMPLATE_NAME = "customer-search";

    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private CustomerService customerService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Content-Type", "text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NAME);
        Map<String, Object> model = new HashMap<>();

        List<Customer> customerList = customerService.searchForCustomers(req);

        if (req.getParameter("searchFor") != null) {
            model.put("customerList", customerList);
        }


        try {
            template.process(model, out);
        } catch (TemplateException e) {
            LOG.error("Failed to process template due to {}", e.getMessage());
        }


    }
}
