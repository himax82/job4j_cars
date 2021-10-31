$(document).ready(function () {
        getBrand();
    setTimeout(function () {
        getBody();
    }, 1000);

});

function validate() {
    if ($('#description').val() === "") {
        alert("Опишите машину");
        return false;
    }
    if ($('#price').val() === "") {
        alert("Укажите цену");
        return false;
    }
    addPost();
}

function addRow() {
    const name = $('#name').val();
    const surname = $('#surname').val();
    const gender = $('#gender').val();
    const description = $('#description').val();
    $(`#table tr:last`).after('<tr><td>' + name +'</td>' +
        '<td>' + surname +'</td>' +
        '<td>' + gender +'</td>' +
        '<td>' + description +'</td></tr>');
}

function getBrand() {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/cars/brand.do',
        dataType: 'json'
    }).done(function (data) {
        for (let brand of data) {
            $('#brand').append($('<option>', {
                value: brand.id,
                text: brand.name
            }));
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function getBody() {
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/cars/body.do',
        dataType: 'json'
    }).done(function (data) {
        for (let body of data) {
            $('#body').append($('<option>', {
                value: body.id,
                text: body.name
            }));
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function addPost() {
    $.ajax({
        method: 'post',
        url: 'http://localhost:8080/cars/add.do',
        data: JSON.stringify({
            description: $('#description').val(),
            brand: {
                id: $('#brand').val()
            },
            body: {
               id: $('#body').val()
            },
            price: $('#price').val()
        })
    }).done(function (response) {
        $('#done').text('Объявление добавлено, через 2 секунды' +
            ' вы будете перенаправлены на главную страницу');
        console.log("Fine Data Sent ");
        setTimeout(function(){
            window.location.href = 'http://localhost:8080/cars/index.html';
        }, 5000);
    }).fail(function (err) {
        alert('Error Sending!' + err);
        console.log("Error Sending data " + $('#description').val() + $('#brand').val() + $('#body').val() + $('#price').val());
    });
}