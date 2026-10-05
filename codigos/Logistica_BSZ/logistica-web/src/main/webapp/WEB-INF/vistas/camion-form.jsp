<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/vistas/partes/cabecera.jsp">
    <jsp:param name="titulo" value="Camión"/>
</jsp:include>
<jsp:include page="/WEB-INF/vistas/partes/menu-admin.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">

                    <h4 class="mb-4">
                        <c:choose>
                            <c:when test="${empty camion or camion.idCamion == 0}">Nuevo camión</c:when>
                            <c:otherwise>Editar camión</c:otherwise>
                        </c:choose>
                    </h4>

                    <form action="${pageContext.request.contextPath}/admin/camiones" method="post">
                        <input type="hidden" name="accion" value="guardar">
                        <input type="hidden" name="id" value="${empty camion ? 0 : camion.idCamion}">

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="marca" class="form-label">Marca</label>
                                <input type="text" class="form-control" id="marca" name="marca"
                                       value="<c:out value="${camion.marca}"/>" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="modelo" class="form-label">Modelo</label>
                                <input type="text" class="form-control" id="modelo" name="modelo"
                                       value="<c:out value="${camion.modelo}"/>" required>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="dominio" class="form-label">Dominio (patente)</label>
                            <input type="text" class="form-control" id="dominio" name="dominio"
                                   value="<c:out value="${camion.dominio}"/>" required>
                        </div>

                        <div class="mb-3">
                            <label for="toneladasMaximas" class="form-label">Toneladas máximas</label>
                            <input type="number" step="0.01" class="form-control" id="toneladasMaximas"
                                   name="toneladasMaximas" value="${camion.toneladasMaximas}" required>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="litrosTanque" class="form-label">Litros del tanque</label>
                                <input type="number" step="0.01" class="form-control" id="litrosTanque"
                                       name="litrosTanque" value="${camion.litrosTanque}" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="consumoLitrosKm" class="form-label">Consumo (litros por km)</label>
                                <input type="number" step="0.01" class="form-control" id="consumoLitrosKm"
                                       name="consumoLitrosKm" value="${camion.consumoLitrosKm}" required>
                            </div>
                        </div>

                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/admin/camiones" class="btn btn-secondary">Cancelar</a>
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/vistas/partes/pie.jsp"/>
