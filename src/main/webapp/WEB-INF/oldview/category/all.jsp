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
        <h1>All categories</h1>
        <table>
            <tbody>
            <tr>
                <th>Name</th>
                <th>Description</th>
            </tr>

            <c:forEach var="tempCategory" items="${categories}">
                <!-- construct an "update" link with customer id -->
                <c:url var="updateLink" value="/category/update">
                    <c:param name="categoryId" value="${tempCategory.categoryId}" />
                </c:url>

                <!-- construct an "delete" link with customer id -->
                <c:url var="deleteLink" value="/category/delete">
                    <c:param name="categoryId" value="${tempCategory.categoryId}" />
                </c:url>
                <tr>
                    <td>${tempCategory.name}</td>
                    <td>${tempCategory.description}</td>
                    <td>

                        <a href="${updateLink}">Update</a>
                        <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this category?'))) return false">Delete</a>
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