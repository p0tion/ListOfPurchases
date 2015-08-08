<%--
  User: Acer
  Date: 21.07.2015
  Time: 17:17
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit customers</title>
    <link href="../resources/css/main.css" type="text/css" rel="Stylesheet"/>
    <script src="../resources/js/editCustomers.js" defer="defer"></script>
</head>
<body>
<div class="logOut"><label>${user.firstName} ${user.lastName}</label>
    <a id="logOutText" href="/signIn">(log out)</a>
</div>
<form class="editCustomersForm" method="post" onsubmit="scrollOnSubmit()">
    <table class="tables" id="table" border="1" cellpadding="3"
           cellspacing="1">
        <caption><span>${tableLabel}</span></caption>
        <tr id="tr">
            <th ${hideFromUser}>ID</th>
            <th id="thFN">First Name</th>
            <th id="thLN">Last Name</th>
            <th id="thCaNum">Card Number</th>
            <th id="thLog">Login</th>
            <th id="thPass">Password</th>
            <th class="hiddenTd" ${hideFromUser}>
                <input type="checkbox"
                       id="mainCheckBox"
                       name="deleteAll"
                       title="Select all"
                       onclick="checkAll()"></th>
        </tr>
        <c:forEach var="i" items="${customerList}">
            <tr>
                <td ${hideFromUser}>
                    <input type="hidden"
                           name="id"
                           value="${i.id}"/>
                    <p align="middle">${i.id}</p>
                </td>
                <td><input type="text"
                           name="firstName"
                           id="firstName"
                           size="15"
                           title="Latin chars only. Size from 2 to 15"
                           placeholder="first name"
                           required
                           pattern="[A-Za-z]{2,15}"
                           value="${i.firstName}"/>
                </td>
                <td><input type="text"
                           name="lastName"
                           id="lastName"
                           size="15"
                           title="Latin chars only. Size from 2 to 15"
                           placeholder="last name"
                           required
                           pattern="[A-Za-z]{2,15}"
                           value="${i.lastName}"/>
                </td>
                <td><input type="text"
                           name="cardNumber"
                           id="cardNumber"
                           size="15"
                           title="16 digits"
                           placeholder="card number"
                           required
                           pattern="[0-9]{16}"
                           value="${i.cardNumber}"/>
                </td>
                <td><input type="text"
                           name="login"
                           id="login"
                           size="15"
                           title="Latin chars, digits or underscore. Size from 2 to 15"
                           placeholder="login"
                           required
                           pattern="[A-Za-z0-9_]{2,15}"
                           value="${i.login}"/>
                </td>
                <td><input type="text"
                           name="password"
                           id="password"
                           size="15"
                           title="At least 6 chars including UPPER/lower case and digits"
                           placeholder="password"
                           required
                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}"
                           value="${i.password}"/>
                </td>
                <td class="hiddenTd" ${hideFromUser}>
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
        ${addCustomerButton}
        ${deleteCustomersButton}
        <input class="button"
               type="submit"
               title="Update all customers in the list"
               value="${updateLabel}"/>
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
