/*
Program sprawdza poprawność wpisywanego imienia. W przypadku wystąpienia spacji w imieniu, funkcja wyrzuca zdefiniowany wyjątek WrongStudentName, który jest wyłapywany w pętli głównej Commit6_0.
Poniższe zadania będą się sprowadzały do modyfikacji bazowego kodu. Proces modyfikacji ogólnie może wyglądać następująco:
• Ustalenie jaki błąd chcę się sprawdzić i wyłapać.
• Decyzja, czy użyje się własnej klasy wyjątku, czy wykorzysta już istniejące (np. Exception, IOException).
• Napisanie kodu sprawdzającego daną funkcjonalność. W przypadku warunku błędu wyrzucany będzie wyjątek: throw new WrongStudentName().
• W definicji funkcji, która zawiera kod wyrzucania wyjątku dopisuje się daną nazwę wyjątku, np. public static String ReadName() throws WrongStudentName.
• We wszystkich funkcjach, które wywołują powyższą funkcję także należy dopisać, że one wyrzucają ten wyjątek – inaczej program się nie skompiluje.
• W pętli głównej, w main’ie, w zdefiniowanym już try-catch dopisuje się Nazwę wyjątku i go obsługuje, np. wypisuje w konsoli co się stało.
*/

//Commit6_1. Na podstawie analogii do wyjątku WrongStudentName utwórz i obsłuż wyjątki WrongAge oraz WrongDateOfBirth. 
//Niepoprawny wiek – gdy jest mniejszy od 0 lub większy niż 100. Niepoprawna data urodzenia – gdy nie jest zapisana w formacie DD-MM-YYYY, np. 28-02-2023.

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WrongStudentName extends Exception {
}

class WrongAge extends Exception {
}

class WrongDateOfBirth extends Exception {
}

class WrongSwitchCase extends Exception {
}

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {
                int ex = menu();
                switch (ex) {
                    case 1:
                        exercise1();
                        break;
                    case 2:
                        exercise2();
                        break;
                    case 3:
                        exercise3();
                        break;
                    default:
                        return;
                }
            } catch (IOException e) {

            } catch (WrongStudentName e) {
                System.out.println("Błędne imie studenta!");
            } catch (WrongDateOfBirth e) {
                System.out.println("Błedna data urodzenia");
            } catch (WrongAge e) {
                System.out.println("Błędny wiek");
            } catch (WrongSwitchCase e) {
                System.out.println("Błędna opcja");
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono znak - oczekiwano cyfry");
                scan.nextLine();
            }
        }
    }

    public static int menu() throws WrongSwitchCase, InputMismatchException {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");
        int option = scan.nextInt();
        if (option < 0 || option > 3) {
            throw new WrongSwitchCase();
        }
        return option;
    }

    public static String ReadName() throws WrongStudentName {
        scan.nextLine();
        System.out.println("Podaj imie: ");
        String name = scan.nextLine();
        if (name.contains(" "))
            throw new WrongStudentName();

        return name;
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        var name = ReadName();
        System.out.println("Podaj wiek: ");
        var age = scan.nextInt();
        Pattern pattern = Pattern.compile("^[0-9]$|^[1-9][0-9]$|^(100)$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(String.valueOf(age));
        if (!matcher.find()) {
            throw new WrongAge();
        }
        scan.nextLine();
        System.out.println("Podaj datę urodzenia DD-MM-YYY");
        var date = scan.nextLine();
        pattern = Pattern.compile("^(?:0[1-9]|[12]\\d|3[01])([-])(?:0[1-9]|1[012])\\1(?:19|20)\\d\\d$",
                Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(date);
        if (!matcher.find()) {
            throw new WrongDateOfBirth();
        }
        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for (Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine();
        System.out.println("Podaj imie: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if (wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}