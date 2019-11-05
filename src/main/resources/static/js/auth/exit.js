jQuery(($) => {
    const KEY_AUTH = 'AUTH';
    let storage = window.localStorage;

    let jumpTo = (dest, time = 2000) => {
        setTimeout(() => {
            location.href = dest;
        }, time);
    };

    storage.removeItem(KEY_AUTH);
    jumpTo('/');
});