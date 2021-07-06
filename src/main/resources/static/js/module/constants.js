/**
 * 常量声明
 */
define([], function () {

    const BASE_URL = window.BASE_URL;
    const API_BASE_URL = BASE_URL + "";

    return {
        KEY_AUTH: 'AUTH',
        KEY_NAME: 'NAME',
        KEY_ADMIN_AUTH: 'ADMIN_AUTH',

        PARAM_ID: 'id',
        PARAM_TYPE: 'type',
        PARAM_CART_IDS: 'cartIds',

        TEMPLATE_PATH: BASE_URL + '/templates/',
        TEMPLATE_SUFFIX: '.hbs',

        TITLE_SUFFIX: " - KMall",

        orderRequestType: {
            SINGLE: 'SINGLE',
            MULTI: 'MULTI'
        },

        BASE_URL: BASE_URL,
        API_BASE_URL: API_BASE_URL,

        TIME_AREA: +8,
    };
});