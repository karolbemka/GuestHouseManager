package Auth;

import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;

@Stateless
public class PasswordHashing {

    public String generateHash(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
