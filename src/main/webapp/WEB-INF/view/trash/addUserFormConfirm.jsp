<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>CSS Template</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <spring:url value="css/style.css" var="mainCSS"/>
    <link href="${mainCSS}" rel="stylesheet" />
</head>
<body>
<header>
    <h2>YourFounds</h2>
</header>

<section>
    <%@include file="../navigation.jsp" %>

    <article>
        <h1>Success</h1>
        <p>User ${user.name} ${user.surname} was added</p>
    </article>
</section>

<footer>
    <p></p>
</footer>

</body>
</html>