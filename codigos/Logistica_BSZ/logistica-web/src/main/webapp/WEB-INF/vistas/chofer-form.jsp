<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/vistas/partes/cabecera.jsp">
    <jsp:param name="titulo" value="Chofer"/>
</jsp:include>
<jsp:include page="/WEB-INF/vistas/partes/menu-admin.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-body">

                    <h4 class="mb-4">
                        <c:choose>
                            <c:when test="${empty chofer or chofer.idChofer == 0}">Nuevo chofer</c:when>
                            <c:otherwise>Editar chofer</c:otherwise>
                        </c:choose>
                    </h4>

                    <form action="${pageContext.request.contextPath}/admin/choferes" method="post">
                        <input type="hidden" name="accion" value="guardar">
                        <input type="hidden" name="id" value="${empty chofer ? 0 : chofer.idChofer}">

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <input type="text" class="form-control" id="nombre" name="nombre"
                                       value="<c:out value="${chofer.nombre}"/>" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="apellido" class="form-label">Apellido</label>
                                <input type="text" class="form-control" id="apellido" name="apellido"
                                       value="<c:out value="${chofer.apellido}"/>" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="dni" class="form-label">DNI</label>
                                <input type="text" class="form-control" id="dni" name="dni"
                                       value="<c:out value="${chofer.dni}"/>" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="fechaNacimiento" class="form-label">Fecha de nacimiento</label>
                                <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento"
                                       value="${chofer.fechaNacimiento}" required>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="telefonoCelular" class="form-label">Teléfono celular</label>
                                <input type="text" class="form-control" id="telefonoCelular" name="telefonoCelular"
                                       value="<c:out value="${chofer.telefonoCelular}"/>" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="idCategoria" class="form-label">Categoría</label>
                                <select class="form-select" id="idCategoria" name="idCategoria" required>
                                    <option value="">Elegí una categoría</option>
                                    <c:forEach var="categoria" items="${categorias}">
                                        <%-- Queda elegida la categoria que ya tenia el chofer --%>
                                        <option value="${categoria.idCategoria}"
                                                <c:if test="${chofer.categoria.idCategoria == categoria.idCategoria}">selected</c:if>>
                                            <c:out value="${categoria.nombre}"/> (hasta <c:out value="${categoria.toneladasMaximas}"/> t)
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <%-- El usuario y la clave solo se piden al crear el chofer --%>
                        <c:if test="${empty chofer or chofer.idChofer == 0}">
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="usuario" class="form-label">Usuario</label>
                                    <input type="text" class="form-control" id="usuario" name="usuario"
                                           value="<c:out value="${chofer.usuario.nombreUsuario}"/>" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="password" class="form-label">Contraseña</label>
                                    <input type="password" class="form-control" id="password" name="password" required>
                                </div>
                            </div>
                        </c:if>

                        <div class="mb-3">
                            <label class="form-label">Camiones que puede manejar</label>
                            <div class="form-text mb-2">
                                Solo se permiten camiones que alcance la categoría del chofer.
                            </div>
                            <c:forEach var="camion" items="${camiones}">
                                <%-- Quedan tildados los camiones que ya tenia autorizados --%>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="camiones"
                                           id="camion${camion.idCamion}" value="${camion.idCamion}"
                                           <c:if test="${idsCamiones.contains(camion.idCamion)}">checked</c:if>>
                                    <label class="form-check-label" for="camion${camion.idCamion}">
                                        <c:out value="${camion}"/> -
                                        <fmt:formatNumber value="${camion.toneladasMaximas}" maxFractionDigits="2"/> t
                                    </label>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/admin/choferes" class="btn btn-secondary">Cancelar</a>
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/vistas/partes/pie.jsp"/>
