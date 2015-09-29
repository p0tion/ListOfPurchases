<%--
  User: Acer
  Date: 21.07.2015
  Time: 17:17
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:message code="shoppingCart.pageTitle.add" var="addPageTitle"/>
<spring:message code="shoppingCart.pageTitle.edit" var="editPageTitle"/>
<spring:message code="tables.yourCart" var="yourCart"/>
<spring:message code="shoppingCart.shoppingCartOf" var="shoppingCartOf"/>
<spring:message code="shoppingCart.shoppingCartOf.title"
                var="shoppingCartSelectTitle"/>
<spring:message code="shoppingCart.fullProductList" var="fullProductList"/>
<spring:message code="shoppingCart.fullProductList.title"
                var="productListSelectTitle"/>
<spring:message code="shoppingCart.moveAllFrom.title" var="moveAllFromTitle"/>
<spring:message code="shoppingCart.moveSelectedFrom.title"
                var="moveSelectedFromTitle"/>
<spring:message code="shoppingCart.moveSelectedTo.title"
                var="moveSelectedToTitle"/>
<spring:message code="shoppingCart.moveAllTo.title" var="moveAllToTitle"/>
<spring:message code="shoppingCart.addButton" var="addButton"/>
<spring:message code="shoppingCart.addButton.title" var="addButtonTitle"/>
<spring:message code="shoppingCart.updateButton" var="updateButton"/>
<spring:message code="shoppingCart.updateButton.title" var="updateButtonTitle"/>
<spring:message code="logOutLink" var="logOutLink"/>
<spring:message code="logOutLink.title" var="logOutLinkTitle"/>
<spring:message code="editCustomersLink.title" var="editCustomersLinkTitle"/>
<spring:message code="backToMainPageLink" var="backToMainPageLink"/>
<spring:message code="backToMainPageLink.title" var="backToMainPageLinkTitle"/>

<html>
<head>
    <title>
        <c:if test="${pageTtl eq 'add'}">
            ${addPageTitle}
        </c:if>
        <c:if test="${pageTtl eq 'edit'}">
            ${editPageTitle}
        </c:if>
    </title>
    <link href="../../resources/css/main.css" type="text/css" rel="Stylesheet"/>
    <script src="../../resources/js/addToOrEditShoppingBasket.js"
            defer="defer"></script>
</head>
<body>
<div class="logOut">
    <a href="${pageContext.request.contextPath}/editCustomers"
       title="${editCustomersLinkTitle}">
        ${user.firstName} ${user.lastName}
    </a>
    <a id="logOutText"
       href="${pageContext.request.contextPath}/signIn"
       title="${logOutLinkTitle}">
        (${logOutLink})
    </a>
</div>
<form class="addEditShoppingBasket">
  <table>
    <tbody>
    <tr>
        <td>
            <c:if test="${customer.id == user.id}">
                <p class="label2">${yourCart}:</p>
            </c:if>
            <c:if test="${customer.id != user.id}">
                ${shoppingCartOf}<br>
                <p id="label1">${customer.firstName}
                 ${customer.lastName}:</p>
            </c:if>
        </td>
        <td>
        </td>
        <td class="label2">
            ${fullProductList}
        </td>
    </tr>
    <tr>
      <td>
        <select multiple class="addOrEditProductsSelects" size="10"
                id="products" title="${shoppingCartSelectTitle}">
          <c:forEach var="i" items="${customer.shoppingBasket}">
            <option value="${i.id}">${i.name}</option>
          </c:forEach>
        </select>
      </td>
      <td>
        <table>
        <tbody>
        <tr>
          <td>
            <input class="addDeleteButtons"
                   type="button"
                   name="removeAllButton"
                   title="${moveAllFromTitle}"
                   onclick="removeAllProducts(products);"
                   value=">>"/>
          </td>
        </tr>
        <tr>
          <td>
            <input class="addDeleteButtons"
                   type="button"
                   name="removeButton"
                   title="${moveSelectedFromTitle}"
                   onclick="removeProducts(products);"
                   value=">"/>
          </td>
          </tr>
          <tr>
          <td>
            <input class="addDeleteButtons"
                   type="button"
                   name="addButton"
                   title="${moveAllToTitle}"
                   onclick="addProducts(productList, products);"
                   value="<"/>
          </td>
        </tr>
        <tr>
          <td>
            <input class="addDeleteButtons"
                   type="button"
                   name="addAllButton"
                   title="${moveAllToTitle}"
                   onclick="addAllProducts(productList, products);"
                   value="<<"/>
          </td>
        </tr>
        </tbody>
      </table>
      </td>
      <td>
        <select multiple class="addOrEditProductsSelects" size="10"
                id="productList" title="${productListSelectTitle}">
          <c:forEach var="i" items="${productList}">
            <option value="${i.id}">${i.name}</option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        <c:if test="${pageTtl eq 'add'}">
          <input class="addOrUpdateButton"
                 type="button"
                 value="${addButton}"
                 title="${addButtonTitle}"
                 onclick="updateCustomer(products,${customer.id})"
                  />
        </c:if>
        <c:if test="${pageTtl eq 'edit'}">
          <input class="addOrUpdateButton"
                 type="button"
                 value="${updateButton}"
                 title="${updateButtonTitle}"
                 onclick="updateCustomer(products,${customer.id})"
                  />
        </c:if>
      </td>
    </tr>
    </tbody>
  </table>
</form>
<p>
    <a id="backToTablesUrl"
       title="${backToMainPageLinkTitle}"
       href=${pageContext.request.contextPath}/tables>${backToMainPageLink}
    </a>
</p>
<div class="localeUrls">
    <a href="?id=${customer.id}&lang=en">en</a> |
    <a href="?id=${customer.id}&lang=ru">ru</a>
</div>
</body>
</html>
