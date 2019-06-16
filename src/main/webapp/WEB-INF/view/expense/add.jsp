<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

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
        <h1>Add expense</h1>
        <form:form action="addProcess" modelAttribute="expense">
            <table>
                <tbody>
                <tr>
                    <td><label>Sum:</label></td>
                    <td><form:input path="sum"/> <form:errors path="sum" cssClass="error"/></td>
                </tr>
                <tr>
                    <td><label>Category:</label></td>
                    <td>
                        <select name="categoryId">
                            <c:forEach var="tempCategory" items="${categories}">
                                <option value=${tempCategory.categoryId}>${tempCategory.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>Account:</label></td>
                    <td>
                        <select name="accountId">
                            <c:forEach var="tempAccount" items="${accounts}">
                                <option value=${tempAccount.accountId}>${tempAccount.type}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>Date:</label></td>
                    <td>
                        <form:input type="date" path="date" pattern="dd/MM/yyyy"/>

                    </td>
                </tr>
<%--                <tr>--%>
<%--                    <td><label>Time:</label></td>--%>
<%--                    <td><form:input path="time"/></td>--%>
<%--                </tr>--%>
                <tr>
                    <td><label>Comment:</label></td>
                    <td><form:input path="comment"/></td>
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