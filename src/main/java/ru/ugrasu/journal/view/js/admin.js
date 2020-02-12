$( document ).ready(function() {

	//Глобальные переменные
	//Массив пользователей после загрузки
	var usersArray = [];

	//Прелоадер
	var preloader = 
	'<div class="preloader-wrapper active">' + 
        '<div class="spinner-layer spinner-blue-only">' +
            '<div class="circle-clipper left">' +
                '<div class="circle"></div>' +
            '</div><div class="gap-patch">' +
                '<div class="circle"></div>' +
            '</div><div class="circle-clipper right">' +
                '<div class="circle"></div>' +
            '</div>' +
        '</div>' +
    '</div>';

    //СМЕНА СТРАНИЦЫ
    $('#account').click(function(){
        location.replace("teacher.html");
    });

    var toast = $('<span>Загрузка... </span>').add(preloader);

    //Включаем на несколько секунд всплывающее окно (загрузка пройдет быстрее, но просто для интерактивности) 
    Materialize.toast(toast, 1000, 'rounded');

    //////////////////////////////////////////////////////////////////////////////////////
    //Блок обновления																	//
    //////////////////////////////////////////////////////////////////////////////////////

	//Обновление пользователей
    $.ajax({
        url:'http://localhost:8080/admin/refrashUser',
        type:'GET',
        success: function(recieved){

        	usersArray = [];
        	usersArray = recieved;

            //Удаление дочерних (jQuery)
            $('#select-user1').empty();

            var out = '<option value=0>Создать нового</option>';

            recieved.forEach(function(item, i, arr) {
               out += '<option value=' + item.id + '>' + item.name + ' - ' +
               		item.roleByRole.name + '</option>';
            });

            $('#select-user1').append(out);
            
            //Обновление material_select
            $('select').material_select();
        },
        error: function(xhr,textStatus){
        	Materialize.toast('Обновление пользователей - ' + textStatus, 2000, 'rounded');
        }
    });

    //Обновление ролей
    $.ajax({
        url:'http://localhost:8080/admin/refrashRole',
        type:'GET',
        success: function(recieved){

        	var out = '';

            //Удаление дочерних (jQuery)
            $('#select-role').empty();

            recieved.forEach(function(item, i, arr) {
               out += '<option value=' + item.id + '>' + item.name + '</option>';
            });

            $('#select-role').append(out);
            
            //Обновление material_select
            $('select').material_select();
        },
        error: function(xhr,textStatus){
        	Materialize.toast('Обновление ролей - ' + textStatus, 2000, 'rounded');
        }
    });

    //Обновление кафедр
    $.ajax({
        url:'http://localhost:8080/admin/refrashDepartment',
        type:'GET',
        success: function(recieved){

        	var out = '';

            //Удаление дочерних (jQuery)
            $('#select-department1').empty();

            recieved.forEach(function(item, i, arr) {
               out += '<option value=' + item.id + '>' + item.name + '</option>';
            });

            $('#select-department1').append(out);
            
            //Обновление material_select
            $('select').material_select();
        },
        error: function(xhr,textStatus){
        	Materialize.toast('Обновление кафедр - ' + textStatus, 2000, 'rounded');
        }
    });

    //Обновление групп
    $.ajax({
        url:'http://localhost:8080/admin/refrashStudyGroup',
        type:'GET',
        success: function(recieved){

        	var out = '';

            //Удаление дочерних (jQuery)
            $('#select-group1').empty();

            recieved.forEach(function(item, i, arr) {
               out += '<option value=' + item.id + '>' + item.name + '</option>';
            });

            $('#select-group1').append(out);
            
            //Обновление material_select
            $('select').material_select();
        },
        error: function(xhr,textStatus){
        	Materialize.toast('Обновление групп - ' + textStatus, 2000, 'rounded');
        }
    });

    //////////////////////////////////////////////////////////////////////////////////////
    //Конец блока обновления															//
    //////////////////////////////////////////////////////////////////////////////////////

    $('#select-user1').change(function() {

    	//Во всех случаях кроме 0 обновляем параметры
    	if ($('#select-user1').val() != 0) {
    		var index;

    		//Индексы внутри селектора могут не совпадать
    		for (var i = 0; i < usersArray.length; i++) {
    			if (usersArray[i].id == ($('#select-user1').val())) {
    				index = i;
    			} 
    		}
    		
    		$('#login').val(usersArray[index].name);
    		$('#password').val(usersArray[index].password);
    		$('#select-role').val(usersArray[index].roleByRole.id);
    		
    		$('#div-group').addClass("hide");
    		$('#div-department').addClass("hide");

    		if (usersArray[index].roleByRole.id == 2) {
    			$('#select-department1').val(usersArray[index].departmentByDepartment.id);
    			$('#div-department').removeClass("hide");
    		} 

    		if ((usersArray[index].roleByRole.id == 3) || (usersArray[index].roleByRole.id == 4)) {
    			$('#select-group1').val(usersArray[index].studyGroupByStudyGroup.id);
    			$('#div-group').removeClass("hide");
    		}

    		//Обновление material_select
            $('select').material_select();
    	}
    });

    $('#select-role').change(function() {
    	if ($('#select-role').val() == 1) {
    		$('#div-department').addClass("hide");
    		$('#div-group').addClass("hide");
    	}

    	//Преподаватель
    	if ($('#select-role').val() == 2) {
    		$('#div-group').addClass("hide");
    		$('#div-department').removeClass("hide");
    	}

    	//Староста, студент
    	if (($('#select-role').val() == 3) || ($('#select-role').val() == 4)) {
    		$('#div-department').addClass("hide");
    		$('#div-group').removeClass("hide");
    	}
    });

	$('#addUser').click(function(){
		var setId = $('#select-user1').val();
		var setName = $('#login').val();
		var setPassword = $('#password').val();
		var setRoleId = $('#select-role').val();
		var setStudyGroupId = 0;
		var setDepartmentId = 0;

		//Преподаватель
    	if (setRoleId == 2) {
    		setDepartmentId = $('#select-department1').val();
    	}

    	//Староста, студент
		if ((setRoleId == 3) || (setRoleId == 4)) {
    		setStudyGroupId = $('#select-group1').val();
    	}

    	$('#select-user1').val(0);
    	$('#login').val('');
    	$('#password').val('');
    	$('#select-role').val('');
    	$('#select-group1').val(1);
    	$('#select-department1').val(1);
    	$('#div-department').addClass("hide");
    	$('#div-group').addClass("hide");

	    //Сохранение пользователя
	    $.ajax({
	        url:'http://localhost:8080/admin/saveUser',
	        type:'POST',
	        data: {
	        	id: setId,
	        	name: setName,
	        	password: setPassword,
	        	roleId: setRoleId,
	        	studyGroupId: setStudyGroupId,
	        	departmentId: setDepartmentId
	        },
	        success: function(){
	        	//Обновление пользователей
			    $.ajax({
			        url:'http://localhost:8080/admin/refrashUser',
			        type:'GET',
			        success: function(recieved){

			        	usersArray = [];
			        	usersArray = recieved;

			            //Удаление дочерних (jQuery)
			            $('#select-user1').empty();

			            var out = '<option value=0>Создать нового</option>';

			            recieved.forEach(function(item, i, arr) {
			               out += '<option value=' + item.id + '>' + item.name + ' - ' +
			               		item.roleByRole.name + '</option>';
			            });


			            $('#select-user1').append(out);
			            
			            //Обновление material_select
			            $('select').material_select();
			        },
			        error: function(xhr,textStatus){
			        	Materialize.toast('Обновление пользователей - ' + textStatus, 2000, 'rounded');
			        }
			    });
			    Materialize.toast('Сохранено!', 2000, 'rounded');
	        },
	        error: function(xhr,textStatus){
	        	Materialize.toast('Сохранение пользователя - ' + textStatus, 2000, 'rounded');
	        }
	    });
    });

    $('#deleteUser').click(function(){
    	var setId = $('#select-user1').val();
		
    	$('#select-user1').val(0);
    	$('#login').val('');
    	$('#password').val('');
    	$('#select-role').val('');
    	$('#select-group1').val(1);
    	$('#select-department1').val(1);
    	$('#div-department').addClass("hide");
    	$('#div-group').addClass("hide");

    	//Удаление пользователя
	    $.ajax({
	        url:'http://localhost:8080/admin/deleteUser/' + setId,
	        type:'GET',
	        success: function(){
	        	//Обновление пользователей
			    $.ajax({
			        url:'http://localhost:8080/admin/refrashUser',
			        type:'GET',
			        success: function(recieved){

			        	usersArray = [];
			        	usersArray = recieved;

			            //Удаление дочерних (jQuery)
			            $('#select-user1').empty();

			            var out = '<option value=0>Создать нового</option>';

			            recieved.forEach(function(item, i, arr) {
			               out += '<option value=' + item.id + '>' + item.name + ' - ' +
			               		item.roleByRole.name + '</option>';
			            });


			            $('#select-user1').append(out);
			            
			            //Обновление material_select
			            $('select').material_select();
			        },
			        error: function(xhr,textStatus){
			        	Materialize.toast('Обновление пользователей - ' + textStatus, 2000, 'rounded');
			        }
			    });
			    Materialize.toast('Удалено!', 2000, 'rounded');
	        },
	        error: function(xhr,textStatus){
	        	Materialize.toast('Удаление пользователя - ' + textStatus, 2000, 'rounded');
	        }
	    });
    });
})