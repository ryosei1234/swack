$(function () {
    $("dd").hide();
    $("body").append("<div id='glayLayer'></div><div id='overLayer'></div>");

     $("#glayLayer").click(function () {
        $(this).hide();
        $("#overLayer").hide();
    });

    $("dt").click(function () {
        $("#glayLayer").show();
        $("#overLayer").show().html($("+dd",this).html()).css({
        "margin-top":"-"+$("#overLayer").height()/2+"px",
        "margin-left":"-"+$("#overLayer").width()/2+"px"
        });
    });
});