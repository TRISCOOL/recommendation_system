var KEY_USER = "user";
$(document).ready(function(){

    //本地存储
    window.storage = {
        typeStr:{
            putData:function (key,value) {
                localStorage.setItem(key,value);
            },
            getDataByKey:function (key) {
                return localStorage.getItem(key);
            }
        },
        typeJson:{
            putJson:function (key,value) {
                localStorage.setItem(key,JSON.stringify(value));
            },
            getJsonByKey:function (key) {
                let value = localStorage.getItem(key);
                if(value == null || value == ""){
                    return null;
                }

                return JSON.parse(value);
            }
        }
    }


});