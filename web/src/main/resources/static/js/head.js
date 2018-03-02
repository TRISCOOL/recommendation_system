$(document).ready(function () {

    initHead();

});

var initHead = function () {
    var headDiv = $('<div class="row" id="main-head"></div>');

    let userName = getUserName();

    var h1 = $('<div class="col-md-10"><h1>Recommendation System <small>emmmmmm</small></h1></div>');
    var h2 = $('<div class="col-md-2"><h1><small><span class="glyphicon glyphicon-user" aria-hidden="true"></span></small></h1></div>');
    if(userName != null){
        h2 = $('<div class="col-md-2"><h1><small><span class="glyphicon glyphicon-user" aria-hidden="true">'+userName+'</span></small></h1></div>')
    }

    headDiv.html(h1);
    headDiv.append(h2)

    $("#head-page").html(headDiv);

    var nav = $('<ul class="nav nav-pills"></ul>');
    var navChild = $('<li role="presentation"><a href="../rank.html">排行</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=terror">恐怖</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=comedy">喜剧</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=love">爱情</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=action">动作</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=all">全部</a></li>');

    nav.html(navChild);
    $(".navigation-list").html(nav);
}

var getUserName = function () {
    let user = storage.typeJson.getJsonByKey(KEY_USER);
    if(user != null){
        return user.userName;
    }

    return null;
}
