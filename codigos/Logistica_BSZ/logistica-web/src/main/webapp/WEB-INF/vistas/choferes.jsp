<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/vistas/partes/cabecera.jsp">
    <jsp:param name="titulo" value="Choferes"/>
</jsp:include>
<jsp:include page="/WEB-INF/vistas/partes/menu-admin.jsp"/>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">Choferes</h3>
        <a href="${pageContext.request.contextPath}/admin/choferes?accion=nuevo" class="btn btn-primary">Nuevo chofer</a>
    </div>

    <c:choose>
        <c:when test="${empty choferes}">
            <div class="alert alert-info">No hay choferes cargados.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-striped table-hover bg-white align-middle">
                    <thead>
                        <tr>
                            <th>Apellido y nombre</th>
                            <th>DNI</th>
                            <th>Nacimiento</th>
                            <th>Teléfono</th>
                            <th>Categoría</th>
                            <th>Usuario</th>
                            <th class="text-end">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="chofer" items="${choferes}">
                            <tr>
                                <td><c:out value="${chofer.apellido}"/>, <c:out value="${chofer.nombre}"/></td>
                                <td><c:out value="${chofer.dni}"/></td>
                                <td><c:out value="${chofer.fechaNacimiento}"/></td>
                                <td><c:out value="${chofer.telefonoCelular}"/></td>
                                <td><c:out value="${chofer.categoria}"/></td>
                                <td><c:out value="${chofer.usuario.nombreUsuario}"/></td>
                                <td class="text-end">
                                    <a href="${pageContext.request.contextPath}/admin/choferes?accion=editar&id=${chofer.idChofer}"
                                       class="btn btn-sm btn-outline-primary">Editar</a>

                                    <form action="${pageContext.request.contextPath}/admin/choferes" method="post" class="d-inline">
                                        <input type="hidden" name="accion" value="eliminar">
                                        <input type="hidden" name="id" value="${chofer.idChofer}">
                                        <button type="button" class="btn btn-sm btn-outline-danger"
                                                onclick="confirmarEliminar(this.form)">Eliminar</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script>
    // Pregunta antes de borrar y recien ahi envia el formulario
    function confirmarEliminar(formulario) {
        Swal.fire({
            title: '¿Eliminar el chofer?',
            text: 'También se elimina su usuario. Esta acción no se puede deshacer',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then(function (resultado) {
            if (resultado.isConfirmed) {
                formulario.submit();
            }
        });
    }
</script>

<jsp:include page="/WEB-INF/vistas/partes/pie.jsp"/>
