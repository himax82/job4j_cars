$(document).ready(function () {
    auth();
    build();
    // $('#add').click(function () {
    //     validateAndAdd();
    //     setTimeout(function () {
    //         build();
    //     }, 2000);
    // });

    // $('#flexSwitchCheckDefault').click(function () {
    //     show = !show;
    //     build();
    // });
});

function validate() {
    if ($('#email').val() === "") {
        alert("Введите email");
        return false;
    }
    if ($('#password').val() === "") {
        alert("Уажите пароль");
        return false;
    }
    login();
    return true;
}

function auth() {
    $.getJSON("http://localhost:8080/cars/auth.do"
    ).done(function (response) {
        $('#user').text('Пользователь ' + response.name);
        console.log("Response Data: " + response);
    }).fail(function (err) {
        console.log("Request Failed: " + err);
    });
}

function login() {
    $.ajax( {
        method: 'POST',
        url: "http://localhost:8080/cars/login.do",
        data: JSON.stringify({
            email: $('#email').val(),
            password: $('#password').val()
        }),
        dataType: 'json'
    }).done(function (data) {
        if (data == '200') {
            window.location.href = "http://localhost:8080/cars/index.html";
        } else if (data == '407') {
            $('#invalid').text('Неправильный пароль');
            console.log(data);
        } else  {
            $('#invalid').text('Такой пользователь не существует');
            console.log(data);
        }
    }).fail(function (err) {
        alert('Error Sending!' + err);
        console.log(err);
    });
}

function build() {
    $.post("http://localhost:8080/cars/post.do"
    ).done(function (response) {
        let post = [];
        let p = '';
        $.each(response, function (key, value) {
                p = '<tr><td>' +
                    '<img src="/cars/downmain.do?id=' + value.user.id + '" width="360px"' +
                         ' height="240px">' + '</td>' +
                    '<td>' + value.brand.name + '</td>' +
                    '<td>' + value.body.name + '</td>' +
                    '<td>' + value.description + '</td>' +
                    '<td>' + value.price + '</td>';
                if (value.sale === false) {
                    p += '<td>' + '<div class="form-check">' +
                        '<input class="form-check-input" type="checkbox" onchange = "update(this.id)" id="' + value.id + '">' +
                        '</div></td></tr>';
                } else {
                    p += '<td>' + 'продано' + '</td></tr>';
                }
                post.push(p);
            })
            $('#table').html(post);
    }).fail(function (err) {
        alert('buildTable Failed!');
        console.log("Request Failed: " + err);
    });
}

function update(id) {
    $.get("http://localhost:8080/cars/update.do", {
        id: id
    }).done(function (response) {
        build();
        console.log("It is done : " + response);
    }).fail(function (err) {
        alert('Post update Failed!');
        console.log("Post update Failed: " + err);
    });
}