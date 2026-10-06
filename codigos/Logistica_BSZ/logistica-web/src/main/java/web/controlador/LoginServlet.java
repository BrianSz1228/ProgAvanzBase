package web.controlador;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dominio.Perfil;
import negocio.modelo.dominio.Usuario;
import negocio.modelo.servicios.ChoferServicio;
import negocio.modelo.servicios.UsuarioServicio;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

// Se configura en el web.xml la url /login
public class LoginServlet extends HttpServlet {

    public static final String cookie_usuario = "usuarioRecordado";

    private final UsuarioServicio usuarioServicio = new UsuarioServicio();
    private final ChoferServicio choferServicio = new ChoferServicio();

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
                Usuario usuario = usuarioServicio.buscarPorNombre(nombreRecordado);
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

        try {
            Usuario usuario = usuarioServicio.login(nombre, password);
            iniciarSesion(request, usuario);

            if (recordar==true) {
                Cookie cookie = new Cookie(cookie_usuario, usuario.getNombreUsuario());
                cookie.setMaxAge(7 * 24 * 60 * 60);
                cookie.setPath(rutaCookies(request));
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
        HttpSession session = request.getSession();
        request.changeSessionId();
        session.setAttribute("usuarioLogueado", usuario);

        if (usuario.getPerfil() == Perfil.CHOFER) {
            session.setAttribute("choferLogueado", choferServicio.buscarPorUsuario(usuario.getIdUsuario()));
        }
    }

    private void mostrarLogin(HttpServletRequest request, HttpServletResponse response,
                              String mensaje, String tipo) throws ServletException, IOException {
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("tipoMensaje", tipo);
        request.getRequestDispatcher("/WEB-INF/vistas/login.jsp").forward(request, response);
    }

    private String buscarCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie_usuario.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Ruta de las cookies la misma al crearlas y al borrarlas
    static String rutaCookies(HttpServletRequest request) {
        String contexto = request.getContextPath();
            if (contexto.isEmpty()) {
                return "/";
            }
            else{
                return contexto;
            }
    }
}
