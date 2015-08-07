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
    <link href="../css/main.css" type="text/css" rel="Stylesheet"/>
  <style type="text/css">
    input
    {
      width: 80px;
    }
    select
    {
      min-width: 150px;
    }
  </style>
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
        <p  style=" margin: 0; text-align: right"><b>${customer.firstName}
        ${customer.lastName}</b>:</p>
      </td>
        <td colspan="2" style="text-align: right;">
          Full product list:
        </td>
    </tr>
    <tr>
      <td>
        <form id="update">
        <select multiple="true" id="products" size="10">
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
            <input class="button"
                   type="button"
                   style="margin-top: 0"
                   name="removeAllButton"
                   onclick="removeAllProducts(products)";
                   value=">>"/>
          </td>
        </tr>
        <tr>
          <td>
            <input class="button"
                   type="button"
                   style="margin-top: 0"
                   name="removeButton"
                   onclick="removeProducts(products)";
                   value=">"/>
          </td>
          </tr>
          <tr>
          <td>
            <input class="button"
                   type="button"
                   style="margin-top: 0"
                   name="addButton"
                   onclick="addProducts(productList, products)";
                   value="<"/>
          </td>
        </tr>
        <tr>
          <td>
            <input class="button"
                   type="button"
                   style="margin-top: 0"
                   name="addAllButton"
                   onclick="addAllProducts(productList, products)";
                   value="<<"/>
          </td>
        </tr>
        </tbody>
      </table>
      </td>
      <td>
        <select multiple="true" id="productList" size="10">
          <c:forEach var="i" items="${productList}">
            <option value="${i.id}">${i.name}</option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td>
        <c:if test="${customer.quantity == 0}">
          <input class="button"
                 type="submit"
                 style="margin-left: 30px;
                        margin-top: 0"
                 value="Add"
                 form="update"
                 onclick="updateCustomer(products)"
                  />
        </c:if>
        <c:if test="${customer.quantity > 0}">
          <input class="button"
                 type="submit"
                 style="margin-left: 30px;
                        margin-top: 0"
                 value="Update"
                 form="update"
                 onclick="updateCustomer(products)"
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
<script>
  function removeAllProducts(select)
  {
    var i;
    for(i=select.options.length-1;i>=0;i--)
    {
      select.remove(i);
    }
  }

  function removeProducts(select) {
    var i;
    for(i=select.options.length-1;i>=0;i--)
    {
      if(select.options[i].selected)
        select.remove(i);
    }
  }

  function addProducts(selectFrom, selectTo)
  {
    for (i = 0; i < selectFrom.length; i++){
      if (selectFrom.options[i].selected){
        var isEqual = false;
        for (j = 0; j < selectTo.length; j++) {
          if (selectFrom.options[i].value == selectTo.options[j].value) {
            isEqual = true;
          }
        }
        if (isEqual == false) {
          var option = new Option(selectFrom.options[i].text,
                  selectFrom.options[i].value);
          selectTo.add(option);
        }
      }
    }
  }

  function addAllProducts(selectFrom, selectTo) {
    selectTo.innerHTML = selectFrom.innerHTML;
  }

  function updateCustomer(select) {
    var array = new Array();
    for (i = 0; i < select.length; i++) {
      array.push(select.options[i].value);
    }
    var jsonText = JSON.stringify(array);
    if (array.length == 0) {
      document.location.href
              ="${pageContext.request.contextPath}/updateCustomer?idToUpdate="
      + ${customer.id} + "&products=none";
    } else {
      document.location.href
              ="${pageContext.request.contextPath}/updateCustomer?idToUpdate="
      + ${customer.id} + "&products=" + jsonText;
    }
  }
</script>
</html>
