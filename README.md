# Курс "Разработчик Java" в OTUS
Группа 2018-04


#### Преподаватели
* Vitaly Chibrikov (Виталий Чибриков)
chibrikov@otus.ru
https://github.com/vitaly-chibrikov

* Vladimir Sonkin (Владимир Сонькин)
vladson@ya.ru

---

### Задания

#### ДЗ №1 Решение - использование maven(плагины, обфустракция)
 * добавлена зависимость - пакет для генерации коллекций объектов с информацией с клиентами как семпл для работы с коллекциями
 * добавлена зависимость - пакет guava для обработки имен клиентов
 * произведен расчет операций получения уникального списка сгенерированных имен
 * произведен расчет операций reverse на списке имен клиенов
 * добавлены аргументы 
 * добавил в .ignore Package Files 
 
** 1 false/true - отображать список сгенерированых клиентов
** 2 Int  - количество объектов клиентов для генерации
** 3 Int  - количество итерация измерения время исполнения
 
 
 ---

 To start the application:
 ```sh
 * cd L01.1-maven
 * mvn package
 * java -cp ./target/L01.1-maven.jar ru.otus.l011.Main
  ```
 или java -jar ./target/L01.1-maven.jar 
 
 
 To unzip the jar:
```sh
 7z x -oJAR ./target/L01.1-maven.jar
 unzip -d JAR ./target/L01.1-maven.jar
```

 To build:
 ```sh
 mvn package
 mvn clean compile
 mvn assembly:single
 mvn clean compile assembly:single
 ```
 
 #### №2 Решение ДЗ 02.1 Измерение памяти
  * используется Java's reflection для обхода полей (в том числе и приватных)
  * в вычисления добавлены знания про разрядность JVM и размер ссылки
  * использовать java 8 (в java 9 ограничение на доступ к приватными методам)
 
  ---
   To run the tests
```sh
   cd L02.1-tutors 
   mvn compile test   
```
 
 ---
  #### №3 Решение ДЗ 03 MyArrayList
  
  ДЗ 03. MyArrayList
  Написать свою реализацию ArrayList на основе массива. 
  Проверить, что на ней работают методы java.util.Collections

 ---
  #### №6 Решение ДЗ 06 Написать эмулятор АТМ
    
  * ДЗ 06. Написать эмулятор АТМ
  Написать эмулятор АТМ (банкомата).
 
 
     To run the tests
  ```sh
     cd L11-T6-ATM  
     mvn compile test   
  ```
  
 ---
  #### №9 Решение ДЗ 06 Написать myORM
  * ДЗ-09: myORM
  
  Описание:
  Создайте в базе таблицу с полями:
  id bigint(20) NOT NULL auto_increment
  name varchar(255)
  age int(3)
  
  
  Создайте абстрактный класс DataSet. Поместите long id в DataSet.
  Добавьте класс UserDataSet (с полями, которые соответствуют таблице) унаследуйте его от DataSet.
  
  Напишите Executor, который сохраняет наследников DataSet в базу и читает их из базы по id и классу.
  
  <T extends="" DataSet=""> void save(T user){…}
  <T extends="" DataSet=""> T load(long id, Class<T> clazz){…}
  
  
       To run the tests
    ```sh
       cd L11-T6-ATM  
       mvn compile test   
    ```