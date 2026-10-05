package web.controlador;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dominio.Camion;
import negocio.modelo.servicios.CamionServicio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/camiones")
public class CamionServlet extends HttpServlet {

    private final CamionServicio camionServicio = new CamionServicio();

    // GET: solo muestra pantallas (la lista o el formulario)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if ("nuevo".equals(accion)) {
                irAlFormulario(request, response);
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("camion", camionServicio.buscarPorId(id));
                irAlFormulario(request, response);
            } else {
                request.setAttribute("camiones", camionServicio.listar());
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

    // POST: las acciones que modifican datos (guardar y eliminar)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        Camion camion = armarCamion(request);

        try {
            if (camion.getIdCamion() == 0) {
                camionServicio.crear(camion);
                guardarMensaje(request, "Camión guardado correctamente", "success");
            } else {
                camionServicio.actualizar(camion);
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
            camionServicio.eliminar(id);
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

    // El mensaje se guarda en la sesion para que sobreviva al redirect
    private void guardarMensaje(HttpServletRequest request, String mensaje, String tipo) {
        request.getSession().setAttribute("mensajeFlash", mensaje);
        request.getSession().setAttribute("tipoFlash", tipo);
    }
}
