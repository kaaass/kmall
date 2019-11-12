/**
 * 后台首页
 */
require([
    'jquery',
    'module/functions',
    'module/constants',
    'module/auth',
    'bootstrap'], function ($, functions, constants, auth, _) {

    let request = auth.getAxiosInstance(true);

    // 加载图标
    feather.replace();
});

// TODO 更换图表为销售数据

// Graphs
var ctx = document.getElementById('myChart')
// eslint-disable-next-line no-unused-vars
var myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [
            'Sunday',
            'Monday',
            'Tuesday',
            'Wednesday',
            'Thursday',
            'Friday',
            'Saturday'
        ],
        datasets: [{
            data: [
                15339,
                21345,
                18483,
                24003,
                23489,
                24092,
                12034
            ],
            lineTension: 0,
            backgroundColor: 'transparent',
            borderColor: '#007bff',
            borderWidth: 4,
            pointBackgroundColor: '#007bff'
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: false
                }
            }]
        },
        legend: {
            display: false
        }
    }
})