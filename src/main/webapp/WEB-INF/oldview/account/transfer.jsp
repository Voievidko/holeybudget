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
        <h1>Transfer money between accounts</h1>
        <form:form action="transferProcess" modelAttribute="accounts">
            <table>
                <tbody>
                <tr>
                    From

                        <select name="accountFrom">
                            <c:forEach var="tempAccount" items="${accounts}">
                                <option value=${tempAccount.accountId}>${tempAccount.type}</option>
                            </c:forEach>
                        </select>

                    to
                        <select name="accountTo">
                            <c:forEach var="tempAccount" items="${accounts}">
                                <option value=${tempAccount.accountId}>${tempAccount.type}</option>
                            </c:forEach>
                        </select>
                </tr>
                <tr>
                    Sum to transfer
                    <input type="text" name="sum">
                </tr>
                </tbody>
            </table>
            <input type="submit" value="Transfer"/>
        </form:form>
    </article>

    <%@include file="../sums.jsp" %>
</section>

<footer>
    <p></p>
</footer>

</body>
</html>