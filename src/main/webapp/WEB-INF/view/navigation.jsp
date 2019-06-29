<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<nav>
    <ul>
        <li><a href="/">Home</a></li>
        <p1>Accounts<br></p1>
        <li><a href="/account/add">Add</a></li>
        <li><a href="/account/all">Show all</a></li>
        <li><a href="/account/transfer">Transfer money</a></li>
        <p1>Categories<br></p1>
        <li><a href="/category/add">Add</a></li>
        <li><a href="/category/all">Show all</a></li>
        <p1><label>Expenses</label><br></p1>
        <li><a href="/expense/add">Add</a></li>
        <li><a href="/expense/all">Show all</a></li>
        <li><a href="/expense/currentmonth">Current month</a></li>
        <p1><label>Users</label><br></p1>
        <li><a href="/user/all">Show all</a></li>
        <form:form action="/logout" method="post">
            <input type="submit" value="Logout">
        </form:form>
    </ul>
</nav>
