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
                <p>Category <b>${category.name}</b> is exist. Try to create with other name</p>
                <li><a href="/category/add">Add</a></li>
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