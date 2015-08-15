<%--
  User: Acer
  Date: 21.07.2015
  Time: 17:17
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add to shopping basket</title>
    <link href="../../resources/css/main.css" type="text/css" rel="Stylesheet"/>
    <script src="../../resources/js/addToOrEditShoppingBasket.js"
            defer="defer"></script>
</head>
<body>
<div class="logOut"><a href="/editCustomers">${user.firstName}
    ${user.lastName}</a> <a id="logOutText" href="/signIn">(log out)</a>
</div>
<form class="addEditShoppingBasket">
  <table>
    <tbody>
    <tr>
      <td>
        Shopping basket of<br>
        <p id="label1">
            <b>${customer.firstName} ${customer.lastName}</b>:
        </p>
      </td>
        <td id="label2" colspan="2">
          Full product list:
        </td>
    </tr>
    <tr>
      <td>
        <form id="update">
        <select multiple="true" class="addOrEditProductsSelects" size="10"
                id="products">
          <c:forEach var="i" items="${customer.shoppingBasket}">
            <option value="${i.id}">${i.name}</option>
          </c:forEach>
        </select>
        </form>
      </td>
      <td>
        <table>
        <tbody>
        <tr>
          <td>
            <input class="addDeleteButtons"
                   type="button"
                   name="removeAllButton"
                   onclick="removeAllProducts(products)";
                   value=">>"/>
          </td>
        </tr>
        <tr>
          <td>
            <input class="addDeleteButtons"
                   type="button"
                   name="removeButton"
                   onclick="removeProducts(products)";
                   value=">"/>
          </td>
          </tr>
          <tr>
          <td>
            <input class="addDeleteButtons"
                   type="button"
                   name="addButton"
                   onclick="addProducts(productList, products)";
                   value="<"/>
          </td>
        </tr>
        <tr>
          <td>
            <input class="addDeleteButtons"
                   type="button"
                   name="addAllButton"
                   onclick="addAllProducts(productList, products)";
                   value="<<"/>
          </td>
        </tr>
        </tbody>
      </table>
      </td>
      <td>
        <select multiple="true" class="addOrEditProductsSelects" size="10"
                id="productList">
          <c:forEach var="i" items="${productList}">
            <option value="${i.id}">${i.name}</option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        <c:if test="${customer.quantity == 0}">
          <input class="addOrUpdateButton"
                 type="submit"
                 value="Add"
                 form="update"
                 onclick="updateCustomer(products,${customer.id})"
                  />
        </c:if>
        <c:if test="${customer.quantity > 0}">
          <input class="addOrUpdateButton"
                 type="submit"
                 value="Update"
                 form="update"
                 onclick="updateCustomer(products,${customer.id})"
                  />
        </c:if>
      </td>
    </tr>
    </tbody>
  </table>
</form>
<a id="backToTablesUrl" href=${pageContext.request.contextPath}/tables>Back
  to tables</a>
</body>
</html>
