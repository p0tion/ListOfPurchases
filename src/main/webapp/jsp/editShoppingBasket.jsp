<%--
  User: Acer
  Date: 21.07.2015
  Time: 17:17
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit shopping basket</title>
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
  <table>
    <tbody>
    <tr>
      <td style="text-align: right">
        Shopping basket of ${customer.firstName}
        ${customer.lastName}:
      </td>
        <td colspan="2" style="text-align: right;">
          Full product list:
        </td>
    </tr>
    <tr>
      <td>
        <form id="update">
        <select style="margin-left: 80px;" multiple="true" id="products"
                size="10">
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
            <input type="button" name="removeAllButton"
                   onclick="removeAllProducts(products)";
                   value=">>"/>
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" name="removeButton"
                   onclick="removeProducts(products)";
                   value=">"/>
          </td>
          </tr>
          <tr>
          <td>
            <input type="button" name="addButton"
                   onclick="addProducts(productList, products)";
                   value="<"/>
          </td>
        </tr>
        <tr>
          <td>
            <input type="button" name="addAllButton"
                   onclick="addAllProducts(productList, products)";
                   value="<<"/>
          </td>
        </tr>
        </tbody>
      </table>
      </td>
      <td>
        <select multiple="true" id="productList" size="10" style="margin-bottom:
         10px">
          <c:forEach var="i" items="${productList}">
            <option value="${i.id}">${i.name}</option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td>
      <input type="button" style="margin-left: 125px" form="update"
              onclick="updateCustomer(products)"; value="Update"/>
      </td>
    </tr>
    </tbody>
  </table>
</body>
<br>
<a style="text-decoration: none" title="Back to the main page"
   href=${pageContext.request.contextPath}/tables>Back
  to tables</a>
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
              ="${pageContext.request.contextPath}/tables?idToUpdate="
      + ${customer.id} + "&products=none";
    } else {
      document.location.href
              ="${pageContext.request.contextPath}/tables?idToUpdate="
      + ${customer.id} + "&products=" + jsonText;
    }
  }
</script>
</html>
