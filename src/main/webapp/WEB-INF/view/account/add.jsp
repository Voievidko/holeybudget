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
        <h1>Add account</h1>
        <form:form action="addProcess" modelAttribute="account">
            <table>
                <tbody>
                <tr>
                    <td><label>Type:</label></td>
                    <td><form:input path="type"/></td>
                </tr>
                <tr>
                    <td><label>Started sum:</label></td>
                    <td><form:input path="summary"/></td>
                </tr>
                <tr>
                    <td><label>Description:</label></td>
                    <td><form:input path="description"/></td>
                </tr>
                </tbody>
            </table>
            <input type="submit" value="Add"/>
        </form:form>
    </article>
</section>

<footer>
    <p></p>
</footer>

</body>
</html>