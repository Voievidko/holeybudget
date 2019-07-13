<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="NotSpend - it's about money">
  <meta name="author" content="Roman Voievidko">

  <title>NotSpend - it's about money</title>

  <!-- Custom fonts for this template-->
  <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="../css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <%@include file="../sidebar.jsp" %>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <%@include file="../topbar.jsp"%>

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <div class="p-5">
            <div class="text-center">
              <h1 class="h4 text-gray-900 mb-4">Add expense</h1>
            </div>

            <form:form action="transferProcess" modelAttribute="accounts">
              FROM
              <div class="form-group">
                <select name="accountFrom" class="form-control">
                  <c:forEach var="tempAccount" items="${accounts}">
                    <option value=${tempAccount.accountId} class="dropdown-item">${tempAccount.type}</option>
                  </c:forEach>
                </select>
              </div>
              TO
              <div class="form-group">
                <select name="accountTo" class="form-control">
                  <c:forEach var="tempAccount" items="${accounts}">
                    <option value=${tempAccount.accountId} class="dropdown-item">${tempAccount.type}</option>
                  </c:forEach>
                </select>
              </div>
              Sum to transfer
              <div class="form-group">
                <input type="text" name="sum" class="form-control" placeholder="Enter summary">
              </div>

                <input type="submit" value="Transfer" class="btn btn-primary btn-user btn-block"/>
            </form:form>

            <hr>
          </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <%@include file="../footer.jsp" %>

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <%@include file="../logout-modal.jsp" %>

  <!-- Bootstrap core JavaScript-->
  <script src="../vendor/jquery/jquery.min.js"></script>
  <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="../js/sb-admin-2.min.js"></script>

</body>

</html>
