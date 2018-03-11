$(document).ready(function () {

    initHead();

});

var initHead = function () {
    var headDiv = $('<div class="row" id="main-head"></div>');

    let userName = getUserName();

    var h1 = $('<div class="col-md-8"><h1>Recommendation System <small>emmmmmm</small></h1></div>');
    var h2 = $('<div class="col-md-2"><h1><small><span class="glyphicon glyphicon-user" aria-hidden="true"></span></small></h1></div>');
    if(userName != null){
        h2 = $('<div class="col-md-2"><h1><small><span class="glyphicon glyphicon-user" aria-hidden="true">'+userName+'</span></small></h1></div>')
    }
    var h3 = $('<div class="col-md-2"><h3><small>注销</small></h3></div>');

    headDiv.html(h1);
    headDiv.append(h2);
    headDiv.append(h3);

    h3.click(function () {
        window.storage.removeByKey("user");
        window.location.href = "login.html";
    });

    $("#head-page").html(headDiv);

    var nav = $('<ul class="nav nav-pills"></ul>');
    var navChild = $('<li role="presentation"><a href="../rank.html">排行</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=terror&page=1&size=9">恐怖</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=comedy&page=1&size=9">喜剧</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=love&page=1&size=9">爱情</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=action&page=1&size=9">动作</a></li>\n' +
        '            <li role="presentation"><a href="../type.html?type=all&page=1&size=9">全部</a></li>');

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
