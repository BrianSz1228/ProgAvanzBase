package web.controlador;

import negocio.modelo.dominio.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/panel")
public class PanelServlet extends HttpServlet {

    // Cada perfil ve su propio panel
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");

        // Sin sesion vuelve al login
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // redirecciono según corresponda a admin o chofer
        if (usuario.esAdmin()) {
            request.getRequestDispatcher("/WEB-INF/vistas/panel-admin.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/vistas/panel-chofer.jsp").forward(request, response);
        }
    }
}
