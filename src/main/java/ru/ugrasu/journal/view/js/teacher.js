//Для хранения дат всех занятий
var arrayDate = [];

//Для хранения id студентов по данному занятию
var arrayStudentsId = [];

//Для хранения id занятий
var arrayExcerciseId = [];

function cellClick(obj) {
    
    //Меняем + на Н
    if ($("#" + obj.id).text() == "+") {
        $("#" + obj.id).text("Н");
    } else {
        $("#" + obj.id).text("+");
    }
    
    //Меняем цвета
    $("#" + obj.id).toggleClass("red");
    $("#" + obj.id).toggleClass("light-green");

    //Вырезаем из obj.id формата u_e индексы для user(u) и excercise(e)
    var uId = (obj.id).substring(0,(obj.id).indexOf("_")) - 1;
    var eId = (obj.id).substr((obj.id).indexOf("_") + 1);

    //Получаем значения из глобальных массивов. User и Excercise по id
    var userId = arrayStudentsId[uId];
    var excerciseId = arrayExcerciseId[eId];

    //Отправляем значение на сервер
    $.ajax({
        url:'http://localhost:8080/teacher/saveUserExcercise',
        type:'POST',
        data: {
            userId: userId,
            excerciseId: excerciseId
        },
        success: function() {
        },
        error: function(xhr,textStatus){
            Materialize.toast('UserExcercise - ' + textStatus, 2000, 'rounded');
        }
    });
};

