<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/vistas/partes/cabecera.jsp">
    <jsp:param name="titulo" value="Camiones"/>
</jsp:include>
<jsp:include page="/WEB-INF/vistas/partes/menu-admin.jsp"/>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">Camiones</h3>
        <a href="${pageContext.request.contextPath}/admin/camiones?accion=nuevo" class="btn btn-primary">Nuevo camión</a>
    </div>

    <c:choose>
        <c:when test="${empty camiones}">
            <div class="alert alert-info">No hay camiones cargados.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-striped table-hover bg-white align-middle">
                    <thead>
                        <tr>
                            <th>Marca</th>
                            <th>Modelo</th>
                            <th>Dominio</th>
                            <th>Toneladas</th>
                            <th>Tanque (L)</th>
                            <th>Consumo (L/km)</th>
                            <th class="text-end">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="camion" items="${camiones}">
                            <tr>
                                <td><c:out value="${camion.marca}"/></td>
                                <td><c:out value="${camion.modelo}"/></td>
                                <td><c:out value="${camion.dominio}"/></td>
                                <td><fmt:formatNumber value="${camion.toneladasMaximas}" maxFractionDigits="2"/></td>
                                <td><fmt:formatNumber value="${camion.litrosTanque}" maxFractionDigits="2"/></td>
                                <td><fmt:formatNumber value="${camion.consumoLitrosKm}" maxFractionDigits="2"/></td>
                                <td class="text-end">
                                    <a href="${pageContext.request.contextPath}/admin/camiones?accion=editar&id=${camion.idCamion}"
                                       class="btn btn-sm btn-outline-primary">Editar</a>

                                    <form action="${pageContext.request.contextPath}/admin/camiones" method="post" class="d-inline">
                                        <input type="hidden" name="accion" value="eliminar">
                                        <input type="hidden" name="id" value="${camion.idCamion}">
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
    // pregunta antes de eliminar y recien ahi se envia el formulario
    function confirmarEliminar(formulario) {
        Swal.fire({
            title: '¿Eliminar el camión?',
            text: 'Esta acción no se puede deshacer',
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
