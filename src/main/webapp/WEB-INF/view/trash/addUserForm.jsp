<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>CSS Template</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <spring:url value="css/style.css" var="mainCSS"/>
    <link href="${mainCSS}" rel="stylesheet" />
    <style>
        .error{
            color: red;
        }
    </style>
</head>
<body>
<header>
    <h2>YourFounds</h2>
</header>



<section>
    <%@include file="../navigation.jsp" %>

    <article>
        <h1>Add User</h1>
        <form:form action="processUserForm" modelAttribute="user">

            Name: <form:input path="name"/> <form:errors path="name" cssClass="error"/> <br>
            Surname: <form:input path="surname"/> <form:errors path="surname" cssClass="error"/><br>
            E-mail: <form:input path="email"/> <form:errors path="email" cssClass="error"/><br>
            Password: <form:password path="password"/><form:errors path="password" cssClass="error"/><br>

            <input type="submit" value="Add"/>
        </form:form>
    </article>
</section>

<footer>
    <p></p>
</footer>

</body>
</html>