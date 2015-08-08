<%--
  User: Tulskih Anton
  Date: 20.07.2015
  Time: 18:04
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tables</title>
    <link href="../resources/css/main.css" type="text/css" rel="Stylesheet"/>
    <script src="../resources/js/tables.js" defer="defer"></script>
</head>
<body onload="initialDefaultSortValues(
        sortCustomersSelect, sortProductsSelect, ${sortCust}, ${sortProd})">
    <div class="logOut"><a href="/editCustomers">${user.firstName}
     ${user.lastName}</a> <a
            id="logOutText" href="${pageContext.request.contextPath}/signIn">(log out)</a> </div>
    <form method="post">
    <p id="sortText1" ${hideFromUser}>Sort customers by </p>
        <p ${hideFromUser}><select class="sortSelect" id="sortCustomersSelect"
                    name="sortCustomer">
            <option value="id">ID</option>
            <option value="lastName">Last name</option>
            <option value="invoice">Invoice</option>
        </select></p>
    <p id="sortText2">Sort products by </p>
    <p><select class="sortSelect" id="sortProductsSelect" name="sortProduct">
        <option value="id">ID</option>
        <option value="name">Name</option>
        <option value="price">Price</option>
    </select></p>
    <p><input id="sortButton" type="submit" value="Sort"/></p>
    </form>
    <a id="hrefEditCustomerTable"
       href="${pageContext.request.contextPath}/editCustomers" ${hideFromUser}>edit
    </a>
    <a id="hrefEditProductTable"
       href="${pageContext.request.contextPath}/editProducts"
       ${hideFromUser}>edit
    </a>
    <form class="showCustProdForm">
        <table class="tables" border="1" cellpadding="3" cellspacing="1">
            <caption><span>${tableLabel}</span></caption>
            <thead>
                <tr id="tr2">
                    <th ${hideFromUser}>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Card Number</th>
                    <th>Quantity</th>
                    <th>Invoice</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="i" items="${customerList}">
                    <tr>
                        <td ${hideFromUser}>${i.id}</td>
                        <td>${i.firstName}</td>
                        <td>${i.lastName}</td>
                        <td>${i.cardNumber}</td>
                        <td>${i.quantity}</td>
                        <td>${i.invoice}</td>
                        <td class="hiddenTd">
                            <c:if test="${i.quantity == 0}">
                                <a title="Add products to the shopping basket"
                                   href="${pageContext.request.contextPath}/addToOrEditShoppingBasket?id=${i.id}">
                                    add
                                </a>
                            </c:if>
                            <c:if test="${i.quantity > 0}">
                                <a title="Edit shopping basket"
                                   href="${pageContext.request.contextPath}/addToOrEditShoppingBasket?id=${i.id}">
                                    edit
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <table class="tables" id="productTable" border="1" cellpadding="3"
               cellspacing="1">
            <caption><span>Products Table</span></caption>
            <thead>
                <tr id="tr">
                    <th>ID</th>
                    <th width="138">Name</th>
                    <th width="138">Description</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="i" items="${productList}">
                    <tr>
                        <td>${i.id}</td>
                        <td>${i.name}</td>
                        <td>${i.description}</td>
                        <td>${i.price}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</body>
</html>
