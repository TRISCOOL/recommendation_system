$(document).ready(function(){
    //初始化数据
    var arr = [
        {id:1,img:"./images/img.jpg",replyName:"哦哦",beReplyName:"匿名",content:"xxxxxxx。",time:"2017-10-17 11:42:53",address:"深圳",osname:"",browse:"谷歌",replyBody:[]},
        {id:2,img:"./images/img.jpg",replyName:"匿名",beReplyName:"",content:"ooooooooooooo",time:"2017-10-17 11:42:53",address:"深圳",osname:"",browse:"谷歌",replyBody:[{id:3,img:"",replyName:"哦哦",beReplyName:"匿名",content:"xxxxxxxx",time:"2017-10-17 11:42:53",address:"",osname:"",browse:"谷歌"}]},
        {id:3,img:"./images/img.jpg",replyName:"哦哦",beReplyName:"匿名",content:"lllllllllllll",time:"2017-10-17 11:42:53",address:"深圳",osname:"win10",browse:"谷歌",replyBody:[]}
    ];
    $(function(){
        $(".comment-list").addCommentList({
            data:arr,
            add:""
        });

        $("#comment").click(function(){
            var obj = new Object();
            obj.img="./images/img.jpg";
            obj.replyName="匿名";
            obj.content=$("#content").val();
            obj.browse="深圳";
            obj.osname="win10";
            obj.replyBody="";
            $(".comment-list").addCommentList({data:[],add:obj});
        });
    })
});