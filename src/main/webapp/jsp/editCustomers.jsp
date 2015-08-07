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
    <link href="../css/main.css" type="text/css" rel="Stylesheet"/>
    <style>
        input:required:invalid,input:focus:invalid,textarea:required:invalid,textarea:focus:invalid{background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAT1JREFUeNpi/P//PwMpgImBRMACY/x7/uDX39sXt/67cMoDyOVgMjBjYFbV/8kkqcCBrIER5KS/967s+rmkXxzI5wJiRSBm/v8P7NTfHHFFl5mVdIzhGv4+u///x+xmuAlcdXPB9KeqeLgYd3bDU2ZpRRmwH4DOeAI07QXIRKipYPD35184/nn17CO4p/+cOfjl76+/X4GYAYThGn7/g+Mfh/ZZwjUA/aABpJVhpv6+dQUjZP78Z0YEK7OezS2gwltg64GmfTu6i+HL+mUMP34wgvGvL78ZOEysf8M1sGgZvQIqfA1SDAL8iUUMPIFRQLf+AmMQ4DQ0vYYSrL9vXDz2sq9LFsiX4dLRA0t8OX0SHKzi5bXf2HUMBVA0gN356N7p7xdOS3w5fAgcfNxWtn+BJi9gVVBOQfYPQIABABvRq3BwGT3OAAAAAElFTkSuQmCC);background-position:right top;background-repeat:no-repeat;box-shadow:none}
        input:required:valid,textarea:required:valid{background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAZZJREFUeNpi/P//PwMpgImBRMAy58QshrNPTzP8+vOLIUInisFQyYjhz98/DB9/fmT48/+35v7H+8KNhE2+WclZd+G0gZmJmYGThUNz1fUVMZtvbWT59eUXG9wGZIWMUPj993eJ5VeWxuy8veM/CzPL3yfvH/9H0QBSBDYZyOVm4mGYfn6q4cory5lYmFh+MrEwM/76/YsR7mk2ZjbWP///WP37/y8cqIDhx58fjvtu7XV6//ndT34G/v8FasUsDjKO/+A2PP3wpGLd+TVsfOz8XH6KAT+nHpokcu7h6d9q/BoMxToVbBYqlt9///+1GO4/WVdpXqY/zMqXn13/+vTjI9mj94/y//v9/3e9ZRObvYbDT0Y2xnm///x+wsfHB3GSGLf41jb3rv0O8nbcR66d+HPvxf2/+YZFTHaqjl8YWBnm/vv37yly5LL8+vuLgYuVa3uf/4T/Kd8SnSTZpb6FGUXwcvJxbAPKP2VkZESNOBDx8+9PBm4OwR1TwmYwcfzjsBUQFLjOxs52A2YyKysrXANAgAEA7buhysQuIREAAAAASUVORK5CYII=);background-position:right top;background-repeat:no-repeat}
    </style>
</head>
<body>
<div class="logOut"><label>${user.firstName} ${user.lastName}</label> <a
        id="logOutText" href="/signIn">(log out)</a> </div>
<form class="editCustomersForm" method="post" onsubmit="scrollOnSubmit()">
    <table class="tables" id="table" border="1" cellpadding="3"
           cellspacing="1">
        <caption><span style="font-size:20px">${tableLabel}</span></caption>
        <tr style="font-size: 10pt">
            <th ${hideFromUser}>ID</th>
            <th style="padding: 0 42px">First Name</th>
            <th style="padding: 0 43px">Last Name</th>
            <th style="padding: 0 34px">Card Number</th>
            <th style="padding: 0 58px">Login</th>
            <th style="padding: 0 32px">Password</th>
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
    //todo scroll down the page on submit

    function appendRow(tableId) {
        var table = document.getElementById(tableId);
        var row = table.insertRow(table.rows.length);

        // text cell for ID
        var cellText = row.insertCell(0);
        var textNode = document.createTextNode("");
        cellText.appendChild(textNode);

        // input text cell for first name
        var cellInputText = row.insertCell(1);
        var el = document.createElement('input');
        el.setAttribute('type', 'text');
        el.setAttribute('name', 'firstName');
        el.setAttribute('id', 'firstName');
        el.setAttribute('size', '15');
        el.setAttribute('title', 'Latin chars only. Size from 2 to 20');
        el.setAttribute('placeholder', 'first name');
        el.setAttribute('required', "");
        el.setAttribute('pattern', "[A-Za-z]{2,20}");
        el.setAttribute('value', "");
        cellInputText.appendChild(el);

        // input text cell for last name
        var cellInputText2 = row.insertCell(2);
        var el2 = document.createElement('input');
        el2.setAttribute('type', 'text');
        el2.setAttribute('name', 'lastName');
        el2.setAttribute('id', 'lastName');
        el2.setAttribute('size', '15');
        el2.setAttribute('title', 'Latin chars only. Size from 2 to 20');
        el2.setAttribute('placeholder', 'last name');
        el2.setAttribute('required', "");
        el2.setAttribute('pattern', "[A-Za-z]{2,20}");
        el2.setAttribute('value', "");
        cellInputText2.appendChild(el2);

        // input text cell for card number
        var cellInputText3 = row.insertCell(3);
        var el3 = document.createElement('input');
        el3.setAttribute('type', 'text');
        el3.setAttribute('name', 'cardNumber');
        el3.setAttribute('id', 'cardNumber');
        el3.setAttribute('size', '15');
        el3.setAttribute('title', '16 digits');
        el3.setAttribute('placeholder', 'card number');
        el3.setAttribute('required', "");
        el3.setAttribute('pattern', "[0-9]{16}");
        el3.setAttribute('value', "");
        cellInputText3.appendChild(el3);

        // input text cell for login
        var cellInputText4 = row.insertCell(4);
        var el4 = document.createElement('input');
        el4.setAttribute('type', 'text');
        el4.setAttribute('name', 'login');
        el4.setAttribute('id', 'login');
        el4.setAttribute('size', '15');
        el4.setAttribute('title', 'Latin chars, digits or underscore. Size from 2 to 15');
        el4.setAttribute('placeholder', 'login');
        el4.setAttribute('required', "");
        el4.setAttribute('pattern', "[A-Za-z0-9_]{2,15}");
        el4.setAttribute('value', "");
        cellInputText4.appendChild(el4);

        // input text cell for password
        var cellInputText5 = row.insertCell(5);
        var el5 = document.createElement('input');
        el5.setAttribute('type', 'text');
        el5.setAttribute('name', 'password');
        el5.setAttribute('id', 'password');
        el5.setAttribute('size', '15');
        el5.setAttribute('title', 'At least 6 chars including UPPER/lower case and digits');
        el5.setAttribute('placeholder', 'password');
        el5.setAttribute('required', "");
        el5.setAttribute('pattern', "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}");
        el5.setAttribute('value', "");
        cellInputText5.appendChild(el5);

        // select cell for checkbox
        var cellChck = row.insertCell(6);
        cellChck.setAttribute("class", "hiddenTd");
        var chck = document.createElement('input');
        chck.setAttribute('type', 'checkbox');
        chck.setAttribute('name', 'child');
        chck.setAttribute('id', 'checkBox');
        chck.setAttribute('title', 'Check to delete');
        chck.setAttribute('tabindex', '-1');
        cellChck.appendChild(chck);

        // scroll the window to the bottom
        window.scrollTo(0, document.body.scrollHeight);

        // place caret into first name input area
        el.select();
    }

    function deleteCustomer(tableID) {
        var table = document.getElementById(tableID).tBodies[0];
        var rowCount = table.rows.length;
        for(var i=1; i<rowCount; i++) {
            var row = table.rows[i];
            var chkbox = row.cells[6].getElementsByTagName('input')[0];
            if(null != chkbox && true == chkbox.checked) {
                table.deleteRow(i);
                rowCount--;
                i--;
            }
        }
    }

    function restore() {
        location.reload();
    }

    function checkAll() {
        var parent = document.getElementById("mainCheckBox");
        var child = document.getElementsByName('child');
        for (var i = 0; i < child.length; i++) {
            child[i].checked = parent.checked;
        }
    }

</script>
</html>
