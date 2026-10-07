package web.controlador;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dominio.Chofer;
import negocio.modelo.dominio.Perfil;
import negocio.modelo.dominio.Usuario;
import negocio.modelo.servicios.ChoferServicio;
import negocio.modelo.servicios.UsuarioServicio;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

// Se configura en el web.xml la url /login
public class LoginServlet extends HttpServlet {

    // Nombre de la cookie que recuerda al usuario
    private static final String COOKIE_USUARIO = "usuarioRecordado";

    private UsuarioServicio usuarioServicio;
    private ChoferServicio choferServicio;

    // Se ejecuta una sola vez, cuando Tomcat crea el servlet
    @Override
    public void init() throws ServletException {
        this.usuarioServicio = new UsuarioServicio();
        this.choferServicio = new ChoferServicio();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Si ya tiene sesion va directo al panel
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuarioLogueado") != null) {
            response.sendRedirect(request.getContextPath() + "/panel");
            return;
        }

        // Si quedo la cookie de un login anterior, entra sin pedir la contraseña
        String nombreRecordado = buscarCookie(request);
        if (nombreRecordado != null) {
            try {
                Usuario usuario = this.usuarioServicio.buscarPorNombre(nombreRecordado);
                if (usuario != null) {
                    iniciarSesion(request, usuario);
                    response.sendRedirect(request.getContextPath() + "/panel");
                    return;
                }
            } catch (NegocioException | AccesoDatosException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String nombre = request.getParameter("usuario");
        String password = request.getParameter("password");
        boolean recordar = request.getParameter("recordarme") != null;

        // Campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            mostrarLogin(request, response, "Ingresá el usuario y la contraseña", "warning");
            return;
        }

        try {
            Usuario usuario = this.usuarioServicio.login(nombre, password);
            iniciarSesion(request, usuario);

            // La cookie guarda solo el nombre de usuario, nunca la contraseña
            if (recordar==true) {
                Cookie cookie = new Cookie(COOKIE_USUARIO, usuario.getNombreUsuario());
                cookie.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(cookie);
            }

            response.sendRedirect(request.getContextPath() + "/panel");

        } catch (NegocioException e) {
            mostrarLogin(request, response, e.getMessage(), "warning");
        } catch (AccesoDatosException e) {
            e.printStackTrace();
            mostrarLogin(request, response, e.getMessage(), "error");
        }
    }

    // Guarda el usuario en la sesion, si es chofer guarda tambien sus datos
    private void iniciarSesion(HttpServletRequest request, Usuario usuario)
            throws NegocioException, AccesoDatosException {

        // Primero busco el chofer, asi si falla no queda una sesion a medias
        Chofer chofer = null;
        if (usuario.getPerfil() == Perfil.CHOFER) {
            chofer = this.choferServicio.buscarPorUsuario(usuario.getIdUsuario());
        }

        HttpSession session = request.getSession();
        session.setAttribute("usuarioLogueado", usuario);
        if (chofer != null) {
            session.setAttribute("choferLogueado", chofer);
        }
    }

    private void mostrarLogin(HttpServletRequest request, HttpServletResponse response,
                              String mensaje, String tipo) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipoMensaje", tipo);
        request.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(request, response);
    }

    // Recorre las cookies que manda el navegador y devuelve el usuario recordado
    private String buscarCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_USUARIO.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
