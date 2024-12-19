package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;

import java.util.List;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны - personConverter.convert()
FYI - DTO = Data Transfer Object - распространенный паттерн, можно погуглить
 */

public class Task4 {

  private final PersonConverter personConverter;

  public Task4(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  /**
   * Задача 4
   * Конвертация в ApiPersonDto
   *
   * @param persons персоны для конвертации
   * @return конвертированные персоны
   * @see ApiPersonDto
   */
  public List<ApiPersonDto> convert(List<Person> persons) {
    return persons.stream()
        .map(personConverter::convert)
        .toList();
  }
}
