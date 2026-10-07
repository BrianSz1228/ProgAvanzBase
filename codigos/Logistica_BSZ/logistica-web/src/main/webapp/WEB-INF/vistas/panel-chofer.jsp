<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/vistas/partes/cabecera.jsp">
    <jsp:param name="titulo" value="Panel"/>
</jsp:include>
<jsp:include page="/WEB-INF/vistas/partes/menu-chofer.jsp"/>

<div class="container">
    <h3 class="mb-4">Hola, <c:out value="${sessionScope.choferLogueado.nombre}"/></h3>

    <div class="card mb-3">
        <div class="card-body">
            <p class="mb-1"><strong>Categoría:</strong> <c:out value="${sessionScope.choferLogueado.categoria}"/></p>
            <p class="mb-1"><strong>Camiones habilitados:</strong></p>
            <ul class="mb-0">
                <c:forEach var="camion" items="${sessionScope.choferLogueado.camiones}">
                    <li><c:out value="${camion}"/></li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/chofer/viajes" class="btn btn-primary">Ver mis viajes</a>
</div>

<jsp:include page="/WEB-INF/vistas/partes/pie.jsp"/>
