package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;


  public List<String> getNames(List<Person> persons) {
    return persons.stream()
        .skip(1)                    //возращает поток после отбрасывания 1 элемента. Если пустой - то пустой поток
        .map(Person::firstName)
        .collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); //используем getNames и ложим в HashSet уникальных имен
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    return Stream.of(person.firstName(), person.secondName(), person.middleName()) //сокращаем всю эту грамозткость до 1 StreamApi(кстати, в оригинале 2 раза secondName, ошибка???)
        .filter(Objects::nonNull)                                                //Изящно простой способ убрать null
        .collect(Collectors.joining(" "));                               //Склеиваем с разделителем " "
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream().collect(Collectors.toMap(
        Person::id,
        this::convertPersonToString,
        (existingValue, newValue) -> existingValue //в случае дубликата - выбирается старая версия(как в оригинале)
    ));//сократили в 1 StreamApi, разве это не прекрастно?)

  }

  // есть ли совпадающие в двух коллекциях персоны?
  /**
   * Была сложность O(n*m), где
   * n - размер persons1
   * m - размер persons2
   * Сложность статичная,т.к. прерывания двойного цикла нет
   * Стало - O(n+m),где
   * n - размер уникальных Person для person1,
   * m - размер persons2,
   * Сложность не статичная, в лучшем случае сложность O(n)(создать HashSet и сразу найти схожесть)
   */
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    Set<Person> personsSet1 = new HashSet<>(persons1);
    return persons2.stream().anyMatch(personsSet1::contains); //используем StreamAPI для нахождения совпадения
  }

  // Посчитать число четных чисел
  /**
   * Это лучше чем использование forEach,потому что:
   * 1. Код говорит сам за себя, count более читаемый(просто перевести слово), чем forEach
   * с лямбда-выражением(надо ещё прочитать лямбда-выражение)
   * 2. forEach это недетерминированная операция, а значит, в случае многопоточности, если будет использован count в
   * другом потоке, то результат не гарантирован.
   *
   * @param numbers
   * @return
   */
  public long countEven(Stream<Integer> numbers) {
    count = numbers.filter(num -> num % 2 == 0).count(); //используем StreamAPI для подсчёта
    return count;
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке

  /**
   * <p>HashSet реализован на основе HashMap.</p>
   * <p>В HashMap есть Node таблица, в которой храняться ключ-значение. Для HashSet все значения - статичный Object.</p>
   * <p>При установке позиции в таблице используется функция hash(h = key.hashCode()) ^ (h >>> 16)</p>
   * <p>Для чисел, меньше 65536 данная функция выдаёт сам hashCode</p>
   * <p>Для Integer.hashcode - это сам Integer</p>
   * <p>Соответственно, в таблице на 1 позиции - чилсо 1, на 2 - число 2 и т.д.</p>
   * <p>Ниже 2 строки из HashMap</p>
   * <p>if ((p = tab[i = (n - 1) & hash]) == null)</p>
   * <p>           tab[i] = newNode(hash, key, value, null);</p>
   * <p>n в нашем случае около 16000, hash - наша цифра(<10000).</p>
   * <p>Вместе с операцией & наш результат i всегда равен hash(то есть, нашей цифры), и в tab[i] встаёт наше число</p>
   * <p>При вызове HashSet.toString() он вызывается из абстрактного класса AbstractSet, который вызывается из класса AbstractCollection</p>
   * <p>Он идёт при помощи итератора с нуля таблицы(если нет коллизий, а их у нас нет).</p>
   * <p>Соответственно, 1, 2, 3...</p>
   */
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
  }
}
