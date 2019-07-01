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
                <p>Category <b>${categoryToDelete.name}</b> can't be deleted because it's used by some of your expenses.<br>
                    Anyway, if you want to delete it. You can reassign all your expenses to other category</p>
            </tr>
            <tr>
                Assign all wastes from <b>${categoryToDelete.name}</b> to <br>
            </tr>
            <tr>
                <td>
                    to existing category
                </td>
                <td>
                    to new category
                </td>
            </tr>
            <tr>
                <td width="50%">

                    <form action="transferToExistCategory">
                        <select name="categoryId">
                            <c:forEach var="tempCategory" items="${categories}">
                                <option value=${tempCategory.categoryId}>${tempCategory.name}</option>
                            </c:forEach>
                        </select>
                        <br>
                        <input type="submit" value="Submit">

                        <input type="hidden" name="categoryToDelete" value=${categoryToDelete.categoryId} hidden="true">
                    </form>
                </td>
                <td width="50%">
                    <form:form action="transferToNewCategory" modelAttribute="category">
                        <table>
                            <tbody>
                            <tr>
                                <td><label>Name:</label></td>
                                <td><form:input path="name"/></td>
                            </tr>
                            <tr>
                                <td><label>Description:</label></td>
                                <td><form:input path="description"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <input type="submit" value="Submit"/>
                        <input type="hidden" name="categoryToDelete" value=${categoryToDelete.categoryId} hidden="true">
                    </form:form>
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