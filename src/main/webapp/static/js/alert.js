jQuery(document).ready(function($) {
    // 验证登录

    $.ajax({
        url: "http://127.0.0.1/login",
        type: "POST",
        data: JSON.stringify({}),
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        success: function (result) {
            console.log(result)
            if(result.status==200){
                $(".alert").css("opacity",0);
                $("#head-zhuxiao").css("opacity",1)
                $("#head-user").html("当前用户："+result.data.username).attr("disabled","disabled");
            }
            if(result.status==302){
                $('.theme-popover').fadeIn(500);
            }

        }
    });



    $('.theme-login').click(function(){
        $('.theme-popover').fadeIn(500);
    })
    $('.theme-poptit .close').click(function(){
        $('.theme-popover').slideUp(500);
    })

    $("#denglu-button").click(function () {
        var userJson = {"username":$("#dl_username").val(),"password":$("#dl_password").val()}
        $.ajax({
            url: "http://127.0.0.1/login",
            type: "POST",
            data: JSON.stringify(userJson),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (result) {
                if(result.status==200){
                    $('.theme-popover').slideUp(500);
                    $("#head-user").html("当前用户："+result.data.username).attr("disabled","disabled");
                    $(".alert").css("opacity",0);
                    $("#head-zhuxiao").css("opacity",1)
                    window.location.reload();
                }else{
                    $("#dl_fail").html("请检查用户名或密码！");
                    $(".alert").css("opacity",1);
                }

            }
        });
    })
    $("#head-zhuxiao").click(function () {
        $.ajax({
            url: "http://127.0.0.1/logout",
            type: "POST",
            data: JSON.stringify({}),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (result) {
                console.log(result)
                if(result.status==200){
                    $("#head-user").html("登录/注册").removeAttr("disabled");
                    $("#head-zhuxiao").css("opacity",0)
                    window.location.reload();
                }

            }
        });

    })

    $("#zhuce-button").click(function () {

        if($("#zc_username").val()==""||$("#zc_username").val().length>10){
            $(".alert").css("opacity",1).html("用户名不能为空或大于10位");
            $("#zc_username").focus();
            return
        }
        if($("#zc_email").val()==""||!($("#zc_email").val().match(/^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/))){
            $("#zc_email").focus();
            $(".alert").css("opacity",1).html("邮箱格式不对");
            return
        }
        if($("#zc_password").val()==""||$("#zc_password").val().length<6){
            $("#zc_password").focus();
            $(".alert").css("opacity",1).html("密码不能少于6位");
            return
        }

        if($("#zc_repassword").val()==""||$("#zc_repassword").val().length<6){
            $("#zc_repassword").focus();
            return
        }



        if($("#zc_password").val()==$("#zc_repassword").val()){
            var userJson = {"username":$("#zc_username").val(),"password":$("#zc_password").val(),"email":$("#zc_email").val()}
            console.log(JSON.stringify(userJson))
            $.ajax({
                url: "http://127.0.0.1/register",
                type: "POST",
                data: JSON.stringify(userJson),
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',
                success: function (result) {
                    console.log(result)
                    if(result.status==200){
                        $(".alert").css("opacity",0);
                        $("#denglu").css("display","block");
                        $("#zhuce").css("display","none");
                    }
                    if(result.status==400){
                        $("#zc_fail").html("当前用户名已被注册");
                        $(".alert").css("opacity",1);
                        $("#zc_username").css("color","red").focus();
                        $("#zc_username").focus(function () {
                            $("#zc_username").css("color","black");
                        })

                    }

                }
            });
        }else{
            $("#zc_repassword").css("color","red");
            $("#zc_repassword").focus(function () {
                $("#zc_repassword").css("color","black")
            })

        }
    })

    $("#change-denglu").click(function(){
        $("#zhuce").css("display","none")
        $("#denglu").css("display","block")
        $("#wangjimima").css("display","none")
    });
    $("#change-zhuce").click(function(){
        $("#denglu").css("display","none")
        $("#zhuce").css("display","block")
        $("#wangjimima").css("display","none")
    });

    /*忘记密码*/
    $("#forget-password").click(function () {
        $("#denglu").css("display","none");
        $("#zhuce").css("display","none")
        $("#wangjimima").css("display","block");
    })
    $("#forget-button").click(function () {


        if($("#forget-email").val()==""||!($("#forget-email").val().match(/^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/))){

            $("#wj-email").html("邮箱格式不对,请检查！");
            $(".alert").css("opacity",1);
            return
        }
         $(this).attr("disabled","disabled");
        var emailJson = {"email":$("#forget-email").val()}
        console.log("dxxx")
        $.ajax({
            url: "http://127.0.0.1/forget/password",
            type: "POST",
            data: JSON.stringify(emailJson),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (result) {
                alert(result.status)
                if(result.status==200){
                    $("#wj-email").html("申请成功,请登陆邮箱完成操作！");
                    $(".alert").css("opacity",1)
                    var i = 60;
                    var t=setInterval(function () {
                        $("#forget-button").html(--i)
                        if(i<1){
                            clearInterval(t);
                            $("#forget-button").removeAttr("disabled").html("申请重置");
                        }
                    },500)
                }
            }
        });


    })


})




