jQuery(($) => {
    const KEY_AUTH = 'AUTH';
    let storage = window.localStorage;
    let $btn = $('[type=submit]'),
        $phone = $('[name=phone]'),
        $password = $('[name=passwd]'),
        $passwordConf = $('[name=passwdConfirm]');

    let warn = (msg) => {
        $('#tips').remove();
        $('<div id="tips" class="alert alert-warning" role="alert">\n' +
            "            <p>\n" +
            `                <strong>错误</strong> ${msg}\n` +
            "            </p></div>").insertBefore('#form');
    };

    let success = (msg) => {
        $('#tips').remove();
        $('<div id="tips" class="alert alert-success" role="alert">\n' +
            "            <p>\n" +
            `                <strong>成功</strong> ${msg}\n` +
            "            </p></div>").insertBefore('#form');
    };

    let jumpTo = (dest, time = 2000) => {
        setTimeout(() => {
            location.href = dest;
        }, time);
    };

    // 检查登录情况
    if (storage.getItem(KEY_AUTH) !== null) {
        warn("您已经登录！");
        jumpTo("../index.html", 1000);
        return;
    }

    // 注册登录操作
    $btn.click(() => {
        var phone = $phone.val(),
            password = $password.val(),
            passwordConf = $passwordConf.val();

        // 参数校验
        if (phone.length <= 0) {
            warn("请输入手机号码！");
            return;
        }
        if (password.length <= 0) {
            warn("请输入密码！");
            return;
        }
        if (password !== passwordConf) {
            warn("两次输入密码不一致！");
            return;
        }

        // 注册请求
        axios.post('../auth/register', {
            phone: phone,
            password: password
        })
            .then((response) => {
                var data = response.data;
                console.log(data);
                if (data.status !== 200) {
                    warn(data.message);
                    return;
                }
                // 跳转到登录页面
                success("注册成功！正在跳转至登录页面");
                jumpTo("login.html");
            })
            .catch((e) => {
                console.error("注册失败：", e);
                warn("注册失败！请检查网络后再试");
            });
    });
});