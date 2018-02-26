/**
 * Created by Administrator on 2017/3/25.
 */
$(function(){
    // 初始化全局type变量 默认是all 不分类型

    var type = null;
    var filterList = {
        init: function () {

            // MixItUp plugin
            // http://mixitup.io
            $('#portfoliolist').mixitup({
                targetSelector: '.portfolio',
                filterSelector: '.filter',
                effects: ['fade'],
                easing: 'snap',
                // call the hover effect
                onMixEnd: filterList.hoverEffect()
            });

        },

        hoverEffect: function () {

            // Simple parallax effect
            $('#portfoliolist .portfolio').hover(
                function () {
                    $(this).find('.label').stop().animate({bottom: 0}, 200, 'easeOutQuad');
                    $(this).find('img').stop().animate({top: -30}, 500, 'easeOutQuad');
                },
                function () {
                    $(this).find('.label').stop().animate({bottom: -40}, 200, 'easeInQuad');
                    $(this).find('img').stop().animate({top: 0}, 300, 'easeOutQuad');
                }
            );

        }

    };

    // Run the show!
    filterList.init();
    // 初始化分页插件

    // 拿到置顶图
    $.ajax({
        url: "http://127.0.0.1/get/topImage",
        type: "POST",
        data: JSON.stringify({}),
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        success: function (result) {
            if(result.status==200){
                $.each(result.data, function(i,item){

                    $("#topImage"+i).attr("src",item.topImage);
                });
            }
        }
    });

    var data = {"type":type,"currentPage":1,"pageSize":40};// 初始化默认链接all
    $.ajax({
        url: "http://127.0.0.1/all/grid",
        type: "POST",
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        success: function (result) {
            if(result.status==200){
                /*$("#portfoliolist").empty();  正常运行时要解开*/
                $.each(result.data, function(i,item){
                    $("#portfoliolist").append(" <div class='portfolio "+item.type+" mix_all col-center-block' data-cat='"+item.type+"' style='display: inline-block; opacity: 1;'>"+
                        "<div class='portfolio-wrapper '><a href='"+item.http+"'><img id="+item.id+" class='img-responsive grid-image ' "+
                        "src='"+item.image+"'alt='xxx'/></a><div class='gir'><h5>"+item.name+"</h5><p>"+item.describtion+"</p></div></div></div>");
                });
                // 第一次不执行change
                var flag = false

                $.jqPaginator('.pagination', {
                    totalPages: result.totalPage,
                    visiblePages: 3,
                    currentPage:  result.currentPage,
                    first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                    prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                    next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                    last: '<li class="last"><a href="javascript:void(0);">最后页</a></li>',
                    page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                    onPageChange: function (currentPage) {
                        if(flag){
                            queryAllGrid(currentPage)
                        }else{
                            flag=true;
                        }

                    }
                });
            }
        }
    });


    $(".type").click(function () {
        if($(this).attr("id")=="all"){
            type = null;
        }else{
            type = $(this).attr("data-filter")
        }
        queryAllGrid(1)
    });

    function queryAllGrid(currentPage) {
        var typeJson = {"type":type,"currentPage":currentPage,"pageSize":40}

        $.ajax({
            url: "http://127.0.0.1/all/grid",
            type: "POST",
            data: JSON.stringify(typeJson),
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (result) {
                if(result.status==200){
                    $("#portfoliolist").empty().removeAttr("style");
                    $("#fenyeul").empty();

                    $.each(result.data, function(i,item){
                        $("#portfoliolist").append(" <div class='portfolio "+item.type+" mix_all col-center-block' data-cat='"+item.type+"' style='display: inline-block; margin-left:5px; opacity: 1;'>"+
                            "<div class='portfolio-wrapper '><a href='/"+item.http+"'><img id="+item.id+" class='img-responsive grid-image ' "+
                            "src='"+item.image+"'alt='xxx'/></a><div class='gir'><h5>"+item.name+"</h5><p>"+item.describtion+"</p></div></div></div>");
                    });
                // 第一次不执行change
                    var flag = false
                    $.jqPaginator('.pagination', {
                        totalPages: result.totalPage,
                        visiblePages: 3,
                        currentPage:  result.currentPage,
                        first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
                        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
                        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
                        last: '<li class="last"><a href="javascript:void(0);">最后页</a></li>',
                        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
                        onPageChange: function (currentPage) {
                            if(flag){
                                queryAllGrid(currentPage)
                            }else{
                                flag=true;
                            }

                        }
                    });
                }


            }
        });
    }

});