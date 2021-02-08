var main = {
    init: function(){
        var _this = this;
        $('#save-btn').on('click', function(){
            _this.save();
        });
    },
    save: function(){
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };
        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'JSON',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert("저장되었습니다");
            window.location.href="/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};
main.init();