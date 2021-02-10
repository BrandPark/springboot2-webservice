var main = {
    init: function(){
        var _this = this;
        $('#save-btn').on('click', function(){
            _this.save();
        });
        $('#update-btn').on('click', function(){
            _this.update();
        });
        $('#delete-btn').on('click', function(){
            _this.delete();
        })
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
    },
    update: function(){
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'put',
            url: '/api/v1/posts/' + id,
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data)
        }).done(function(){
            alert('수정 되었습니다');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    delete: function(){
        var id = $('#id').val();

        $.ajax({
            type: 'delete',
            url: '/api/v1/posts/'+id,
            contentType: 'application/json; charset=utf-8',
        }).done(function(){
            alert("삭제되었습니다.");
            window.location.href='/';
        }).fail(function(error){
            alert("삭제를 실패하였습니다.");
        });

    }
};
main.init();