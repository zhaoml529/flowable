/**
 * Description: 获取可切换的商家列表
 * Author: mingliang.zhao
 * Date: 2018/01/10
 */

function initVendorList() {
    $("#boardVendorId").chosen({
        no_results_text: "没有找到此商家：",//搜索无结果时显示的提示
        search_contains:true,   //关键字模糊搜索，设置为false，则只从开头开始匹配
        allow_single_deselect:true //是否允许取消选择
    });
    $.ajax({
        type: 'GET',
        dataType : "json",
        url: "/vendor/vendorChangeList",
        data: {},
        error: function () {
            console.log("请求商家列表异常!");
        },
        success:function(result){
            if (result.code == "0000") {
                $("#boardVendorId").empty();
                $("#boardVendorId").append('<option value="">--请选择商家--</option>');
                for(i in result.data) {
                    if(result.data[i].vendorId == $.cookie("vendorId")) {
                        $("#boardVendorId").append("<option value='"+result.data[i].vendorId+"' selected>"+result.data[i].vendorName+"</option>");
                    } else {
                        $("#boardVendorId").append("<option value='"+result.data[i].vendorId+"'>"+result.data[i].vendorName+"</option>");
                    }
                }
                $("#boardVendorId").trigger("liszt:updated");
            }
        }
    });
}

function changeVendor() {
    var vendorId = $('#boardVendorId').val();
    if(!vendorId) {
        return;
    }
    $("#vendorSelectForm").submit();
}
