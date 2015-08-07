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
    <link href="../css/main.css" type="text/css" rel="Stylesheet"/>
    <style>
        input:required:invalid,input:focus:invalid,textarea:required:invalid,textarea:focus:invalid{background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAT1JREFUeNpi/P//PwMpgImBRMACY/x7/uDX39sXt/67cMoDyOVgMjBjYFbV/8kkqcCBrIER5KS/967s+rmkXxzI5wJiRSBm/v8P7NTfHHFFl5mVdIzhGv4+u///x+xmuAlcdXPB9KeqeLgYd3bDU2ZpRRmwH4DOeAI07QXIRKipYPD35184/nn17CO4p/+cOfjl76+/X4GYAYThGn7/g+Mfh/ZZwjUA/aABpJVhpv6+dQUjZP78Z0YEK7OezS2gwltg64GmfTu6i+HL+mUMP34wgvGvL78ZOEysf8M1sGgZvQIqfA1SDAL8iUUMPIFRQLf+AmMQ4DQ0vYYSrL9vXDz2sq9LFsiX4dLRA0t8OX0SHKzi5bXf2HUMBVA0gN356N7p7xdOS3w5fAgcfNxWtn+BJi9gVVBOQfYPQIABABvRq3BwGT3OAAAAAElFTkSuQmCC);background-position:right top;background-repeat:no-repeat;box-shadow:none}
        input:required:valid,textarea:required:valid{background-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAwAAAAMCAYAAABWdVznAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAZZJREFUeNpi/P//PwMpgImBRMAy58QshrNPTzP8+vOLIUInisFQyYjhz98/DB9/fmT48/+35v7H+8KNhE2+WclZd+G0gZmJmYGThUNz1fUVMZtvbWT59eUXG9wGZIWMUPj993eJ5VeWxuy8veM/CzPL3yfvH/9H0QBSBDYZyOVm4mGYfn6q4cory5lYmFh+MrEwM/76/YsR7mk2ZjbWP///WP37/y8cqIDhx58fjvtu7XV6//ndT34G/v8FasUsDjKO/+A2PP3wpGLd+TVsfOz8XH6KAT+nHpokcu7h6d9q/BoMxToVbBYqlt9///+1GO4/WVdpXqY/zMqXn13/+vTjI9mj94/y//v9/3e9ZRObvYbDT0Y2xnm///x+wsfHB3GSGLf41jb3rv0O8nbcR66d+HPvxf2/+YZFTHaqjl8YWBnm/vv37yly5LL8+vuLgYuVa3uf/4T/Kd8SnSTZpb6FGUXwcvJxbAPKP2VkZESNOBDx8+9PBm4OwR1TwmYwcfzjsBUQFLjOxs52A2YyKysrXANAgAEA7buhysQuIREAAAAASUVORK5CYII=);background-position:right top;background-repeat:no-repeat}
    </style>
</head>
<body>
<div class="logOut"><a href="/editCustomers">${user.firstName}
    ${user.lastName}</a> <a id="logOutText" href="/signIn">(log out)</a> </div>
<form method="post" class="editProductsForm">
    <table class="tables" id="productsTable" border="1" cellpadding="3" cellspacing="1">
        <caption><span style="font-size:20px; alignment: center">Products
            Table</span></caption>
        <tr style="font-size: 10pt">
            <th>ID</th>
            <th width="138">Name</th>
            <th width="208">Description</th>
            <th width="68">Price</th>
            <td class="hiddenTd">
                <input type="checkbox"
                       id="mainCheckBox"
                       name="deleteAll"
                       title="Select all"
                       onclick="checkAll()">
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
               onclick="appendRow('productsTable');" />
        <input class="button"
               type="button"
               title="Delete checked products"
               value="Delete products"
               onclick="deleteProduct('productsTable');" />
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

    //todo scroll down the page on submit

    function appendRow(tableId)
    {
        var table = document.getElementById(tableId);
        var row = table.insertRow(table.rows.length);

        // text cell for ID
        var cellText = row.insertCell(0);
        var textNode = document.createTextNode("");
        cellText.appendChild(textNode);

        // input text cell for name
        var cellInputText = row.insertCell(1);
        var el = document.createElement('input');
        el.setAttribute('type', 'text');
        el.setAttribute('name', 'name');
        el.setAttribute('id', 'nameCell');
        el.setAttribute('size', '15');
        el.setAttribute('title', 'Latin chars, numbers, signs.Size from 2 to 25');
        el.setAttribute('placeholder', 'name');
        el.setAttribute('required', "");
        el.setAttribute('pattern', "[A-Za-z .,!?:;+&0-9()/\\@\'&#34;<>$-]{2,15}");
        el.setAttribute('value', "");
        cellInputText.appendChild(el);

        // input text cell for description
        var cellInputText2 = row.insertCell(2);
        var el2 = document.createElement('input');
        el2.setAttribute('type', 'text');
        el2.setAttribute('name', 'description');
        el2.setAttribute('id', 'descriptionCell');
        el2.setAttribute('size', '25');
        el2.setAttribute('title', 'Latin chars, numbers, signs.Size from 2 to 25');
        el2.setAttribute('placeholder', 'description');
        el2.setAttribute('required', "");
        el2.setAttribute('pattern', "[A-Za-z .,!?:;+&0-9()/\\@\'&#34;<>$-]{2,25}");
        el2.setAttribute('value', "");
        cellInputText2.appendChild(el2);

        // input text cell for price
        var cellInputText3 = row.insertCell(3);
        var el3 = document.createElement('input');
        el3.setAttribute('type', 'text');
        el3.setAttribute('name', 'price');
        el3.setAttribute('id', 'priceCell');
        el3.setAttribute('size', '5');
        el3.setAttribute('title', 'A number fractioned by a dot');
        el3.setAttribute('placeholder', 'price');
        el3.setAttribute('required', "");
        el3.setAttribute('pattern', "[0-9]{1,3}[.][0-9]{1,2}");
        el3.setAttribute('value', "");
        cellInputText3.appendChild(el3);

        // select cell for checkbox
        var cellChck = row.insertCell(4);
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

        // place caret into name input area
        el.select();
    }

    function deleteProduct(tableID)  {
        var table = document.getElementById(tableID).tBodies[0];
        var rowCount = table.rows.length;
        for(var i=1; i<rowCount; i++) {
            var row = table.rows[i];
            var chkbox = row.cells[4].getElementsByTagName('input')[0];
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
