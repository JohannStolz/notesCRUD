function empty_form() {
    var txt = document.getElementById('nmb').value;
    if (txt == '') {
        alert('Вы забыли ввести количество страниц');
        return false;
    }
    return true;
}

function validBool() {
    var bo1 = document.getElementById('bo1');
    var bo2 = document.getElementById('bo2');
    if (bo1.checked != true && bo1.checked != true) {

        alert('Проставьте значение столбца Выполнение');
        return false;
    }
    return true;
}

function validDate() { // date в формате 31.12.2014
    var date = document.getElementById('dat').value;
    var d_arr = date.split('-');
    var d = new Date(d_arr[2] + '/' + d_arr[1] + '/' + d_arr[0] + ''); // дата в формате 2014/12/31
    if (d_arr[2] != d.getFullYear() || d_arr[1] != (d.getMonth() + 1) || d_arr[0] != d.getDate()) {
        alert('Вы ввели невалидную дату. Пожалуйста, введите корректное значение');
        return false; // неккоректная дата
    }
    return true;
}