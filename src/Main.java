import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry",
                "George", "Samuel", "John", "Oliver", "Jacob", "Thomas", "Martin");
        List<String> families = Arrays.asList("Evans", "Young", "Harris",
                "Wilson", "Davies", "Adamson", "Brown", "Smith", "Jones", "Moore");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i <= 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        // Найти количество несовершеннолетних (т.е. людей младше 18 лет)
        long under18Age = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.printf("Количество людей до 18 лет: %,d\n", under18Age);

        // Получить список фамилий призывников
        List<String> family = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() < 27)
                .map(Person::getFamily)
                .limit(20)
                .collect(Collectors.toList());
        System.out.printf("Фамилии призывников: %s\n", family);

        // Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием
        List<Person> manWorking = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() < 65)
                .collect(Collectors.toList());

        List<Person> womenWorking = persons.stream()
                .filter(person -> person.getSex() == Sex.WOMAN)
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() < 60)
                .collect(Collectors.toList());
;
        manWorking.addAll(womenWorking);
        System.out.printf("Количество потенциальных работников: %,d\n", manWorking.size());

        List<Person> workingMen = manWorking.stream()
                .sorted(Comparator.comparing(Person::getAge))
                .limit(50)
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.printf("Потенциальные работники: %s\n", workingMen);
    }
}