<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>CSS Template</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <spring:url value="/css/style.css" var="mainCSS"/>
    <link href="${mainCSS}" rel="stylesheet" />
</head>
<body>
<header>
    <h2>YourFounds</h2>
</header>

<section>
    <article>
        <h1>Welcome</h1>
        <p>To keep track with your money, please login</p>
        <c:if test="${param.error != null}">
            <i>Sorry! You entered invalid username or password</i>
        </c:if>
        <form:form action="/authenticate" method="post">
            <p>
                Username: <input type="text" name="username"/>
            </p>
            <p>
                Password: <input type="password" name="password"/>
            </p>
            <input type="submit" value="Login"/>
        </form:form>
    </article>
</section>

<footer>
    <p></p>
</footer>

</body>
</html>
