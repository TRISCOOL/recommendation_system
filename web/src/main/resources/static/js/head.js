$(document).ready(function () {

    initHead();

});

var initHead = function () {
    var headDiv = $('<div class="row" id="main-head"></div>');
    var h1 = $('<div class="col-md-10"><h1>Recommendation System <small>emmmmmm</small></h1></div>' +
        '<div class="col-md-2"><h1><small><span class="glyphicon glyphicon-user" aria-hidden="true"></span></small></h1></div>');

    headDiv.html(h1);
    $("#head-page").html(headDiv);

    var nav = $('<ul class="nav nav-pills"></ul>');
    var navChild = $('<li role="presentation"><a href="#">排行</a></li>\n' +
        '            <li role="presentation"><a href="#">恐怖</a></li>\n' +
        '            <li role="presentation"><a href="#">恐怖</a></li>\n' +
        '            <li role="presentation"><a href="#">恐怖</a></li>\n' +
        '            <li role="presentation"><a href="#">恐怖</a></li>\n' +
        '            <li role="presentation"><a href="#">恐怖</a></li>');

    nav.html(navChild);
    $(".navigation-list").html(nav);
}
