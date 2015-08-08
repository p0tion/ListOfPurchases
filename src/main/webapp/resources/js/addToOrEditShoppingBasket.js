function removeAllProducts(select) {
    for (var i = select.options.length-1; i >= 0; i--) {
        select.remove(i);
    }
}

function removeProducts(select) {
    for (var i = select.options.length-1; i >= 0; i--) {
        if (select.options[i].selected)
            select.remove(i);
    }
}

function addProducts(selectFrom, selectTo) {
    for (var i = 0; i < selectFrom.length; i++) {
        if (selectFrom.options[i].selected) {
            var isEqual = false;
            for (var j = 0; j < selectTo.length; j++) {
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

function updateCustomer(select, customerId) {
    var array = new Array();
    for (var i = 0; i < select.length; i++) {
        array.push(select.options[i].value);
    }
    var jsonText = JSON.stringify(array);
    if (array.length == 0) {
        document.location.href="/updateCustomer?idToUpdate="
        + customerId + "&products=none";
    } else {
        document.location.href="/updateCustomer?idToUpdate="
        + customerId + "&products=" + jsonText;
    }
}