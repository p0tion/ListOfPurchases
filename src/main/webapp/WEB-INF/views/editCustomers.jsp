<%--
  User: Acer
  Date: 21.07.2015
  Time: 17:17
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<spring:message code="editCustomers.pageTitle" var="pageTitle"/>
<spring:message code="editCustomers.customersProfiles" var="customersProfiles"/>
<spring:message code="editCustomers.yourProfile" var="yourProfile"/>
<spring:message code="customer.firstName" var="firstName"/>
<spring:message code="customer.firstName.small" var="firstNamePlaceholder"/>
<spring:message code="customer.firstNameTitle" var="firstNameTitle"/>
<spring:message code="customer.lastName" var="lastName"/>
<spring:message code="customer.lastName.small" var="lastNamePlaceholder"/>
<spring:message code="customer.lastNameTitle" var="lastNameTitle"/>
<spring:message code="customer.cardNumber" var="cardNumber"/>
<spring:message code="customer.cardNumber.small" var="cardNumberPlaceholder"/>
<spring:message code="customer.cardNumberTitle" var="cardNumberTitle"/>
<spring:message code="customer.login" var="login"/>
<spring:message code="customer.login.small" var="loginPlaceholder"/>
<spring:message code="customer.loginTitle" var="loginTitle"/>
<spring:message code="customer.password.small" var="passwordPlaceholder"/>
<spring:message code="customer.passwordTitle" var="passwordTitle"/>
<spring:message code="editCustomers.newPassword" var="newPassword"/>
<spring:message code="editCustomers.newPassword.small"
                var="newPasswordPlaceholder"/>
<spring:message code="editCustomers.newPasswordTitle" var="newPasswordTitle"/>
<spring:message code="editCustomers.selectAllTitle" var="selectAllTitle"/>
<spring:message code="editCustomers.selectTitle" var="selectTitle"/>
<spring:message code="editCustomers.addCustomerButton" var="addCustomerButton"/>
<spring:message code="editCustomers.addCustomerButtonTitle"
                var="addCustomerButtonTitle"/>
<spring:message code="editCustomers.deleteCustomersButton"
                var="deleteCustomersButton"/>
<spring:message code="editCustomers.deleteCustomersButtonTitle"
                var="deleteCustomersButtonTitle"/>
<spring:message code="editCustomers.updateProfilesButton"
                var="updateProfilesButton"/>
<spring:message code="editCustomers.updateProfilesButtonTitle"
                var="updateProfilesButtonTitle"/>
<spring:message code="editCustomers.updateProfileButton"
                var="updateProfileButton"/>
<spring:message code="editCustomers.updateProfileButtonTitle"
                var="updateProfileButtonTitle"/>
<spring:message code="editCustomers.restoreChangesButton"
                var="restoreChangesButton"/>
<spring:message code="editCustomers.restoreChangesButtonTitle"
                var="restoreChangesButtonTitle"/>
<spring:message code="logOutLink" var="logOutLink"/>
<spring:message code="backToMainPageLink" var="backToMainPageLink"/>
<spring:message code="backToMainPageLinkTitle" var="backToMainPageLinkTitle"/>

<html>
<head>
    <title>${pageTitle}</title>
    <link href="../../resources/css/main.css" type="text/css" rel="Stylesheet"/>
    <script src="../../resources/js/editCustomers.js" defer="defer"></script>
