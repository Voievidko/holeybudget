<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        <h1>${expenseName}</h1>
        <table>
            <tbody>
                <tr>
                    <th>Date</th>
                    <th>Sum</th>
                    <th>Category</th>
                    <th>Account</th>
                    <th>Comment</th>
                </tr>

                <c:forEach var="tempExpense" items="${expenses}">

                    <c:url var="deleteLink" value="/expense/delete">
                        <c:param name="expenseId" value="${tempExpense.expenseId}" />
                    </c:url>
                    <tr>
                        <td>
                            <fmt:formatDate value="${tempExpense.date}" pattern="yyyy-MM-dd" />
                        </td>

                        <td>${tempExpense.sum}</td>
                        <td>${tempExpense.category.name}</td>
                        <td>${tempExpense.account.type}</td>
                        <td>${tempExpense.comment}</td>
                        <td>
                            <a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this expense?'))) return false">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <th>Total</th>
                    <th>${totalSum}</th>
                    <th></th>
                    <th></th>
                </tr>
            </tbody>
        </table>
    </article>

    <%@include file="../sums.jsp" %>
</section>

<footer>
    <p></p>
</footer>

</body>
</html>