$( document ).ready(function() {

    //СМЕНА СТРАНИЦЫ
    $('#account').click(function(){
        location.replace("admin.html");
    });

    $('.datepicker').pickadate({
        selectMonth: true,
        selectYears: 5,
        today: 'Сегодня',
        clear: 'Очистить',
        close: 'OK',
        closeOnSelect: false,
        format: 'yyyy-mm-dd'
    });

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

    var toast = $('<span>Загрузка... </span>').add(preloader);

    //Включаем на несколько секунд всплывающее окно (загрузка пройдет быстрее, но просто для интерактивности) 
    Materialize.toast(toast, 1000, 'rounded');

    //////////////////////////////////////////////////////////////////////////////////////
    //Блок обновления                                                                   //
    //////////////////////////////////////////////////////////////////////////////////////

    //Обновление предметов
    $.ajax({
        url:'http://localhost:8080/teacher/refrashSubject',
        type:'GET',
        success: function(recieved){

            //Удаление дочерних (jQuery)
            $('#select-subject').empty();

            var out = '<optgroup label="Предметы">';

            recieved.forEach(function(item, i, arr) {
               out += '<option value=' + item.id + '>' + item.name + '</option>';
            });

            out += '</optgroup>';

            $('#select-subject').append(out);
            
            //Обновление material_select
            $('select').material_select();
        },
        error: function(xhr,textStatus){
            Materialize.toast('Обновление предметов - ' + textStatus, 2000, 'rounded');
        }
    });

    //Обновление групп
    $.ajax({
        url:'http://localhost:8080/teacher/refrashStudyGroup',
        type:'GET',
        success: function(recieved){

            //Удаление дочерних (jQuery)
            $('#select-group').empty();

            var out = '<optgroup label="Группы">';

            recieved.forEach(function(item, i, arr) {
               out += '<option value=' + item.id + '>' + item.name + '</option>';
            });

            out += '</optgroup>';

            $('#select-group').append(out);
            
            //Обновление material_select
            $('select').material_select();
        },
        error: function(xhr,textStatus){
            Materialize.toast('Обновление групп - ' + textStatus, 2000, 'rounded');   
        }
    });

    //////////////////////////////////////////////////////////////////////////////////////
    //Конец блока обновления                                                            //
    //////////////////////////////////////////////////////////////////////////////////////

    function getStudents(group) {
        $.ajax({
            url:'http://localhost:8080/teacher/getStudentsByGroup/' + group,
            type:'GET',
            success: function(recieved) {

                var out = '';
                var rowNum = 1;
                arrayStudentsId = [];

                recieved.forEach(function(item, i, arr) {
                    arrayStudentsId.push(item.id);

                    out += '<tr>' + 
                                '<td>' + rowNum + '</td>' +
                                '<td>' + item.name + '</td>';

                                var arrayIndex = 0;

                                //Если у студента есть ссылки на занятия
                                if (item.excercisesById.length != 0) {

                                    item.excercisesById.forEach(function(item1, i1, arr1) {
                                        
                                        //Если дата занятия в массиве дат по занятию
                                        //и текущая дата у студента не совпадают - не был на занятии
                                        while (arrayDate[arrayIndex] != item1.date) {
                                            out += '<td id=' 
                                                + rowNum + '_' + arrayIndex + 
                                                ' onclick="cellClick(this)" class="red lighten-2">Н</td>';
                                            arrayIndex++;
                                        }

                                        //TODO
                                        /*
                                            ОШИБКА, если перевести студента в другую группу
                                            Или занятия отмечаются, как в прошлой группе
                                            Или, если дат нету, страница зависает!!!
                                        */

                                        //Как вариант, при переводе - удалять все занятия студента

                                        //Если совпадают дата - студент был на занятии
                                        out += '<td id=' 
                                            + rowNum + '_' + arrayIndex + 
                                            ' onclick="cellClick(this)" class="light-green lighten-2">+</td>';
                                        arrayIndex++;
                                    });
                                } else {
                                    //Если нету - для всех занятий Н
                                    for (var i = 0; i < arrayDate.length; i++) {
                                        out += '<td id='
                                            + rowNum + '_' + arrayIndex + 
                                            ' onclick="cellClick(this)" class="red lighten-2">Н</td>';
                                        arrayIndex++;
                                    }
                                }

                                //Если еще остались занятия - доставляем Н
                                while (arrayIndex < arrayDate.length) {
                                    out += '<td id=' 
                                            + rowNum + '_' + arrayIndex + 
                                            ' onclick="cellClick(this)" class="red lighten-2">Н</td>';
                                    arrayIndex++;
                                }

                    out += '</tr>';

                    rowNum++;
                });

                $('#table-body').append(out);

            },
            error: function(xhr,textStatus){
                Materialize.toast('Refrash - ' + textStatus, 2000, 'rounded');
            }
        })
    };

    $('#refrash').click(function (){

        //Для того, чтобы получить текст
        //var group = $('#select-group option:selected').text();

        //При приеме был получен id в value
        var subject = $('#select-subject').val();
        var group = $('#select-group').val();

        $.ajax({
            url:'http://localhost:8080/teacher/getExcercises/' + subject + '/' + group,
            type:'GET',
            success: function(recieved) {

                Materialize.toast(toast, 1000, 'rounded');

                $('#table-head').empty();
                $('#table-body').empty();
                arrayDate = [];
                arrayExcerciseId = [];

                var out = '';

                if (recieved.length == 0) {
                    out += '<tr><th>Похоже, у данной группы не ведется этот предмет =)</th></tr>';
                }
                else {
                    out += '<tr><th>№</th><th>Студент</th>';

                    recieved.forEach(function(item, i, arr) {
                        out += '<th>' + item.date + '</th>';
                        arrayDate.push(item.date);
                        arrayExcerciseId.push(item.id);
                    })

                    out += '</tr>';

                    //Добавляем студентов через другой запрос
                    getStudents(group);
                };

                $('#table-head').append(out);
            },
            error: function(xhr,textStatus){
                Materialize.toast('Refrash - ' + textStatus, 2000, 'rounded');
            }
        });
    })

    $('#add').click(function() {
        //Дата, группа и предмет
        //Передаем для сохранения занятия
        var d = $('#datepicker').val();
        var g = $('#select-group').val();
        var s = $('#select-subject').val();

        $.ajax({
            url:'http://localhost:8080/teacher/saveExcercise',
            type:'POST',
            data: {
                date: d,
                groupId: g,
                subjectId: s
            },
            success: function(recieved) {
                //Обновляем данные
                $('#refrash').click();
            },
            error: function(xhr,textStatus){
                Materialize.toast('Add date - ' + textStatus, 2000, 'rounded');
            }
        });
    });
});