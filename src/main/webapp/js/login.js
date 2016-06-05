var basicUrl = 'http://localhost:8084/SistemZaPracenjeDobavljacaSampinjona/';

$('#logIn').click(function () {
    logIn();
});

$('#srch').click(function () {
    // searchKompanije();
});
document.cookie = "basicURL=" + basicUrl;

//za enter
// document.onkeydown = function (evt) {
//     if(evt.keyCode == 13){
//         $("#logIn").click();
//     }
// };

function make_base_auth(user, password) {
    var tok = user + ':' + password;
    var hash = btoa(tok);
    return "Basic " + hash;
}

function logIn() {
    var username = document.getElementById("korisnickoIme").value;
    var password = document.getElementById("korisnickaSifra").value;
    $.ajax({
        type: "POST",
        url: basicUrl + "rest/authorization/login",
        dataType: "json",
        headers: {
            'Content-Type': 'application/json'
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', make_base_auth(username, password));
        },
        success: function (response) {
//      json_token = response;
            var p = document.getElementById('message');
            p.innerHTML = "Uspešno ste se prijavili na sistem!";
            $('#messageModal').modal('show');
            $('#bla').click(function () {
                window.location.href = "dobavljaci.html";
                document.cookie = "token=" + response.token;
            });
        },
        async: false,
        error: function (response) {
            alert("Niste se uspešno ulogovali!");
        }

    });
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ')
            c = c.substring(1);
        if (c.indexOf(name) == 0)
            return c.substring(name.length, c.length);
    }
    return "";
}
//
//function getData() {
//  $.ajax({
//    url: basicUrl + 'rest/student',
//    dataType: 'json',
//    success: function(response) {
//      studenti = response;
//      // napuniInfo();
//
//
//    }
//  });
//}
//
// function napuniInfo() {
//   for (var i = 0; i < studenti.length; i++) {
//     if (studenti[i].email == emailtoken) {
//       trenutniUser = studenti[i].email;
//       alert(trenutniUser.prezime);
//     }
//   }
// }

//var kompanije;
//
//function napuniSideBar() {
//
//  var lista = document.getElementById('lg-menu');
//  for (var k in kompanije) {
//    // alert();
//    var li = document.createElement('li');
//    var a = document.createElement('a');
//    a.href = "kompanija.html?name=" + kompanije[k].id;
//    a.innerHTML = kompanije[k].ime;
//
//    // li.innerHTML = kompanije[k].ime;
//
//    li.appendChild(a);
//    lista.appendChild(li);
//    // lista.append('<li><a href="sdasad.com"></a></li>')
//
//  }
//}


//function searchKompanije() {
//  $.ajax({
//    url: basicUrl + 'rest/kompanija',
//    dataType: 'json',
//    success: function(response) {
//      kompanije = response;
//      napuniSideBar();
//    },
//    beforeSend: function(xhr) {
//      xhr.setRequestHeader('Authorization', json_token);
//    },
//  });

// $.getJSON('http://192.168.0.104:8080/hashfon/rest/kompanija',  function(json, textStatus) {
//     /*optional stuff to do after success */
//     alert(json);
// });
//}



//function vratiKompaniju() {
//
//  var sPageURL = decodeURIComponent(window.location.search.substring(1));
//  var str = sPageURL.split('=');
//  var idK = str[1];
//
//  $.ajax({
//    url: basicUrl + 'rest/kompanija/' + idK,
//    dataType: 'json',
//    success: function(response) {
//      napuniKompaniju(response);
//    },
//    beforeSend: function(xhr) {
//      xhr.setRequestHeader('Authorization', json_token);
//    }
//  });
//}


//
//function napuniKompaniju(k) {
//  $('#ime').html(k.ime);
//  $('#email').html(k.email);
//  $('#adresa').html(k.adresa);
//  $('#miniNaslov').html(k.opis);
//  // $('#ime').html(k.ime);
//
//}

//$(document).ready(function() {
//  searchKompanije();
//  getData();
//
//
//});
