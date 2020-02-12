package ru.ugrasu.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JournalApplication {

	public static void main(String[] args) {

		//TODO Главный список недоработок:
		//1. Расписать функционал системы
		//2. Закончить структуру БД

		//3. Написать вьюшку SQL для удобства обработки

		//4. Обработка Null-value
		//5. Обработка исключений, можно возвращать
				//ResponseEntity (вернет так же указанный статус HTTP параметром)

		//6. Тестирование!

		//7. Frontend нарисовать)
		//8. При редактировании пользователя логичнее сделать выпадающий список (Front)

		SpringApplication.run(JournalApplication.class, args);
	}
}
