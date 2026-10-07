package web.controlador;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dominio.Chofer;
import negocio.modelo.dominio.Usuario;
import negocio.modelo.dominio.Viaje;
import negocio.modelo.servicios.CamionServicio;
import negocio.modelo.servicios.ChoferServicio;
import negocio.modelo.servicios.ViajeServicio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/viajes")
public class ViajeServlet extends HttpServlet {

    private ViajeServicio viajeServicio;
    private ChoferServicio choferServicio;
    private CamionServicio camionServicio;

    // Se ejecuta una sola vez, cuando Tomcat crea el servlet
    @Override
    public void init() throws ServletException {
        this.viajeServicio = new ViajeServicio();
        this.choferServicio = new ChoferServicio();
        this.camionServicio = new CamionServicio();
    }

    //  muestra la lista, el formulario o el resultado de buscar un chofer
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Si no hay un admin logueado, vuelve al login
        if (!hayAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String accion = request.getParameter("accion");

        try {
            if ("nuevo".equals(accion)) {
                irAlFormulario(request, response);
            } else if ("buscar".equals(accion)) {
                buscarChofer(request, response);
            } else {
                request.setAttribute("viajes", this.viajeServicio.listarTodos());
                request.getRequestDispatcher("/WEB-INF/vistas/viajes.jsp").forward(request, response);
            }
        } catch (AccesoDatosException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", e.getMessage());
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/WEB-INF/vistas/panel-admin.jsp").forward(request, response);
        }
    }

    // guardo el viaje
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!hayAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        try {
            if ("guardar".equals(request.getParameter("accion"))) {
                guardar(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/viajes");
            }
        } catch (NumberFormatException e) {
            guardarMensaje(request, "Los datos del viaje no son válidos", "warning");
            response.sendRedirect(request.getContextPath() + "/admin/viajes");
        } catch (AccesoDatosException e) {
            e.printStackTrace();
            guardarMensaje(request, e.getMessage(), "error");
            response.sendRedirect(request.getContextPath() + "/admin/viajes");
        }
    }

    // Paso 1: se busca el chofer por DNI. Si existe, se cargan sus camiones libres
    private void buscarChofer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AccesoDatosException {
        String dni = request.getParameter("dni");

        if (estaVacio(dni)) {
            request.setAttribute("mensaje", "Ingresá el DNI del chofer");
            request.setAttribute("tipoMensaje", "warning");
            irAlFormulario(request, response);
            return;
        }

        try {
            Chofer chofer = this.choferServicio.buscarPorDni(dni);
            prepararViaje(request, chofer);
        } catch (NegocioException e) {
            // No existe: se vuelve a la busqueda con el aviso
            request.setAttribute("mensaje", e.getMessage());
            request.setAttribute("tipoMensaje", "warning");
        }
        irAlFormulario(request, response);
    }

    // Paso 2: se elige camion, origen y destino y se guarda el viaje
    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AccesoDatosException {

        // El DNI viaja oculto en el formulario para poder volver a la busqueda del chofer
        String volverABuscar = request.getContextPath() + "/admin/viajes?accion=buscar&dni="
                + request.getParameter("dni");

        // Campos obligatorios
        if (estaVacio(request.getParameter("idCamion")) || estaVacio(request.getParameter("idOrigen"))
                || estaVacio(request.getParameter("idDestino"))) {
            guardarMensaje(request, "Tenés que elegir el camión, el origen y el destino", "warning");
            response.sendRedirect(volverABuscar);
            return;
        }

        int idChofer = Integer.parseInt(request.getParameter("idChofer"));
        int idCamion = Integer.parseInt(request.getParameter("idCamion"));
        int idOrigen = Integer.parseInt(request.getParameter("idOrigen"));
        int idDestino = Integer.parseInt(request.getParameter("idDestino"));

        try {
            Viaje viaje = this.viajeServicio.crearViaje(idChofer, idCamion, idOrigen, idDestino);

            guardarMensaje(request, "Viaje cargado. Distancia: " + viaje.getKm() + " km - Días: "
                    + viaje.getDias() + " - Tanques: " + viaje.getTanques(), "success");
            response.sendRedirect(request.getContextPath() + "/admin/viajes");

        } catch (NegocioException e) {
            // Vuelve a la busqueda del chofer con el aviso
            guardarMensaje(request, e.getMessage(), "warning");
            response.sendRedirect(volverABuscar);
        }
    }

    // Deja en la request lo que necesita el formulario una vez elegido el chofer
    private void prepararViaje(HttpServletRequest request, Chofer chofer) throws AccesoDatosException {
        request.setAttribute("chofer", chofer);
        request.setAttribute("camiones", this.camionServicio.listarDisponiblesParaChofer(chofer.getIdChofer()));
        request.setAttribute("destinos", this.viajeServicio.listarDestinos());
    }

    private void irAlFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/vistas/viaje-form.jsp").forward(request, response);
    }

    // Solo puede entrar un admin que haya iniciado sesion
    private boolean hayAdminLogueado(HttpServletRequest request) {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogueado");
        return usuario != null && usuario.esAdmin();
    }

    // Un campo esta vacio si no llego o si tiene solo espacios
    private boolean estaVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    // El mensaje se guarda en la sesion para que sobreviva al redirect
    private void guardarMensaje(HttpServletRequest request, String mensaje, String tipo) {
        request.getSession().setAttribute("mensajeFlash", mensaje);
        request.getSession().setAttribute("tipoFlash", tipo);
    }
}
