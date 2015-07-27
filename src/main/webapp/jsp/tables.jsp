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
    <link href="../css/main.css" type="text/css" rel="Stylesheet"/>
</head>
<body onload="initialDefaultSortValues(sortCustomersSelect, sortProductsSelect)">
    <p>${customer.firstName} ${customer.lastName} <a id="logOutText" href="">
        (log out)</a> </p>
    <p id="sortText1">Sort customer table by </p>
    <p id="sortText2">Sort product table by </p>
    <p><select class="sortSelect" id="sortCustomersSelect">
        <option value="id">ID</option>
        <option value="lastName">Last name</option>
        <option value="invoice">Invoice</option>
    </select></p>
    <p><select class="sortSelect" id="sortProductsSelect">
        <option value="id">ID</option>
        <option value="name">Name</option>
        <option value="price">Price</option>
    </select></p>
    <p><input id="sortButton" type="submit" value="Sort"
              onclick="sortTables(sortCustomersSelect,
    sortProductsSelect)"></p>
    <a id="hrefEditCustomerTable"
       href="${pageContext.request.contextPath}/jsp/editCustomers">edit
    </a>
    <table class="tables" id="customerTable" border="1" cellpadding="3" cellspacing="1">
        <caption><span style="font-size:20px;">Customers Table</span></caption>
        <thead>
            <tr>
                <th>ID</th>
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
                    <td>${i.id}</td>
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
    <a id="hrefEditProductTable" href="${pageContext.request.contextPath}/jsp/editProducts">edit
    </a>
    <table class="tables" id="productTable" border="1" cellpadding="3"
           cellspacing="1">
        <caption><span style="font-size:20px;">Products Table</span></caption>
        <thead>
            <tr>
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

</body>
<script>
    function sortTables(selectCustomers, selectProducts) {
        for (var i = 0; i < selectCustomers.length; i++) {
            if (selectCustomers.options[i].selected) {
                sortCust = selectCustomers.options[i].value;
            }
        }
        for (var i = 0; i < selectProducts.length; i++) {
            if (selectProducts.options[i].selected) {
                sortProd = selectProducts.options[i].value;
            }
        }

        window.location =
                "${pageContext.request.contextPath}/tables?sortCust="
        + sortCust + "&sortProd=" + sortProd;
    }

    function initialDefaultSortValues(selectCustomers, selectProducts) {
        var sortCust = "${sortCust}";
        var sortProd = "${sortProd}";
        for(var i, j = 0; i = selectCustomers.options[j]; j++) {
            if(i.value == sortCust) {
                selectCustomers.selectedIndex = j;
                break;
            }
        }
        for(var i, j = 0; i = selectProducts.options[j]; j++) {
            if(i.value == sortProd) {
                selectProducts.selectedIndex = j;
                break;
            }
        }
    }
</script>
</html>
