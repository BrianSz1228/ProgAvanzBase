package web.controlador;

import negocio.modelo.dominio.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// con el filtro redirecciono según corresponda

@WebFilter(urlPatterns = {"/panel", "/admin/*", "/chofer/*"})
public class AutenticacionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        Usuario usuario = null;
        HttpSession session = request.getSession(false);

        if (session != null) {
            usuario = (Usuario) session.getAttribute("usuarioLogueado");
        }

        // Sin sesion vuelve al login
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String ruta = request.getServletPath();
        boolean sinPermiso = (ruta.startsWith("/admin") && !usuario.esAdmin())
                            || (ruta.startsWith("/chofer") && usuario.esAdmin());
        if (sinPermiso) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Evita que el boton "atras" muestre paginas viejas despues de cerrar sesion
        response.setHeader("Cache-Control", "no-store");
        chain.doFilter(request, response);
    }
}
