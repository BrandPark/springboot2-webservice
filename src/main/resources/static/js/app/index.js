var index = {
    init : function(){      //btn-save라는 css클래스를 가진 컴포넌트에 click리스너를 달아 놓는다.
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();       //클릭하면 save()호출
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
    }
};
index.init();