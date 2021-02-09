var index = {
    init : function(){      //btn-save라는 css클래스를 가진 컴포넌트에 click리스너를 달아 놓는다.
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();       //클릭하면 save()호출
        });
        $('#btn-update').on('click', function(){
           _this.update();
        });
        $('#btn-delete').on('click', function(){
            _this.delete();
        });
    },
    save : function(){
        var data = {        //컴포넌트들의 값들을 동적으로 가져와 data라는 객체를 만든다.
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    },
    update: function(){
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+ id,
            dataType: 'JSON',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('수정되었습니다');
            window.location.href = '/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    delete: function(){
        var id = $('#id').val();

        $.ajax({
            type: 'delete',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function(){
            alert('삭제되었습니다');
            window.location.href='/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
};
index.init();