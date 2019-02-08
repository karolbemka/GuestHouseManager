package filters;

import model.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.CheckRoomServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        filterName = "AdminFilter",
        urlPatterns = {"/add-admin",
                "/add-reservation",
                "/add-room",
                "/customer-details",
                "/customer-search",
                "reservation-details",
                "/reservations",
        })
public class AdminFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(CheckRoomServlet.class);

    private static final String INDEX_PAGE = "/all-rooms";


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String reqUri = req.getRequestURI();

        HttpSession session = req.getSession();
        Admin sessionAdmin = (Admin) session.getAttribute("admin");
        if (sessionAdmin != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            LOG.info("User {} entered ADMIN page {}", sessionAdmin.getAdminLogin(), reqUri);
        } else {
            resp.sendRedirect(INDEX_PAGE);
            LOG.error("Auth ERROR! Unlogged user tried to enter ADMIN page: {} ", reqUri);
        }
    }

    @Override
    public void destroy() {

    }
}
