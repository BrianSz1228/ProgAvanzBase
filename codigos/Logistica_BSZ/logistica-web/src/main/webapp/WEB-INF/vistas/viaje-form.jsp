<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/vistas/partes/cabecera.jsp">
    <jsp:param name="titulo" value="Nuevo viaje"/>
</jsp:include>
<jsp:include page="/WEB-INF/vistas/partes/menu-admin.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">

            <h4 class="mb-3">Nuevo viaje</h4>

            <%-- Paso 1: buscar el chofer por DNI --%>
            <div class="card shadow-sm mb-3">
                <div class="card-body">
                    <h6 class="card-title">1. Buscar chofer</h6>
                    <form action="${pageContext.request.contextPath}/admin/viajes" method="get" class="row g-2">
                        <input type="hidden" name="accion" value="buscar">
                        <div class="col-auto">
                            <input type="text" class="form-control" name="dni" placeholder="DNI del chofer"
                                   value="<c:out value="${param.dni}"/>" required>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-primary">Buscar</button>
                        </div>
                    </form>
                </div>
            </div>

            <%-- Paso 2: solo aparece si se encontro el chofer --%>
            <c:if test="${not empty chofer}">
                <div class="card shadow-sm mb-3">
                    <div class="card-body">
                        <h6 class="card-title">2. Datos del viaje</h6>

                        <p class="mb-1"><strong>Chofer:</strong>
                            <c:out value="${chofer.apellido}"/>, <c:out value="${chofer.nombre}"/>
                            (DNI <c:out value="${chofer.dni}"/>)</p>
                        <p class="mb-3"><strong>Categoría:</strong> <c:out value="${chofer.categoria}"/></p>

                        <c:choose>
                            <c:when test="${empty camiones}">
                                <div class="alert alert-warning mb-0">
                                    Este chofer no tiene camiones disponibles en este momento.
                                </div>
                            </c:when>
                            <c:otherwise>
                                <form action="${pageContext.request.contextPath}/admin/viajes" method="post">
                                    <input type="hidden" name="accion" value="guardar">
                                    <input type="hidden" name="idChofer" value="${chofer.idChofer}">
                                    <%-- El DNI sirve para volver a la busqueda si algo falla --%>
                                    <input type="hidden" name="dni" value="<c:out value="${chofer.dni}"/>">

                                    <div class="mb-3">
                                        <label for="idCamion" class="form-label">Camión disponible</label>
                                        <select class="form-select" id="idCamion" name="idCamion" required>
                                            <option value="">Elegí un camión</option>
                                            <c:forEach var="camion" items="${camiones}">
                                                <option value="${camion.idCamion}">
                                                    <c:out value="${camion}"/> -
                                                    <fmt:formatNumber value="${camion.toneladasMaximas}" maxFractionDigits="2"/> t
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label for="idOrigen" class="form-label">Origen</label>
                                            <select class="form-select" id="idOrigen" name="idOrigen" required>
                                                <option value="">Elegí el origen</option>
                                                <c:forEach var="destino" items="${destinos}">
                                                    <option value="${destino.idDestino}">
                                                        <c:out value="${destino.nombre}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <label for="idDestino" class="form-label">Destino</label>
                                            <select class="form-select" id="idDestino" name="idDestino" required>
                                                <option value="">Elegí el destino</option>
                                                <c:forEach var="destino" items="${destinos}">
                                                    <option value="${destino.idDestino}">
                                                        <c:out value="${destino.nombre}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="d-flex justify-content-end gap-2">
                                        <a href="${pageContext.request.contextPath}/admin/viajes" class="btn btn-secondary">Cancelar</a>
                                        <button type="submit" class="btn btn-primary">Cargar viaje</button>
                                    </div>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:if>

            <c:if test="${empty chofer}">
                <a href="${pageContext.request.contextPath}/admin/viajes" class="btn btn-secondary">Volver a la lista</a>
            </c:if>

        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/vistas/partes/pie.jsp"/>
