package web.controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    // modifico estado para cerrar sesion
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // borro cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        // Se borra la sesion
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // redirecciono al login
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
