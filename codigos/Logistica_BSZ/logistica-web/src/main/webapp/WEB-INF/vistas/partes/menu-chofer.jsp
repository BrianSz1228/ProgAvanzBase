<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<nav class="navbar navbar-expand-lg navbar-dark mb-4" style="background-color: #0b2a5b;">
    <div class="container">

        <a class="navbar-brand" href="${pageContext.request.contextPath}/panel">Logística BSZ</a>

        <ul class="navbar-nav me-auto">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/chofer/viajes">Mis viajes</a></li>
        </ul>

        <span class="navbar-text me-3"><c:out value="${sessionScope.choferLogueado.nombreCompleto}"/></span>

        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit" class="btn btn-outline-light btn-sm">Cerrar sesión</button>
        </form>

    </div>
</nav>
