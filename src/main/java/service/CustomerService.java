package service;

import dao.CustomerDao;
import model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Stateless
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Inject
    private CustomerDao customerDao;

    public Customer createCustomerFromHttpRequest(HttpServletRequest req) {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName(req.getParameter("customerName"));
        newCustomer.setCustomerSurname(req.getParameter("customerSurname"));
        newCustomer.setCustomerPhone(Integer.parseInt(req.getParameter("customerPhone")));
        newCustomer.setCustomerEmail(req.getParameter("customerEmail"));
        System.out.println(req.getParameter("customerName"));
        System.out.println(req.getParameter("customerSurname"));
        return newCustomer;
    }

    public boolean checkIfCustomerExist(Customer givenCustomer) {
        List<Customer> customerInDb = customerDao.findByPhone(givenCustomer.getCustomerPhone());

        if (customerInDb.isEmpty()) {
            LOG.info("Customer {} not found in DB", givenCustomer.getCustomerSurname());
            return false;
        } else {
            LOG.info("Given customer {} already exist in DB", givenCustomer.getCustomerSurname());

            return customerInDb.get(0).getCustomerName().equals(givenCustomer.getCustomerName()) &&
                    customerInDb.get(0).getCustomerSurname().equals(givenCustomer.getCustomerSurname());
        }
    }
}
