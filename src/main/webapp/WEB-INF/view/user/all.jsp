<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<%@include file="../head.jsp" %>
<body>
<header>
    <h2>YourFounds</h2>
</header>

<section>
    <%@include file="../navigation.jsp" %>

    <article>
        <h1>All users</h1>
        <table>
            <tbody>
                <tr>
                    <th>Id</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Password</th>
                </tr>

                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.email}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.password}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </article>
</section>

<footer>
    <p></p>
</footer>

</body>
</html>