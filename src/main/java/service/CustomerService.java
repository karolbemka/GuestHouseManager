package service;

import dao.CustomerDao;
import model.Customer;

import javax.inject.Inject;
import java.util.List;

public class CustomerService {

    @Inject
    private CustomerDao customerDao;

    public boolean checkIfCustomerExist(Customer givenCustomer) {
        List<Customer> customerInDb = customerDao.findByPhone(givenCustomer.getCustomerPhone());

        if (customerInDb.isEmpty()) {
            return false;
        } else return customerInDb.get(0).getCustomerName().equals(givenCustomer.getCustomerName()) &&
                customerInDb.get(0).getCustomerSurname().equals(givenCustomer.getCustomerSurname());
    }
}
