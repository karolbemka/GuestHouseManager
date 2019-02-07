package model;

import javax.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMIN_ID")
    private int adminId;

    @Column(name = "ADMIN_LOGIN", unique = true)
    private String adminLogin;

    @Column(name = "ADMIN_PASSWORD")
    private String adminPassword;

    public Admin(){
    }

    public Admin(String adminLogin, String adminPassword) {
        this.adminLogin = adminLogin;
        this.adminPassword = adminPassword;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
