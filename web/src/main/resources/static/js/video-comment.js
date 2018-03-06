$(document).ready(function(){


    let movieId = GetQueryString("id");
    if(movieId != null){
        init(movieId);
    }else {
        alert("id is null");
    }

    $("#comment").on('click',function () {
        let user = window.storage.typeJson.getJsonByKey("user");
        if(user == null){
            alert("请先登陆！");
            window.location.href = "../login.html";
        }

        let commentContent = $("#content").val();

        var obj = new Object();
        //obj.img=user.logo;
        obj.img = "./images/img.jpg";
        obj.replyName=user.userName;
        obj.content=commentContent;
        obj.browse="深圳";
        obj.osname="win10";
        obj.replyBody="";

        $.ajax({
            type:"post",
            url:"/comment/insert",
            headers: {
                "Authorization":user.token
            },
            data:{
                "content":commentContent,
                "movieId":movieId
            },
            async: true,
            success:function(response){
                var code = response.code;
                if(code == 200){
                    $(".comment-list").addCommentList({data:[],add:obj});
                }else {
                    alert("评论失败。。。。");
                }

            },
            error:function(response){

            }
        });
    });


    function init(movieId) {
        $.ajax({
            type:"get",
            url:"/movie/get?id="+movieId,
            data:null,
            async: false,
            success:function(response){
                var code = response.code;
                if(code == 200){
                    let movieInfo = response.data;
                    //加载电影
                    let movie = movieInfo.movie;
                    loadMovie(movie);

                    //加载评论
                    let comments = movieInfo.comment;
                    loadComment(comments);


                    let favors = movieInfo.favorCount;
                    loadFavor(favors);
                }else {
                    alert("页面加载失败。。。。");
                }

            },
            error:function(response){

            }
        });
    }

    function loadMovie(movie) {
        if(movie == undefined || movie == null){
            alert("电影加载失败。。。。");
            return;
        }

        let source = movie.address;
        if(source != undefined && source != null){
            let video = $('<video id="my-video" class="video-js" controls preload="auto" width="640" height="400"\n' +
                '                               poster="http://vjs.zencdn.net/v/oceans.png" data-setup="{}">\n' +
                '                            <source src="'+source+'" type=\'video/mp4\'>\n' +
                '                            <!--<source src="MY_VIDEO.webm" type=\'video/webm\'>-->\n' +
                '                            <p class="vjs-no-js">\n' +
                '                                To view this video please enable JavaScript, and consider upgrading to a web browser that\n' +
                '                                <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>\n' +
                '                            </p>\n' +
                '                        </video>');

            $("#movie-details").html(video);
        }

    }

    function loadComment(comments) {
        let arr = comments;
        if(arr != null && arr.length > 0){
            $(".comment-list").addCommentList({
                data:arr,
                add:""
            });
        }
    }

    function loadFavor(count) {
        $("#favor-count").html(count);
    }

    //初始化数据
/*    var arr = [
        {id:1,img:"./images/img.jpg",replyName:"哦哦",beReplyName:"匿名",content:"xxxxxxx。",time:"2017-10-17 11:42:53",address:"深圳",osname:"",browse:"谷歌",replyBody:[]},
        {id:2,img:"./images/img.jpg",replyName:"匿名",beReplyName:"",content:"ooooooooooooo",time:"2017-10-17 11:42:53",address:"深圳",osname:"",browse:"谷歌",replyBody:[{id:3,img:"",replyName:"哦哦",beReplyName:"匿名",content:"xxxxxxxx",time:"2017-10-17 11:42:53",address:"",osname:"",browse:"谷歌"}]},
        {id:3,img:"./images/img.jpg",replyName:"哦哦",beReplyName:"匿名",content:"lllllllllllll",time:"2017-10-17 11:42:53",address:"深圳",osname:"win10",browse:"谷歌",replyBody:[]}
    ];*/
});