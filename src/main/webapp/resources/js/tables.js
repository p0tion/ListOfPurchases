function sortTables(selectCustomers, selectProducts) {
    for (var i = 0; i < selectCustomers.length; i++) {
        if (selectCustomers.options[i].selected) {
            sortCust = selectCustomers.options[i].value;
        }
    }
    for (var i = 0; i < selectProducts.length; i++) {
        if (selectProducts.options[i].selected) {
            sortProd = selectProducts.options[i].value;
        }
    }

    window.location = "/tables?sortCust=" + sortCust + "&sortProd=" + sortProd;
}

function initialDefaultSortValues(
    selectCustomers, selectProducts, sortCust, sortProd) {

    for(var i, j = 0; i = selectCustomers.options[j]; j++) {
        if(i.value == sortCust) {
            selectCustomers.selectedIndex = j;
            break;
        }
    }
    for(var i, j = 0; i = selectProducts.options[j]; j++) {
        if(i.value == sortProd) {
            selectProducts.selectedIndex = j;
            break;
        }
    }
}
