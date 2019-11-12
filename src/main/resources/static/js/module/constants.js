/**
 * 常量声明
 */
define([], function () {

    return {
        KEY_AUTH: 'AUTH',
        KEY_NAME: 'NAME',
        KEY_ADMIN_AUTH: 'ADMIN_AUTH',

        PARAM_ID: 'id',
        PARAM_TYPE: 'type',
        PARAM_CART_IDS: 'cartIds',

        TEMPLATE_PATH: '/templates/',
        TEMPLATE_SUFFIX: '.hbs',

        TITLE_SUFFIX: " - KMall",

        orderRequestType: {
            SINGLE: 'SINGLE',
            MULTI: 'MULTI'
        }
    };
});