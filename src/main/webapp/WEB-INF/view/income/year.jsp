<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="NotSpend - it's about money">
  <meta name="author" content="Roman Voievidko">

  <title>NotSpend - it's about money</title>

  <!-- Custom fonts for this template -->
  <link href="../../../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="../../../css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="../../../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <%@include file="../sidebar.jsp" %>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <%@include file="../topbar.jsp"%>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">Income</h1>
          <p class="mb-4">View your income during year.</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>Date</th>
                      <th>Sum</th>
                      <th>Currency</th>
                      <th>Category</th>
                      <th>Account</th>
                      <th>Comment</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th>Date</th>
                      <th>Sum</th>
                      <th>Currency</th>
                      <th>Category</th>
                      <th>Account</th>
                      <th>Comment</th>
                      <th>Action</th>
                    </tr>
                  </tfoot>
                  <tbody>
                    <c:forEach var="tempExpense" items="${currentYearIncome}">
                    <!-- construct an "delete" link with customer id -->
                                        <c:url var="deleteLink" value="/expense/delete">
                                          <c:param name="expenseId" value="${tempExpense.expenseId}" />
                                        </c:url>
                    <tr>
                      <td>
<%--                    <fmt:formatDate value="${tempExpense.date}" pattern="yyyy-MM-dd" />--%>
                        <javatime:parseLocalDate value="${tempExpense.date}" pattern="yyyy-MM-dd" />
                      </td>
                      <td>${tempExpense.sum}</td>
                      <td>${tempExpense.currency.getCode()}</td>
                      <td>${tempExpense.category.name}</td>
                      <td>${tempExpense.account.type}</td>
                      <td>${tempExpense.comment}</td>
                      <td>
                        <a href="${deleteLink}" data-toggle="modal" data-target="#deleteModal" onclick="window.id=${tempExpense.expenseId}">Delete</a>
                      </td>
                    </tr>
                    </c:forEach>
                  </tbody>

                  <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title" id="exampleModalLabelForDelete">Delete expense?</h5>
                          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">x</span>
                          </button>
                        </div>
                        <div class="modal-body">Expense will be deleted</div>
                        <div class="modal-footer">
                          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                          <a class="btn btn-primary" href="#" onclick="location.href='/expense/delete?expenseId='+window.id">Delete</a>
                        </div>
                      </div>
                    </div>
                  </div>

                </table>
              </div>
            </div>
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

  <!-- Page level plugins -->
  <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="../js/demo/datatables-demo.js"></script>

</body>

</html>
