var dataReflush ;//index.jsp页面刷新数据方法

setTabHeight();
$(window).bind('resize',function(){
	setTabHeight();
});

function setTabHeight(){
	var winHeight = $(window).height();
	var tab = $('#main-bd');
	var h = winHeight - tab.offset().top - 1;
	tab.height(h);
	$("#page-sidebar").height(h);
};

$('#page-tab').ligerTab({ 
	height: '100%', 
	changeHeightOnResize : true,
	onBeforeAddTabItem : function(tabid){
		setCurrentNav(tabid);
	},
	onAfterAddTabItem : function(tabid){
		
	},
	onAfterSelectTabItem : function(tabid){
		setCurrentNav(tabid);
	},
	onBeforeRemoveTabItem : function(tabid){
		//if(confirm('要关闭吗')){
			//return true;
		//} else{

			//return false;
		//}
	},
	onAfterLeaveTabItem: function(tabid){
//		switch(tabid){
//			case 'setting-vendorList' :
//				console.log(tabid);
//				break ;
//		}

	},
	onAfterSelectTabItem: function(tabid){
			switch(tabid){
			case 'index' :
				dataReflush && dataReflush();
				break ;
		}
	
	}
});

function setCurrentNav(tabid){
	if(!tabid){return ;}
	var pre = tabid.match((/([a-zA-Z]+)[-]?/))[1];
	$('#nav > li').removeClass('current');
	$('#nav > li.item-' + pre).addClass('current');
};

//增加页签
var tab = $("#page-tab").ligerGetTabManager();
$('#nav').on('click', '[rel=pageTab]', function(e){
	e.preventDefault();
	var right = $(this).data('right');
	if (right && !Business.verifyRight(right)) {
		return false;
	}
	var tabid = $(this).attr('tabid'), url = $(this).attr('href'), showClose = $(this).attr('showClose'), text = $(this).attr('tabTxt') || $(this).text().replace('>',''),parentOpen = $(this).attr('parentOpen');
	if(parentOpen){
		parent.tab.addTabItem({tabid: tabid, text: text, url: url, showClose: showClose});
	} else {
		tab.addTabItem({tabid: tabid, text: text, url: url, showClose: showClose});
	}
	return false;
});

// var token = $.cookie("token");
tab.addTabItem({tabid: 'index', text: '首页', url:'/main', showClose: false});

$("#fixed a[target=navtab]").click(function(e){
	var btn = $(this);
	tab.addTabItem({tabid: btn.attr("tabid"), text: btn.attr("title"), url: btn.attr("href")||btn.attr("url"), showClose: true});
	e.preventDefault();
});
$(".tops a[target=navtab]").click(function(e){
	var btn = $(this);
	tab.addTabItem({tabid: btn.attr("tabid"), text: btn.attr("title"), url: btn.attr("href")||btn.attr("url"), showClose: true});
	e.preventDefault();
});
	
$(window).load(function(e) {
	var navItems = $('#nav .item');
	$('#scollUp').click(function(){
		var visibleEle = navItems.filter(':visible');
		if(visibleEle.first().prev().length > 0) {
			visibleEle.first().prev().show(500);
			visibleEle.last().hide();
		};
	});
	$('#scollDown').click(function(){
		var visibleEle = navItems.filter(':visible');
		if(visibleEle.last().next().length > 0) {
			visibleEle.first().hide();
			visibleEle.last().next().show(500);
		} else {
			
		}
	});
});
