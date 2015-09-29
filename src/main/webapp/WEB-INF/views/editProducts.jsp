<%--
  User: Acer
  Date: 21.07.2015
  Time: 17:17
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:message code="editProducts.pageTitle" var="pageTitle"/>
<spring:message code="product.name" var="name"/>
<spring:message code="product.name.small" var="namePlaceholder"/>
<spring:message code="product.name.title" var="nameTitle"/>
<spring:message code="product.description" var="description"/>
<spring:message code="product.description.small" var="descriptionPlaceholder"/>
<spring:message code="product.description.title" var="descriptionTitle"/>
<spring:message code="product.price" var="price"/>
<spring:message code="product.price.small" var="pricePlaceholder"/>
<spring:message code="product.price.title" var="priceTitle"/>
<spring:message code="editProducts.addProductButton" var="addProductButton"/>
<spring:message code="editProducts.addProductButton.title"
                var="addProductButtonTitle"/>
<spring:message code="editProducts.deleteProductsButton"
                var="deleteProductsButton"/>
<spring:message code="editProducts.deleteProductsButton.title"
                var="deleteProductsButtonTitle"/>
<spring:message code="editProducts.updateProductsButton"
                var="updateProductsButton"/>
<spring:message code="editProducts.updateProductsButton.title"
                var="updateProductsButtonTitle"/>
<spring:message code="editProducts.restoreChangesButton"
                var="restoreChangesButton"/>
<spring:message code="editProducts.restoreChangesButton.title"
                var="restoreChangesButtonTitle"/>
<spring:message code="editProducts.selectAll.title" var="selectAllTitle"/>
<spring:message code="editProducts.select.title" var="selectTitle"/>
<spring:message code="logOutLink" var="logOutLink"/>
<spring:message code="logOutLink.title" var="logOutLinkTitle"/>
<spring:message code="editCustomersLink.title" var="editCustomersLinkTitle"/>
<spring:message code="backToMainPageLink" var="backToMainPageLink"/>
<spring:message code="backToMainPageLink.title" var="backToMainPageLinkTitle"/>
<spring:message code="tables.productsTable" var="productsTable"/>

<html>
<head>
    <title>${pageTitle}</title>
    <link href="../../resources/css/main.css" type="text/css" rel="Stylesheet"/>
    <script src="../../resources/js/editProducts.js" defer="defer"></script>
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
<form class="editProductsForm" method="post">
    <table class="tables" id="productsTable" border="1" cellpadding="3" cellspacing="1">
        <caption><span>${productsTable}</span></caption>
        <tr class="tr">
            <th>ID</th>
            <th id="thN">${name}</th>
            <th id="thD">${description}</th>
            <th id="thP">${price}</th>
            <td class="hiddenTd" id="thChck">
                <input type="checkbox"
                       id="mainCheckBox"
                       name="deleteAll"
                       title="${selectAllTitle}"
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
                           title="${nameTitle}}"
                           placeholder="${namePlaceholder}"
                           required
                           pattern="[A-Za-z .,!?:;+&0-9()/\\@\'&#34;<>$-]{2,15}"
                           value="${i.name}"/>
                </td>
                <td><input type="text"
                           name="description"
                           id="descriptionCell"
                           size="25"
                           title="${descriptionTitle}"
                           placeholder="${descriptionPlaceholder}"
                           required
                           pattern="[A-Za-z .,!?:;+&0-9()/\\@\'&#34;<>$-]{2,25}"
                           value="${i.description}"/>
                </td>
                <td><input type="text"
                           name="price"
                           id="priceCell"
                           size="3"
                           title="${priceTitle}"
                           placeholder="${pricePlaceholder}"
                           required
                           pattern="[0-9]{1,3}[.][0-9]{1,2}"
                           value="${i.price}"/>
                </td>
                <td class="hiddenTd">
                    <input type="checkbox"
                           name="child"
                           id="checkBox"
                           title="${selectTitle}"
                           tabindex="-1"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p align="middle">
        <input class="button"
               type="button"
               title="${addProductButtonTitle}"
               value="${addProductButton}"
               onclick="appendRow('productsTable', '${nameTitle}',
                       '${namePlaceholder}', '${descriptionTitle}',
                       '${descriptionPlaceholder}', '${priceTitle}',
                       '${pricePlaceholder}', '${selectTitle}');"/>
        <input class="button"
               type="button"
               title="${deleteProductsButtonTitle}"
               value="${deleteProductsButton}"
               onclick="deleteProduct('productsTable');"/>
        <input class="button"
               type="submit"
               title="${updateProductsButtonTitle}"
               value="${updateProductsButton}"/>
        <input class="button"
               type="button"
               title="${restoreChangesButtonTitle}"
               value="${restoreChangesButton}"
               onclick="restore()"/>
    </p>
    <div class="left">
        ${updateMessage}
    </div>
</form>
<p>
    <a id="backToTablesUrl"
       title="${backToMainPageLinkTitle}"
       href=${pageContext.request.contextPath}/tables>${backToMainPageLink}
    </a>
</p>
<div class="localeUrls">
    <a href="?lang=en">en</a> | <a href="?lang=ru">ru</a>
</div>
</body>
<script>
    ${scrollDownOnSubmit}
</script>
</html>
