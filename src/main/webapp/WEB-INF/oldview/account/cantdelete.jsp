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
        <h1>Can't delete</h1>
        <table>
            <tr>
                <p>Account <b>${accountToDelete.type}</b> can't be deleted because it's used by some of your expenses.<br>
                    Anyway, if you want to delete it. You can reassign all your expenses to other account</p>
            </tr>
            <tr>
                Assign all wastes from <b>${categoryToDelete.name}</b> to <br>
            </tr>
            <tr>
                <td>
                    to existing account
                </td>
            </tr>
            <tr>
                <td>

                    <form action="transferToExistAccount">
                        <select name="accountId">
                            <c:forEach var="tempAccount" items="${accounts}">
                                <option value=${tempAccount.accountId}>${tempAccount.type}</option>
                            </c:forEach>
                        </select>
                        <br>
                        <input type="submit" value="Submit">

                        <input type="hidden" name="accountToDelete" value=${accountToDelete.accountId} hidden="true">
                    </form>
                </td>
            </tr>
        </table>
        <h1></h1>
    </article>
</section>

<footer>
    <p></p>
</footer>

</body>
</html>