package Auth;

import dao.AdminDao;
import model.Admin;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class Auth {

    @Inject
    private AdminDao adminDao;
    @Inject
    private PasswordHashing passwordHashing;

    public boolean checkCredensials(String login, String password) {

        Admin admin = adminDao.findByLogin(login).get(0);
        String hashedPassword = admin.getAdminPassword();

        return passwordHashing.checkPassword(password, hashedPassword);
    }
}
