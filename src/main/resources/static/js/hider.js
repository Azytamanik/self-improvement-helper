$('#btn-progress').click(function(){
    let item = $('.habits-progress');
    item.toggle();
    if (item.css('display') === 'none') {
        $('.first-arrow').html('arrow_drop_down');
    } else {
        $('.first-arrow').html('arrow_drop_up');
    }
});

$('#btn-completed').click(function(){
    let item = $('.habits-completed');
    item.toggle();
    if (item.css('display') === 'none') {
        $('.second-arrow').html('arrow_drop_down');
    } else {
        $('.second-arrow').html('arrow_drop_up');
    }
});