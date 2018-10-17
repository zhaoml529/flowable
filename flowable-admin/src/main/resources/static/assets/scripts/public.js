(function ($, Public) {
    Public.ajaxPostForm = function (url, params, callback, errCallback) {
        $.ajax({
            type: "POST",
            url: url,
            data: params,
            success: function (data, status) {
                if (callback) {
                    callback(data, status);
                }
            },
            error: function (xhr, status, error) {
                if (xhr.status == 401) {//没有登录
                    bootbox.alert('登录已过期，请重新登录！');
                } else {
                    bootbox.alert('操作失败了哦，请检查您的网络链接！');
                }
                if (errCallback) {
                    errCallback(xhr, status, error);
                }
            }
        });
    };
    Public.ajaxGet = function (url, params, callback, errCallback) {
        $.ajax({
            type: "GET",
            url: url,
            data: params,
            success: function (data, status) {
                if (callback) {
                    callback(data);
                }
            },
            error: function (xhr, status, error) {
                if (xhr.status == 401) {//没有登录
                    bootbox.alert('登录已过期，请重新登录！');
                } else {
                    bootbox.alert('操作失败了哦，请检查您的网络链接！');
                }
                if (errCallback) {
                    errCallback(xhr, status, error);
                }
            }
        });
    };

    Public.initForm = function (opt) {
        opt.bindForm.validator({
            msgWrapper: null,
            msgClass: opt.msgClass || null,
            validClass: opt.validClass,
            invalidClass: opt.invalidClass,
            bindClassTo: opt.bindClassTo,
            display: opt.display,
            msgIcon: '',
            target: opt.target || null,
            msgMaker: function (opt) {
                return opt.msg;
            },
            focusInvalid: false,
            // ignore: ":hidden",
            fields: opt.fields,
            rules: opt.rules || {},
            valid: function (form) {
                if (opt.valid) {
                    opt.valid(form);
                } else {
                    //表单验证通过
                    var disabled = $(form).find(':disabled').removeAttr('disabled');
                    var formData = $(form).serialize();
                    disabled.attr('disabled', 'disabled');
                    Public.ajaxPostForm(opt.url, formData, function (data, status) {
                        console.log(JSON.stringify(data));
                        if (data.code === '0000') {
                            bootbox.alert(data.message, function () {
                                window.location.assign(opt.successUrl);
                            });
                        } else {
                            bootbox.alert(data.message);
                        }

                    });
                }
            },
            timely: 2
        });
        if (opt.triggerBtn) {
            opt.triggerBtn.click(function (e) {
                e.preventDefault();
                opt.bindForm.trigger('validate');
            })
        }
    };
})(jQuery, window.Public = {});

