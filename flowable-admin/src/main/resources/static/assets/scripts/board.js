/**
 * Description: 渲染菜板块和菜单JS
 * Author: mingliang.zhao
 * Date: 2018/1/2
 */

function initBoard() {
    $(".header_real_name").text(decodeURI($.cookie("realName")));
    $.ajax({
        url: "/board/menu",
        data: {},
        type: "GET",
        dataType: 'json',
        success: function (result) {
            var data = result.data;
            if (result.code == "0000") {
                getBoardMenu(data);
            } else {
                bootbox.alert(result.msg);
            }
        }
    });
}

// 顶部board菜单
function getBoardMenu(data) {
    var board = "";
    for (var i = 0; i < data.length; i++) {
        board += '<li id="board_' + i + '">';
        board += '<a href="#" onclick="getSideBar(' + data[i].id + ');">' + data[i].name + '</a>';
        board += '</li>';
    }
    $('#board_menu').prepend($(board));
    $("li[id^='board_']").click(function(){
        $("#board_menu li.active").removeClass("active");
        $(this).addClass("active");
    });
    $("li[id='board_0'] a").trigger('click');
}

// 根据boardId获取左侧菜单
function getSideBar(boardId) {
    $('#pageSideBarMenu').find('li.open').find('.select1').remove();
    $('#sidebar_loading').addClass("board-loading");
    $.ajax({
        url: "/board/system",
        data: {"boardId" : boardId},
        type: "POST",
        dataType: 'json',
        success: function (result) {
            if (result.code == "0000") {
                getBoardSystem(result.data);
            } else {
                bootbox.alert(result.msg);
            }
        }
    });
}

function getBoardSystem(data) {
    $('#pageSideBarMenu').children().remove();
    if(data.length <= 0) {
        $('#sidebar_loading').removeClass("board-loading");    // 删除loading
        $("#pageSideBarMenu").append('<li>对不起，您所访问的板块未被授权!</li>');
        return;
    }
    for (var i = 0; i < data.length; i++) {
        var icon = "";
        if(!data[i].icon) {
            icon = "icon-bookmark-empty";
        } else {
            icon = data[i].icon;
        }
        var subMenu = $('<ul class="sub-menu"></ul>');
        getSubMenu(data[i].permission, subMenu);
        var menu = $('<li class="start ">' +
            '<a href="javascript:void(0);" onclick="menuClick(this)">' +
            '<i class="'+icon+'"></i>' +
            '<span class="title">'+data[i].name+'</span>' +
            '<span class="arrow "></span>' +
            '</a></li>');
        $(menu).append(subMenu);
        $("#pageSideBarMenu").append(menu);
    }
    $('#sidebar_loading').removeClass("board-loading");    // 删除loading
    $("#pageSideBarMenu").find('li').eq(0).find('a').eq(0).trigger('click');
}

function menuClick(thisDom) {
    $("#pageSideBarMenu li").find('.select1').remove();
    $(thisDom).append('<span class="select1 "></span>');
}

function getSubMenu(children, parent) {
    for(var menu in children) {
        // 如果有子菜单则遍历
        if (children[menu].children.length > 0) {
            //创建一个子节点li
            var li = $('<li></li>');
            //将li的文本设置好，并马上添加一个空白的ul子节点，并且将这个li添加到父亲节点中
            $(li).append('<a href="javascript:void(0);" id="'+children[menu].id+'">'+children[menu].name+'<span class="arrow"></span></a>').append('<ul class="sub-menu"></ul>').appendTo(parent);
            //将空白的ul作为下一个递归遍历的父亲节点传入
            getSubMenu(children[menu].children, $(li).children("ul"));
        } else {
            //如果该节点没有子节点，则直接将该节点li以及文本创建好直接添加到父亲节点中
            $("<li></li>").append('<a href="javascript:void(0);" id="'+children[menu].id+'" onclick="toPage('+children[menu].id+',\''+children[menu].name+'\',\''+children[menu].uri+'\', this);">'+children[menu].name+'</a>').appendTo(parent);
        }
    }
}

function toPage(permissionId, permissionName, path, thisDom){
    var token = $.cookie("token");
    var current = new Date().getTime();
    $("#pageSideBarMenu li").find('.active').removeClass("active");
    $(thisDom).parent("li").parent("ul").find("li.active").removeClass("active");
    $(thisDom).parent("li").parent("ul").find("li.open a").trigger("click");
    $(thisDom).parent("li").parent("ul").find("li.open").removeClass("open");
    $(thisDom).parent("li").addClass("active");
    tab.addTabItem({tabid: permissionId, text: permissionName, url:path + '?_time='+current+'&token=' + token, showClose: true});
}
