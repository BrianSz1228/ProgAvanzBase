<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/vistas/partes/cabecera.jsp">
    <jsp:param name="titulo" value="Panel"/>
</jsp:include>
<jsp:include page="/WEB-INF/vistas/partes/menu-admin.jsp"/>

<div class="container">
    <h3 class="mb-4">Panel de administración</h3>

    <div class="row g-3">
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">Choferes</h5>
                    <p class="card-text">Alta, modificación y baja de choferes.</p>
                    <a href="${pageContext.request.contextPath}/admin/choferes" class="btn btn-primary">Ir a choferes</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">Camiones</h5>
                    <p class="card-text">Alta, modificación y baja de camiones.</p>
                    <a href="${pageContext.request.contextPath}/admin/camiones" class="btn btn-primary">Ir a camiones</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">Viajes</h5>
                    <p class="card-text">Cargar viajes y ver los ya asignados.</p>
                    <a href="${pageContext.request.contextPath}/admin/viajes" class="btn btn-primary">Ir a viajes</a>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/vistas/partes/pie.jsp"/>
