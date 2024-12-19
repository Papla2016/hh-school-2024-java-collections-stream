package tasks;

import common.Person;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {
  /**
   * Задача 3
   * <p>Сортировка по фамилии -> имени -> дате создания</p>
   *
   * @param persons персоны для сортировки
   * @return отсортированные персоны в приоритете:Фамилия -> имя -> дата создания
   */
  public static List<Person> sort(Collection<Person> persons) {

    return persons.stream()
        .sorted(Comparator.comparing(Person::secondName)
            .thenComparing(Person::firstName)
            .thenComparing(Person::createdAt))
        .toList();
  }
}
