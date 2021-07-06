// 服务器根路径
window.BASE_URL = "";
/**
 * RequireJS 配置
 */
requirejs.config({
    baseUrl: window.BASE_URL + "/js",
    shim: {
        bootstrap: {
            deps: [ "jquery" ]
        }
    },
    paths: {
        axios: "axios.min",
        handlebars: "handlebars.min",
        bootstrap: "bootstrap.bundle.min",
        jquery: "jquery.slim.min"
    },
    waitSeconds: 5
});
