package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Задание 1
   * <p>Асимптотика:</p>
   * <p>для потока  personMap - сложность O(m) где m - размер persons</p>
   * <p>для потока результата - сложность O(n) Где n - размер personIds</p>
   * <p>Итоговая сложность - O(m+n) - сумма</p>
   *
   * @param personIds Id пользователей
   * @return отсортированный лист пользователей
   */
  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    Map<Integer, Person> personMap = persons.stream()
        .collect(Collectors.toMap(Person::id, person -> person));
    return personIds.stream()
        .map(personMap::get)
        .collect(Collectors.toList());
  }
}
