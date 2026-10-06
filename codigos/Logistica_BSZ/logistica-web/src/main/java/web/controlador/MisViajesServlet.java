package web.controlador;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dominio.Chofer;
import negocio.modelo.servicios.ViajeServicio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Pantalla del chofer: ve sus viajes y los inicia o finaliza
@WebServlet("/chofer/viajes")
public class MisViajesServlet extends HttpServlet {

    private final ViajeServicio viajeServicio = new ViajeServicio();

    //  muestra solo los viajes del chofer que inicio sesion
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Chofer chofer = (Chofer) request.getSession().getAttribute("choferLogueado");
        if (chofer == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            request.setAttribute("viajes", viajeServicio.listarPorChofer(chofer.getIdChofer()));
            request.getRequestDispatcher("/WEB-INF/vistas/mis-viajes.jsp").forward(request, response);
        } catch (AccesoDatosException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", e.getMessage());
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/WEB-INF/vistas/panel-chofer.jsp").forward(request, response);
        }
    }

    // cambia el estado de un viaje (iniciar o finalizar)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // El chofer sale de la sesion, no del formulario, para que nadie pueda tocar viajes ajenos
        Chofer chofer = (Chofer) request.getSession().getAttribute("choferLogueado");
        if (chofer == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String accion = request.getParameter("accion");

        try {
            int idViaje = Integer.parseInt(request.getParameter("idViaje"));

            if ("iniciar".equals(accion)) {
                viajeServicio.iniciarViaje(idViaje, chofer.getIdChofer());
                guardarMensaje(request, "Viaje iniciado", "success");
            } else if ("finalizar".equals(accion)) {
                viajeServicio.finalizarViaje(idViaje, chofer.getIdChofer());
                guardarMensaje(request, "Viaje finalizado", "success");
            }
        } catch (NegocioException e) {
            guardarMensaje(request, e.getMessage(), "warning");
        } catch (NumberFormatException e) {
            guardarMensaje(request, "El viaje indicado no es válido", "warning");
        } catch (AccesoDatosException e) {
            e.printStackTrace();
            guardarMensaje(request, e.getMessage(), "error");
        }

        response.sendRedirect(request.getContextPath() + "/chofer/viajes");
    }

    // El mensaje se guarda en la sesion para que sobreviva al redirect
    private void guardarMensaje(HttpServletRequest request, String mensaje, String tipo) {
        request.getSession().setAttribute("mensajeFlash", mensaje);
        request.getSession().setAttribute("tipoFlash", tipo);
    }
}
