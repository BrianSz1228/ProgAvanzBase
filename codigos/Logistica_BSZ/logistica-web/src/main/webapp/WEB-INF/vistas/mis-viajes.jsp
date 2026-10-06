<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/vistas/partes/cabecera.jsp">
    <jsp:param name="titulo" value="Mis viajes"/>
</jsp:include>
<jsp:include page="/WEB-INF/vistas/partes/menu-chofer.jsp"/>

<div class="container">
    <h3 class="mb-3">Mis viajes</h3>

    <c:choose>
        <c:when test="${empty viajes}">
            <div class="alert alert-info">Todavía no tenés viajes asignados.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-striped table-hover bg-white align-middle">
                    <thead>
                        <tr>
                            <th>N°</th>
                            <th>Camión</th>
                            <th>Origen</th>
                            <th>Destino</th>
                            <th>Km</th>
                            <th>Días</th>
                            <th>Tanques</th>
                            <th>Estado</th>
                            <th class="text-end">Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="viaje" items="${viajes}">
                            <tr>
                                <td>${viaje.idViaje}</td>
                                <td><c:out value="${viaje.camion}"/></td>
                                <td><c:out value="${viaje.origen}"/></td>
                                <td><c:out value="${viaje.destino}"/></td>
                                <td>${viaje.km}</td>
                                <td>${viaje.dias}</td>
                                <td>${viaje.tanques}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${viaje.estado.name() == 'ASIGNADO'}">
                                            <span class="badge bg-secondary">${viaje.estado.descripcion}</span>
                                        </c:when>
                                        <c:when test="${viaje.estado.name() == 'EN_CURSO'}">
                                            <span class="badge bg-primary">${viaje.estado.descripcion}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-success">${viaje.estado.descripcion}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-end">
                                    <%-- El boton depende del estado del viaje --%>
                                    <c:choose>
                                        <c:when test="${viaje.estado.name() == 'ASIGNADO'}">
                                            <form action="${pageContext.request.contextPath}/chofer/viajes" method="post" class="d-inline">
                                                <input type="hidden" name="accion" value="iniciar">
                                                <input type="hidden" name="idViaje" value="${viaje.idViaje}">
                                                <button type="button" class="btn btn-sm btn-primary"
                                                        onclick="confirmarAccion(this.form, '¿Iniciar el viaje?')">Iniciar</button>
                                            </form>
                                        </c:when>
                                        <c:when test="${viaje.estado.name() == 'EN_CURSO'}">
                                            <form action="${pageContext.request.contextPath}/chofer/viajes" method="post" class="d-inline">
                                                <input type="hidden" name="accion" value="finalizar">
                                                <input type="hidden" name="idViaje" value="${viaje.idViaje}">
                                                <button type="button" class="btn btn-sm btn-success"
                                                        onclick="confirmarAccion(this.form, '¿Finalizar el viaje?')">Finalizar</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">-</span>
                                        </c:otherwise>
                                    </c:choose>
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
    // Pide confirmacion con el texto que recibe y recien ahi envia el formulario
    function confirmarAccion(formulario, titulo) {
        Swal.fire({
            title: titulo,
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Sí',
            cancelButtonText: 'Cancelar'
        }).then(function (resultado) {
            if (resultado.isConfirmed) {
                formulario.submit();
            }
        });
    }
</script>

<jsp:include page="/WEB-INF/vistas/partes/pie.jsp"/>
