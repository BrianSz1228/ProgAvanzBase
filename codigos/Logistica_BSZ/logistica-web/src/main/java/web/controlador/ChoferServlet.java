package web.controlador;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dominio.Camion;
import negocio.modelo.dominio.Categoria;
import negocio.modelo.dominio.Chofer;
import negocio.modelo.dominio.Perfil;
import negocio.modelo.dominio.Usuario;
import negocio.modelo.servicios.CamionServicio;
import negocio.modelo.servicios.ChoferServicio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/choferes")
public class ChoferServlet extends HttpServlet {

    private final ChoferServicio choferServicio = new ChoferServicio();
    private final CamionServicio camionServicio = new CamionServicio();

    //  solo muestro pantallas (la lista o el formulario)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if ("nuevo".equals(accion)) {
                irAlFormulario(request, response);
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Chofer chofer = choferServicio.buscarPorId(id);
                request.setAttribute("chofer", chofer);
                request.setAttribute("idsCamiones", idsDe(chofer.getCamiones()));
                irAlFormulario(request, response);
            } else {
                request.setAttribute("choferes", choferServicio.listar());
                request.getRequestDispatcher("/WEB-INF/vistas/choferes.jsp").forward(request, response);
            }
        } catch (NegocioException e) {
            guardarMensaje(request, e.getMessage(), "warning");
            response.sendRedirect(request.getContextPath() + "/admin/choferes");
        } catch (NumberFormatException e) {
            guardarMensaje(request, "El chofer indicado no es válido", "warning");
            response.sendRedirect(request.getContextPath() + "/admin/choferes");
        } catch (AccesoDatosException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", e.getMessage());
            request.setAttribute("tipoMensaje", "error");
            request.getRequestDispatcher("/WEB-INF/vistas/panel-admin.jsp").forward(request, response);
        }
    }

    // las acciones que modifican datos (guardar y eliminar)
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
                response.sendRedirect(request.getContextPath() + "/admin/choferes");
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            guardarMensaje(request, "Los datos ingresados no son válidos", "warning");
            response.sendRedirect(request.getContextPath() + "/admin/choferes");
        } catch (AccesoDatosException e) {
            e.printStackTrace();
            guardarMensaje(request, e.getMessage(), "error");
            response.sendRedirect(request.getContextPath() + "/admin/choferes");
        }
    }

    // Si el id es 0 es un chofer nuevo, si no es una modificacion
    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AccesoDatosException {
        Chofer chofer = armarChofer(request);
        List<Integer> idsCamiones = leerIdsCamiones(request);

        try {
            if (chofer.getIdChofer() == 0) {
                choferServicio.crear(chofer, idsCamiones);
                guardarMensaje(request, "Chofer guardado correctamente", "success");
            } else {
                choferServicio.actualizar(chofer, idsCamiones);
                guardarMensaje(request, "Chofer actualizado correctamente", "success");
            }
            response.sendRedirect(request.getContextPath() + "/admin/choferes");

        } catch (NegocioException e) {
            // Vuelve al formulario con los datos que habia escrito
            request.setAttribute("chofer", chofer);
            request.setAttribute("idsCamiones", idsCamiones);
            request.setAttribute("mensaje", e.getMessage());
            request.setAttribute("tipoMensaje", "warning");
            irAlFormulario(request, response);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AccesoDatosException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            choferServicio.eliminar(id);
            guardarMensaje(request, "Chofer eliminado correctamente", "success");
        } catch (NegocioException e) {
            guardarMensaje(request, e.getMessage(), "warning");
        }
        response.sendRedirect(request.getContextPath() + "/admin/choferes");
    }

    // Arma el chofer con lo que mando el formulario
    private Chofer armarChofer(HttpServletRequest request) {
        Chofer chofer = new Chofer();
        chofer.setIdChofer(Integer.parseInt(request.getParameter("id")));
        chofer.setNombre(request.getParameter("nombre"));
        chofer.setApellido(request.getParameter("apellido"));
        chofer.setDni(request.getParameter("dni"));
        chofer.setFechaNacimiento(LocalDate.parse(request.getParameter("fechaNacimiento")));
        chofer.setTelefonoCelular(request.getParameter("telefonoCelular"));

        // De la categoria solo llega el id, el servicio trae el resto
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
        chofer.setCategoria(categoria);

        // El usuario y la clave solo se cargan cuando el chofer es nuevo
        if (chofer.getIdChofer() == 0) {
            chofer.setUsuario(new Usuario(0, request.getParameter("usuario"),
                    request.getParameter("password"), Perfil.CHOFER));
        }
        return chofer;
    }

    // Los camiones tildados llegan como varios valores con el mismo nombre
    private List<Integer> leerIdsCamiones(HttpServletRequest request) {
        List<Integer> ids = new ArrayList<>();
        String[] valores = request.getParameterValues("camiones");
        if (valores != null) {
            for (String valor : valores) {
                ids.add(Integer.parseInt(valor));
            }
        }
        return ids;
    }

    private List<Integer> idsDe(List<Camion> camiones) {
        List<Integer> ids = new ArrayList<>();
        for (Camion camion : camiones) {
            ids.add(camion.getIdCamion());
        }
        return ids;
    }

    // El formulario necesita las categorias y los camiones para los combos
    private void irAlFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, AccesoDatosException {
        request.setAttribute("categorias", choferServicio.listarCategorias());
        request.setAttribute("camiones", camionServicio.listar());
        request.getRequestDispatcher("/WEB-INF/vistas/chofer-form.jsp").forward(request, response);
    }

    // El mensaje se guarda en la sesion para que sobreviva al redirect
    private void guardarMensaje(HttpServletRequest request, String mensaje, String tipo) {
        request.getSession().setAttribute("mensajeFlash", mensaje);
        request.getSession().setAttribute("tipoFlash", tipo);
    }
}
