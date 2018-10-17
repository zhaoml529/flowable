var tabtool = {};
//公共业务
(function($,windows){
	/**
	 * 打开一个标签页
	 * @tabid : 标签id，使用资源码
	 * @tabTitle : 标签显示的名称
	 * @url : 页面地址
	 * @showClose : 是否可以关闭 true可以，false不可
	 */
    tabtool.openTab = function (tabid, tabTitle, url, showClose) {
        var hostArr = document.domain.split(".");
        //hostArr.length > 3 意味在使用*.oh.quanshishequ.com
        var topDomain = hostArr.length > 3 ? document.domain.substring(document.domain.indexOf(".") + 1, document.domain.length) : document.domain;
        //设置document.domain为主页面域名
        document.domain = topDomain;
        if (window.parent && window.parent.tab) {
            //打开页签
            window.parent.tab.addTabItem({tabid: tabid, text: tabTitle, url: url, showClose: showClose});
        } else {//没有就导向新页面
            window.location.href = url;
        }
    }
    //关闭当前标签页
    tabtool.closeCurrentTab = function () {
        var hostArr = document.domain.split(".");
        //hostArr.length > 3 意味在使用*.oh.quanshishequ.com
        var topDomain = hostArr.length > 3 ? document.domain.substring(document.domain.indexOf(".") + 1, document.domain.length) : document.domain;
        //设置document.domain为主页面域名
        document.domain = topDomain;
        if (window.parent && window.parent.tab) {
            //打开页签
            window.parent.tab.removeSelectedTabItem();
        }
    }
    //重新加载当前标签页
    tabtool.reloadCurrentTab = function () {
        var hostArr = document.domain.split(".");
        //hostArr.length > 3 意味在使用*.oh.quanshishequ.com
        var topDomain = hostArr.length > 3 ? document.domain.substring(document.domain.indexOf(".") + 1, document.domain.length) : document.domain;
        //设置document.domain为主页面域名
        document.domain = topDomain;
        if (window.parent && window.parent.tab) {
            //重新加载页签
            window.parent.tab.reload(window.parent.tab.getSelectedTabItemID());
        }
    }
    /**
     * 刷新指定标签页
     * @tabId : 标签页id，资源码
     */
    tabtool.reloadTab = function (tabId) {
        var hostArr = document.domain.split(".");
        //hostArr.length > 3 意味在使用*.oh.quanshishequ.com
        var topDomain = hostArr.length > 3 ? document.domain.substring(document.domain.indexOf(".") + 1, document.domain.length) : document.domain;
        //设置document.domain为主页面域名
        document.domain = topDomain;
        if (window.parent && window.parent.tab) {
            //重新加载
            window.parent.tab.reload(tabId);
        }
    }
})(jQuery,window)

