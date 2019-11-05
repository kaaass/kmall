jQuery(($) => {
    const KEY_AUTH = 'AUTH';
    const KEY_NAME = 'NAME';
    let storage = window.localStorage;

    let jumpTo = (dest, time = 2000) => {
        setTimeout(() => {
            location.href = dest;
        }, time);
    };

    let modal = (title, body) => {
        $('#msgModalTitle').html(title);
        $('#msgModalBody').html(body);
        $('#msgModal').modal('show');
    };

    let forbiddenHandler = (data) => {
        let json = JSON.parse(data);
        if (json.status === 403) {
            modal("错误", "您的登录已过期！正在跳转至登录页面");
            storage.removeItem(KEY_AUTH);
            jumpTo("/auth/login.html", 3000);
        }
        return data;
    };

    // 导航栏变更
    if (storage.getItem(KEY_AUTH) != null) {
        // 已经登录
        $('#name').text(storage.getItem(KEY_NAME));
        $('#register').remove();
        $('#my-dropdown').removeClass('invisible');
    } else {
        // 未登录
        $('#register').removeClass('invisible');
        $('#my-dropdown').remove();
    }

    // 暴露接口
    window.getAxiosInstance = () => {
        let auth = storage.getItem(KEY_AUTH);
        return axios.create({
            headers: {'Authorization': auth},
            transformResponse: [forbiddenHandler]
        });
    };
});