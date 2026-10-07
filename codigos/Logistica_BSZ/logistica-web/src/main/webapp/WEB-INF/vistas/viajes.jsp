<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/vistas/partes/cabecera.jsp">
    <jsp:param name="titulo" value="Viajes"/>
</jsp:include>
<jsp:include page="/WEB-INF/vistas/partes/menu-admin.jsp"/>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">Viajes</h3>
        <a href="${pageContext.request.contextPath}/admin/viajes?accion=nuevo" class="btn btn-primary">Nuevo viaje</a>
    </div>

    <c:choose>
        <c:when test="${empty viajes}">
            <div class="alert alert-info">No hay viajes cargados.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-striped table-hover bg-white align-middle">
                    <thead>
                        <tr>
                            <th>N°</th>
                            <th>Chofer</th>
                            <th>Camión</th>
                            <th>Origen</th>
                            <th>Destino</th>
                            <th>Km</th>
                            <th>Días</th>
                            <th>Tanques</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="viaje" items="${viajes}">
                            <tr>
                                <td>${viaje.idViaje}</td>
                                <td><c:out value="${viaje.chofer.apellido}"/>, <c:out value="${viaje.chofer.nombre}"/></td>
                                <td><c:out value="${viaje.camion}"/></td>
                                <td><c:out value="${viaje.origen}"/></td>
                                <td><c:out value="${viaje.destino}"/></td>
                                <td>${viaje.km}</td>
                                <td>${viaje.dias}</td>
                                <td>${viaje.tanques}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${viaje.estado == 'ASIGNADO'}">
                                            <span class="badge bg-secondary">${viaje.estado.descripcion}</span>
                                        </c:when>
                                        <c:when test="${viaje.estado == 'EN_CURSO'}">
                                            <span class="badge bg-primary">${viaje.estado.descripcion}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-success">${viaje.estado.descripcion}</span>
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

<jsp:include page="/WEB-INF/vistas/partes/pie.jsp"/>
