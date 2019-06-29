<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Yourfounds</title>
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
    <%@include file="navigation.jsp" %>

    <article>
        <h1>Welcome</h1>
        <p>With this cite you can easily keep track with your money.</p>
        <p>Enjoy.</p>
        <p>
            User: <security:authentication property="principal.username"/>
            <br/>
            Role: <security:authentication property="principal.authorities"/>
        </p>
    </article>
</section>

<footer>
    <p></p>
</footer>

</body>
</html>