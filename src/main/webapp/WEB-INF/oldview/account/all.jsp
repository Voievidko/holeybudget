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
        <h1>All accounts</h1>
        <table>
            <tbody>
                <tr>
                    <th>Type</th>
                    <th>Summary</th>
                    <th>Description</th>
                </tr>

                <c:forEach var="tempAccount" items="${accounts}">
                    <c:url var="updateLink" value="/account/update">
                        <c:param name="accountId" value="${tempAccount.accountId}" />
                    </c:url>
                    <c:url var="deleteLink" value="/account/delete">
                        <c:param name="accountId" value="${tempAccount.accountId}" />
                    </c:url>
                    <tr>
                        <td>${tempAccount.type}</td>
                        <td>${tempAccount.summary}</td>
                        <td>${tempAccount.description}</td>
                        <td>
                            <a href="${updateLink}">Update</a>
                            <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this account?'))) return false">Delete</a>
                        </td>
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