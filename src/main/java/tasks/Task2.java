package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {
  /**
   * Задание 2
   * <p>Объединение персон, сортировка по дате создания и вывод limit штук</p>
   *
   * @param persons1 первая коллекция для объединения
   * @param persons2 вторая коллекция для объединения
   * @param limit    лимит на вывод
   * @return первые <code>limit</code> штук объединённых коллекций персон
   */
  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
    return Stream
        .concat(persons1.stream(), persons2.stream())
        .sorted(Comparator.comparing(Person::createdAt))
        .limit(limit)
        .collect(Collectors.toList());
  }
}
