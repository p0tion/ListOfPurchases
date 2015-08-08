<%--
  User: Acer
  Date: 21.07.2015
  Time: 17:17
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit products</title>
    <link href="../resources/css/main.css" type="text/css" rel="Stylesheet"/>
    <script src="../resources/js/editProducts.js" defer="defer"></script>
</head>
<body>
<div class="logOut"><a href="/editCustomers">${user.firstName}
    ${user.lastName}</a> <a id="logOutText" href="/signIn">(log out)</a> </div>
<form method="post" class="editProductsForm">
    <table class="tables" id="productsTable" border="1" cellpadding="3" cellspacing="1">
        <caption><span>Products Table</span></caption>
        <tr id="tr">
            <th>ID</th>
            <th width="138">Name</th>
            <th width="208">Description</th>
            <th width="68">Price</th>
            <td class="hiddenTd">
                <input type="checkbox"
                       id="mainCheckBox"
                       name="deleteAll"
                       title="Select all"
                       onclick="checkAll()"/>
            </td>
        </tr>
        <c:forEach var="i" items="${productList}">
            <tr>
                <td><input type="hidden" name="id" value="${i.id}"/><p
                        align="middle">${i.id}</p>
                </td>
                <td><input type="text"
                           name="name"
                           id="nameCell"
                           size="15"
                           title="Latin chars, numbers, signs.Size from 2 to 15"
                           placeholder="name"
                           required
                           pattern="[A-Za-z .,!?:;+&0-9()/\\@\'&#34;<>$-]{2,15}"
                           value="${i.name}"/>
                </td>
                <td><input type="text"
                           name="description"
                           id="descriptionCell"
                           size="25"
                           title="Latin chars, numbers, signs.Size from 2 to 25"
                           placeholder="description"
                           required
                           pattern="[A-Za-z .,!?:;+&0-9()/\\@\'&#34;<>$-]{2,25}"
                           value="${i.description}"/>
                </td>
                <td><input type="text"
                           name="price"
                           id="priceCell"
                           size="5"
                           title="A number fractioned by a dot"
                           placeholder="price"
                           required
                           pattern="[0-9]{1,3}[.][0-9]{1,2}"
                           value="${i.price}"/>
                </td>
                <td class="hiddenTd">
                    <input type="checkbox"
                           name="child"
                           id="checkBox"
                           title="Check to delete"
                           tabindex="-1"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p align="middle">
        <input class="button"
               type="button"
               title="Add a row for another product"
               value="Add product"
               onclick="appendRow('productsTable');"/>
        <input class="button"
               type="button"
               title="Delete checked products"
               value="Delete products"
               onclick="deleteProduct('productsTable');"/>
        <input class="button"
               type="submit"
               title="Update all products in the list"
               value="Update products"/>
        <input class="button"
               type="button"
               title="Restore changes before update"
               value="Restore changes"
               onclick="restore()"/>
    </p>
    ${updateMessage}
</form>
<p><a id="backToTablesUrl" title="Back to the main page"
      href=${pageContext.request.contextPath}/tables>Back to tables</a></p>

</body>
<script>
    ${scrollDownOnSubmit}
</script>
</html>
