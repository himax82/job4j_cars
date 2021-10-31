function validate() {
    if ($('#name').val() === "") {
        alert("Введите имя");
        return false;
    }
    if ($('#email').val() === "") {
        alert("Введите email");
        return false;
    }
    if ($('#phone').val() === "") {
        alert("Введите телефон");
        return false;
    }
    if ($('#password').val() === "") {
        alert("Уажите пароль");
        return false;
    }
    authEmail();
}

function authEmail() {
    $.ajax( {
        method: 'POST',
        url: "http://localhost:8080/cars/reg.do",
        data: JSON.stringify({
            email: $('#email').val(),
            password: $('#password').val(),
            name: $('#name').val(),
            phone: $('#phone').val(),
        }),
        dataType: 'json'
    }).done(function (data) {
        if (data == '200') {
            window.location.href = "http://localhost:8080/cars/index.html";
        } else {
            $('#fail').text('Пользователь с таким email уже существует');
            console.log(data);
        }
    }).fail(function (err) {
        alert('Error Sending!' + err);
            console.log(err);
    });
}