</head>
<body>
<form class="editCustomersForm" method="post" onsubmit="scrollOnSubmit()">
    <table class="tables" id="table" border="1" cellpadding="3"
           cellspacing="1">
        <caption>
            <span>
                <c:if test="${tableLabel eq 'Customers Profiles'}">
                    ${customersProfiles}
                </c:if>
                <c:if test="${tableLabel eq 'Your Profile'}">
                    ${yourProfile}
                </c:if>
            </span>
        </caption>
        <tr id="tr">
            <th class="${hideFromUser}" id="thId">ID</th>
            <th id="thFn">${firstName}</th>
            <th id="thLn">${lastName}</th>
            <th id="thCn">${cardNumber}</th>
            <th id="thLogin">${login}</th>
            <th id="thPass">${newPassword}</th>
            <th class="hiddenTd ${hideFromUser}" id="thChck">
                <input type="checkbox"
                       id="mainCheckBox"
                       name="deleteAll"
                       title="${selectAllTitle}"
                       onclick="checkAll()"></th>
        </tr>
        <c:forEach var="i" items="${customerList}">
            <tr>
                <td class="${hideFromUser}">
                    <input type="hidden"
                           name="id"
                           id="idCol"
                           value="${i.id}"/>${i.id}
                </td>
                <td><input type="text"
                           name="firstName"
                           id="firstName"
                           size="17"
                           title="${firstNameTitle}"
                           placeholder="${firstNamePlaceholder}"
                           required
                           pattern="[A-Za-z]{2,15}"
                           value="${i.firstName}"/>
                </td>
                <td><input type="text"
                           name="lastName"
                           id="lastName"
                           size="17"
                           title="${lastNameTitle}"
                           placeholder="${lastNamePlaceholder}"
                           required
                           pattern="[A-Za-z]{2,15}"
                           value="${i.lastName}"/>
                </td>
                <td><input type="text"
                           name="cardNumber"
                           id="cardNumber"
                           size="14"
                           title="${cardNumberTitle}"
                           placeholder="${cardNumberPlaceholder}"
                           required
                           pattern="[0-9]{16}"
                           value="${i.cardNumber}"/>
                </td>
                <td><input type="text"
                           name="login"
                           id="login"
                           size="15"
                           title="${loginTitle}"
                           placeholder="${loginPlaceholder}"
                           required
                           pattern="[A-Za-z0-9_]{2,15}"
                           value="${i.login}"/>
                </td>
                <td><input type="text"
                           name="password"
                           id="password"
                           size="15"
                           title="${newPasswordTitle}"
                           placeholder="${newPasswordPlaceholder}"
                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}"
                           value=""/>
                </td>
                <td class="hiddenTd ${hideFromUser}">
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
        <input class="button ${hideFromUser}"
               type="button"
               title="${addCustomerButtonTitle}"
               value="${addCustomerButton}"
               onclick="appendRow('table', '${firstNameTitle}',
                       '${firstNamePlaceholder}', '${lastNameTitle}',
                       '${lastNamePlaceholder}', '${cardNumberTitle}',
                       '${cardNumberPlaceholder}', '${loginTitle}',
                       '${loginPlaceholder}', '${passwordTitle}',
                       '${passwordPlaceholder}', '${selectTitle}')"/>
        <input class="button ${hideFromUser}"
               type="button"
               title="${deleteCustomersButtonTitle}"
               value="${deleteCustomersButton}"
               onclick="deleteCustomer('table');"/>
        <input class="button"
               type="submit"
                <c:if test="${updateLabel eq 'Update profiles'}">
                    title="${updateProfilesButtonTitle}"
                    value="${updateProfilesButton}"
                </c:if>
                <c:if test="${updateLabel eq 'Update profile'}">
                    title="${updateProfileButtonTitle}"
                    value="${updateProfileButton}"
                </c:if>/>
        <input class="button"
               type="button"
               title="${restoreChangesButtonTitle}"
               value="${restoreChangesButton}"
               onclick="restore()"/>
    </p>
    <div class="left">${updateMessage}</div>
</form>
<div class="logOut"><label>${user.firstName} ${user.lastName}</label>
    <a id="logOutText" href="/signIn">(${logOutLink})</a>
</div>
<p><a id="backToTablesUrl" title="${backToMainPageLinkTitle}"
      href=${pageContext.request.contextPath}/tables>${backToMainPageLink}</a></p>
<div class="localeUrls">
    <a href="?lang=en">en</a> | <a href="?lang=ru">ru</a>
</div>

</body>
<script>
    ${scrollDownOnSubmit}
</script>
</html>
