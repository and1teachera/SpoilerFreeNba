$(function () {
    $('#datepicker').datepicker({
        format: "dd/mm/yyyy",
        autoclose: false,
        keepOpen: true,
        todayHighlight: true,
        showOtherMonths: true,
        selectOtherMonths: true,
        changeMonth: true,
        changeYear: true,
        orientation: "button"
    }).show().on('changeDate', function (e) {

    });
});