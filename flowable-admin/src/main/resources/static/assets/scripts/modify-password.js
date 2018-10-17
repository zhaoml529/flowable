/**
 * Description: 修改密码
 * Author: mingliang.zhao
 * Date: 2017/12/21
 */

function initPwdForm() {
    $('#pwdForm').validator({
        messages: {
            match: "此处与新密码不一致",
        },
        fields: {
            originalPwd: {
                rule: 'required',
                target: '#originalMsg'
            },
            newPwd: {
                rule: 'required;length(6~16);',
                target: '#newMsg'
            },
            confirmPwd: {
                rule: 'required;match(newPwd);',
                target: '#confirmMsg'
            }
        },
        valid: function(form){
            // 表单验证通过，提交表单
            $.post("/update/password", $(form).serialize())
                .done(function(d){
                    $('#pwdModifyBtn').removeClass('disabled');
                    if(d.code == "0000") {
                        $('#pwdForm')[0].reset();
                        $('#pwdModal').modal('hide');
                    }
                    bootbox.alert(d.message);
                });
        },
        invalid: function (form) {
            $('#pwdModifyBtn').removeClass('disabled');
        },
        timely: 3
    });
}

function modifyPassword() {
    $('#pwdModifyBtn').addClass('disabled');
    $('#pwdForm').trigger('validate');
}

function cancelPwd() {
    $('#pwdModal').modal('hide');
    $('#pwdForm')[0].reset();
}
