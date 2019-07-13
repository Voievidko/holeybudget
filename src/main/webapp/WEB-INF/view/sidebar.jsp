<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">NotSpend</div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <!-- Nav Item - Dashboard -->
    <li class="nav-item active">
        <a class="nav-link" href="/">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span></a>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider">

    <!-- Heading -->
    <div class="sidebar-heading">
        Interface
    </div>

    <li class="nav-item">
        <a class="nav-link" href="/expense/add">
            <i class="fas fa-fw fa-minus"></i>
            <span>Expense</span></a>
    </li>

    <li class="nav-item">
        <a class="nav-link" href="/expense/income">
            <i class="fas fa-fw fa-plus"></i>
            <span>Income</span></a>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider">

    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePagesAccount" aria-expanded="true" aria-controls="collapsePage">
            <i class="fas fa-fw fa-folder"></i>
            <span>Account</span>
        </a>
        <div id="collapsePagesAccount" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/account/add">Add</a>
                <a class="collapse-item" href="/account/all">Show all</a>
                <a class="collapse-item" href="/account/transfer">Transfer money</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true" aria-controls="collapsePage">
            <i class="fas fa-fw fa-folder"></i>
            <span>Categories</span>
        </a>
        <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/category/add">Add</a>
                <a class="collapse-item" href="/category/allincome">All income</a>
                <a class="collapse-item" href="/category/allexpense">All expense</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePagesExpense" aria-expanded="true" aria-controls="collapsePage">
            <i class="fas fa-fw fa-folder"></i>
            <span>Expense</span>
        </a>
        <div id="collapsePagesExpense" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/expense/add">Add</a>
                <a class="collapse-item" href="/expense/currentmonth">This month</a>
                <a class="collapse-item" href="/expense/all">Show all</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePagesCurrency" aria-expanded="true" aria-controls="collapsePage">
            <i class="fas fa-fw fa-folder"></i>
            <span>Currency</span>
        </a>
        <div id="collapsePagesCurrency" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/currency/all">Show all</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Charts -->
    <li class="nav-item">
        <a class="nav-link" href="/statistic/all">
            <i class="fas fa-fw fa-chart-area"></i>
            <span>Statistic</span></a>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider d-none d-md-block">

    <!-- Sidebar Toggler (Sidebar) -->
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>

</ul>
        <!-- End of Sidebar -->