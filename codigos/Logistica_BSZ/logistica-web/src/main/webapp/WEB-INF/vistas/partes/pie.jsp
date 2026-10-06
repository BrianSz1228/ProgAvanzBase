<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<footer class="text-center text-white py-3 mt-auto" style="background-color: #0b2a5b;">
    &copy; 2026 Logística BSZ. Todos los derechos reservados a "El Brian".
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<%-- Mensaje que manda un servlet con forward (viaja en la request) --%>
<c:if test="${not empty mensaje}">
    <script>
        Swal.fire({
            icon: '${tipoMensaje}',
            text: '<c:out value="${mensaje}"/>'
        });
    </script>
</c:if>

<%-- Mensaje que deja un servlet antes de un redirect (viaja en la sesion) --%>
<c:if test="${not empty sessionScope.mensajeFlash}">
    <script>
        Swal.fire({
            icon: '${sessionScope.tipoFlash}',
            text: '<c:out value="${sessionScope.mensajeFlash}"/>'
        });
    </script>
    <c:remove var="mensajeFlash" scope="session"/>
    <c:remove var="tipoFlash" scope="session"/>
</c:if>

</body>
</html>
