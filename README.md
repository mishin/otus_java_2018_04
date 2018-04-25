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

#### ТЗ №1 Решение
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
 * cd L01.1-maven
 * mvn package
 * java -cp ./target/L01.1-maven.jar ru.otus.l011.Main
 или java -jar ./target/L01.1-maven.jar 
 
 
 To unzip the jar:
 * 7z x -oJAR ./target/L01.1-maven.jar
 * unzip -d JAR ./target/L01.1-maven.jar
 

 To build:
 * mvn package
 * mvn clean compile
 * mvn assembly:single
 * mvn clean compile assembly:single