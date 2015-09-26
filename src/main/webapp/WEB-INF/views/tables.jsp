<%--
  User: Tulskih Anton
  Date: 20.08.2015
  Time: 18:04
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:message code="tables.pageTitle" var="pageTitle"/>
<spring:message code="tables.sortCustomersBy" var="sortCustomersBy"/>
<spring:message code="tables.sortProductsBy" var="sortProductsBy"/>
<spring:message code="tables.sortButton" var="sortButton"/>
<spring:message code="tables.customersTable" var="customersTable"/>
<spring:message code="tables.yourCart" var="yourCart"/>
<spring:message code="tables.add" var="add"/>
<spring:message code="tables.addLinkTitle" var="addLinkTitle"/>
<spring:message code="tables.edit" var="edit"/>
<spring:message code="tables.editLinkTitle" var="editShoppingCart"/>
<spring:message code="customer.firstName" var="firstName"/>
<spring:message code="customer.lastName" var="lastName"/>
<spring:message code="customer.cardNumber" var="cardNumber"/>
<spring:message code="customer.quantity" var="quantity"/>
<spring:message code="customer.invoice" var="invoice"/>
<spring:message code="tables.productsTable" var="productsTable"/>
<spring:message code="product.name" var="name"/>
<spring:message code="product.description" var="description"/>
<spring:message code="product.price" var="price"/>
<spring:message code="logOutLink" var="logOut"/>

<html>
<head>
    <title>${pageTitle}</title>
    <link href="../../resources/css/main.css" type="text/css" rel="Stylesheet"/>
    <script src="../../resources/js/tables.js"></script>
</head>
<body onload="initialDefaultSortValues(
    'sortCustomersSelect', 'sortProductsSelect', '${sortCust}', '${sortProd}')">

    <div class="logOut">
        <a href="/editCustomers">${user.firstName} ${user.lastName}</a>
        <a href="${pageContext.request.contextPath}/signIn">(${logOut})</a>
    </div>

    <form method="POST" action="/tables">
        <p id="sortText1" ${hideFromUser}>${sortCustomersBy}</p>
        <p ${hideFromUser}>
            <select class="sortSelect" id="sortCustomersSelect"
                    name="sortCustomer">
                <option value="id">ID</option>
                <option value="lastName">${lastName}</option>
                <option value="invoice">${invoice}</option>
            </select>
        </p>
        <p id="sortText2">${sortProductsBy}</p>
        <p>
            <select class="sortSelect" id="sortProductsSelect"
                    name="sortProduct">
                <option value="id">ID</option>
                <option value="name">${name}</option>
                <option value="price">${price}</option>
            </select>
        </p>
        <p>
            <input id="sortButton" type="submit" value="${sortButton}"/>
        </p>
    </form>

    <form class="showCustProdForm">
            <table class="tables" border="1" cellpadding="3" cellspacing="1">
                <caption>
                    <span>
                        <c:if test="${tableLabel eq 'Customers Table'}">
                            ${customersTable}
                        </c:if>
                        <c:if test="${tableLabel eq 'Your Cart'}">
                            ${yourCart}
                        </c:if>
                        <sup>
                            <a class="editTableLink"
                            href="${pageContext.request.contextPath}/editCustomers"
                            ${hideFromUser}>${edit}
                            </a>
                        </sup>
                    </span>
                </caption>
                <thead>
                    <tr id="tr2">
                        <th ${hideFromUser}>ID</th>
                        <th>${firstName}</th>
                        <th>${lastName}</th>
                        <th>${cardNumber}</th>
                        <th>${quantity}</th>
                        <th>${invoice}</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="i" items="${customerList}">
                        <tr>
                            <td ${hideFromUser}>${i.id}</td>
                            <td class="tdCustFN">${i.firstName}</td>
                            <td class="tdCustLN">${i.lastName}</td>
                            <td>${i.cardNumber}</td>
                            <td>${i.quantity}</td>
                            <td>${i.invoice}</td>
                            <td class="hiddenTd">
                                <c:if test="${i.quantity == 0}">
                                    <a title="${addLinkTitle}"
                                       href="${pageContext.request.contextPath}/addToOrEditShoppingBasket?id=${i.id}">
                                        ${add}
                                    </a>
                                </c:if>
                                <c:if test="${i.quantity > 0}">
                                    <a title="${editShoppingCart}"
                                       href="${pageContext.request.contextPath}/addToOrEditShoppingBasket?id=${i.id}">
                                        ${edit}
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <table class="tables" id="productTable" border="1" cellpadding="3"
                   cellspacing="1">
                <caption>
                    <span id="productTableSpan">
                        ${productsTable}
                           <sup>
                           <a class="editTableLink"
                              href="${pageContext.request.contextPath}/editProducts"
                              ${hideFromUser}>${edit}
                           </a>
                           </sup>
                    </span>
                </caption>
                <thead>
                    <tr id="tr">
                        <th>ID</th>
                        <th class="tdProdName">${name}</th>
                        <th class="tdProdDesc">${description}</th>
                        <th>${price}</th>
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
    <div class="localeUrls">
        <a href="?lang=en">en</a> | <a href="?lang=ru">ru</a>
    </div>
</body>
</html>
