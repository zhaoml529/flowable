/**
 * Description: 渲染菜单页JS
 * Author: bingqiang.lin
 * Date: 2017/9/18
 */

function initSideBar() {
    var userId = $.cookie("userId");
    if(typeof(userId)=="undefined"){
        alert("用户id为空，获取菜单失败!");
    }
    $.ajax({
        url: "/user/permissions",
        data: {"userId": userId},
        type: "POST",
        dataType: 'json',
        xhrFields:{
            withCredentials:true
        },
        success:function (result) {
            var data = result.data;
            var currSystemId = $.cookie("systemId");
            var currPermissionId = $.cookie("permissionId");
            if (result.code == "0000" && data.length > 0) {
                var txt = "";
                for (var i = 0; i < data.length; i++) {
                    txt += '<li id="txt">';
                    txt += '               <a href="javascript:;" id="systemId_'+data[i].systemId+'">';
                    txt += '                   <i class="icon-bookmark-empty"></i> ';
                    txt += '                    <span class="title">'+data[i].permissionName+'</span>';
                    txt += '                    <span class="arrow "></span>';
                    txt += '                    </a>';
                    txt += '                    <ul class="sub-menu">';
                    txt += createChild(data[i], currSystemId, currPermissionId);
                    txt += '                    </ul>';
                    txt += '                </li>';
                }
                $(".page-sidebar-menu").append($(txt));
                $(".header_real_name").text(decodeURI($.cookie("realName")));
                $("a[id=systemId_"+currSystemId+"]").click();
                $("li[id=permissionId_"+currPermissionId+"]").addClass("active");
            } else {
                console.log("initSideBar: "+result.msg);
            }
        }
    });

    function createChild(data, currSystemId, currPermissionId) {
        var txt = "";
        var systemId = data.systemId;
        var permission;
        var path;
        for (var i = 0 ; i < data.menuPermissionDtoList.length; i++) {
            permission  = data.menuPermissionDtoList[i];
            var systemUrl = permission.path;
            txt += '                        <li id="permissionId_'+permission.permissionId+'">';
            if (systemUrl === "" || systemUrl === null) {
                txt += '                            <a id="menuUri" href="javascript:;" onclick="toMenu(this,'+systemId +','+permission.permissionId +',\''+ permission.uri +'\',\'' + permission.permissionValue +'\',\'' + permission.permissionName +'\')" >';
            } else {
                txt += '                            <a id="menuUri" href="javascript:;" onclick="toMenu(this,'+systemId +','+permission.permissionId +',\''+ systemUrl + permission.uri+'\',\'' + permission.permissionValue  +'\',\''+ permission.permissionName +'\')">';
            }
            txt += '                                <span id="menuName">'+permission.permissionName+'</span>';
            txt += '                            </a>';
            txt += '                        </li>';
        }
        return txt;
    }
}

function toMenu(thisDom,systemId, permissionId, uri,permissionValue,permissionName) {
	//修改菜单项css样式
    $("#pageSideBarMenu ul.sub-menu li.active").removeClass("active");
    $(thisDom).parents("li:first").addClass("active");
	
    var token = $.cookie("token");
    $.cookie("systemId",null, {path:"/"});
    $.cookie("permissionId",null, {path:"/"});
    $.cookie("systemId", systemId, {path:"/"});
    $.cookie("permissionId", permissionId, {path:"/"});
    var current = new Date().getTime();
    if(typeof(isTab)!="undefined" && isTab == true){
    		tab.addTabItem({tabid: permissionValue, text: permissionName, url:uri + '?_time='+current+'&token=' + token, showClose: true});
    }else{
    		window.contentFrame.location.href = uri + '?_time='+current+'&token=' + token;
    }
}