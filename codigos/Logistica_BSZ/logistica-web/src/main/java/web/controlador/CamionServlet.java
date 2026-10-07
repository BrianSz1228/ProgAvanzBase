package web.controlador;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dominio.Camion;
import negocio.modelo.dominio.Usuario;
import negocio.modelo.servicios.CamionServicio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/camiones")
public class CamionServlet extends HttpServlet {

    private CamionServicio camionServicio;

    // Se ejecuta una sola vez, cuando Tomcat crea el servlet
    @Override
    public void init() throws ServletException {
        this.camionServicio = new CamionServicio();
    }

    //  solo muestra pantallas (la lista o el formulario)
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
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("camion", this.camionServicio.buscarPorId(id));
                irAlFormulario(request, response);
            } else {
                request.setAttribute("camiones", this.camionServicio.listar());
                request.getRequestDispatcher("/WEB-INF/vistas/camiones.jsp").forward(request, response);
            }
        } catch (NegocioException e) {
            guardarMensaje(request, e.getMessage(), "warning");
            response.sendRedirect(request.getContextPath() + "/admin/camiones");
        } catch (NumberFormatException e) {
            guardarMensaje(request, "El camión indicado no es válido", "warning");
            response.sendRedirect(request.getContextPath() + "/admin/camiones");
        } catch (AccesoDatosException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", e.getMessage());
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/WEB-INF/vistas/panel-admin.jsp").forward(request, response);
        }
    }

    //  las acciones que modifican datos (guardar y eliminar)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!hayAdminLogueado(request)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        try {
            if ("guardar".equals(accion)) {
                guardar(request, response);
            } else if ("eliminar".equals(accion)) {
                eliminar(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/camiones");
            }
        } catch (NumberFormatException e) {
            guardarMensaje(request, "Los datos numéricos ingresados no son válidos", "warning");
            response.sendRedirect(request.getContextPath() + "/admin/camiones");
        } catch (AccesoDatosException e) {
            e.printStackTrace();
            guardarMensaje(request, e.getMessage(), "error");
            response.sendRedirect(request.getContextPath() + "/admin/camiones");
        }
    }

    // Si el id es 0 es un camion nuevo, si no es una modificacion
    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AccesoDatosException {

        // Campos obligatorios
        if (estaVacio(request.getParameter("marca")) || estaVacio(request.getParameter("modelo"))
                || estaVacio(request.getParameter("dominio"))
                || estaVacio(request.getParameter("toneladasMaximas"))
                || estaVacio(request.getParameter("litrosTanque"))
                || estaVacio(request.getParameter("consumoLitrosKm"))) {
            guardarMensaje(request, "Todos los campos son obligatorios", "warning");
            response.sendRedirect(request.getContextPath() + "/admin/camiones");
            return;
        }

        Camion camion = armarCamion(request);

        try {
            if (camion.getIdCamion() == 0) {
                this.camionServicio.crear(camion);
                guardarMensaje(request, "Camión guardado correctamente", "success");
            } else {
                this.camionServicio.actualizar(camion);
                guardarMensaje(request, "Camión actualizado correctamente", "success");
            }
            response.sendRedirect(request.getContextPath() + "/admin/camiones");

        } catch (NegocioException e) {
            // Vuelve al formulario con los datos que habia escrito
            request.setAttribute("camion", camion);
            request.setAttribute("mensaje", e.getMessage());
            request.setAttribute("tipoMensaje", "warning");
            irAlFormulario(request, response);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AccesoDatosException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            this.camionServicio.eliminar(id);
            guardarMensaje(request, "Camión eliminado correctamente", "success");
        } catch (NegocioException e) {
            guardarMensaje(request, e.getMessage(), "warning");
        }
        response.sendRedirect(request.getContextPath() + "/admin/camiones");
    }

    // Arma el camion con lo que mando el formulario
    private Camion armarCamion(HttpServletRequest request) {
        Camion camion = new Camion();
        camion.setIdCamion(Integer.parseInt(request.getParameter("id")));
        camion.setMarca(request.getParameter("marca"));
        camion.setModelo(request.getParameter("modelo"));
        camion.setDominio(request.getParameter("dominio"));
        camion.setToneladasMaximas(Double.parseDouble(request.getParameter("toneladasMaximas")));
        camion.setLitrosTanque(Double.parseDouble(request.getParameter("litrosTanque")));
        camion.setConsumoLitrosKm(Double.parseDouble(request.getParameter("consumoLitrosKm")));
        return camion;
    }

    private void irAlFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/vistas/camion-form.jsp").forward(request, response);
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
