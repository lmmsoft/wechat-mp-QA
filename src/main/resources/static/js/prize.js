$(function () {
    var rotateTimeOut = function () {
        $('#rotate').rotate({
            angle: 0,
            animateTo: 2160,
            duration: 8000,
            callback: function () {
                alert('网络超时，请检查您的网络设置！');
            }
        });
    };
    var bRotate = false;
    // 转动
    var rotateFn = function (awards, angles) {
        bRotate = !bRotate;
        $('#rotate').stopRotate();
        $('#rotate').rotate({
            angle: 0,
            animateTo: angles + 1800,
            duration: 6000,
            callback: function () {
                bRotate = !bRotate;
                $('#mark').fadeIn();
                $('#mark').css("z-index", 4);
                $('#mark').click(function () {
                    $(this).fadeOut();
                })
            }
        })
    };
    $('.submit').click(function () {
        if (bRotate) return;//如果正在转动，点击时将不再重复执行
        var item = $('.prize-id').html() * 1;
        rotateFn(item - 1, 360 - item * 30);
    });
});

// 设置奖项
function rnd(n, m) {
    return Math.floor(Math.random() * (m - n) + n)
}
