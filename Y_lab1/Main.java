import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Регистрация");
            System.out.println("2. Авторизация");
            System.out.println("3. Подача показаний счетчика");
            if (userService.isUserLoggedIn()) {
                System.out.println("4. Просмотр актуальных показаний счетчиков");
                System.out.println("5. Просмотр показаний счетчиков за конкретный месяц");
                System.out.println("6. Просмотр истории показаний счетчиков");
                System.out.println("7. Выход");
            } else {
                System.out.println("4. Выход");
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите имя пользователя: ");
                    String regUsername = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String regPassword = scanner.nextLine();
                    userService.registerUser(regUsername, regPassword);
                    break;
                case 2:
                    System.out.print("Введите имя пользователя: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String loginPassword = scanner.nextLine();
                    if (userService.loginUser(loginUsername, loginPassword)) {
                        System.out.println("Авторизация успешна.");
                    } else {
                        System.out.println("Ошибка авторизации. Пользователь не найден или неверный пароль.");
                    }
                    break;
                case 3:
                    if (userService.isUserLoggedIn()) {
                        Map<String, Double> readings = new HashMap<>();
                        System.out.print("Введите месяц: ");
                        int month = scanner.nextInt();
                        System.out.print("Введите год: ");
                        int year = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Введите показания счетчика:");
                        for (String key : userService.getLatestMeterReadings().get(0).getReadings().keySet()) {
                            System.out.print(key + ": ");
                            double value = scanner.nextDouble();
                            readings.put(key, value);
                        }

                        userService.addMeterReading(month, year, readings);
                        System.out.println("Показания счетчика добавлены.");
                    } else {
                        System.out.println("Вы не авторизованы. Авторизуйтесь для подачи показаний.");
                    }
                    break;
                case 4:
                    if (userService.isUserLoggedIn()) {
                        List<MeterReading> latestReadings = userService.getLatestMeterReadings();
                        System.out.println("Актуальные показания счетчиков:");
                        for (MeterReading reading : latestReadings) {
                            System.out.println("Месяц: " + reading.getMonth() + ", Год: " + reading.getYear());
                            System.out.println("Показания: " + reading.getReadings());
                        }
                    } else {
                        System.out.println("Вы не авторизованы. Авторизуйтесь для просмотра актуальных показаний.");
                    }
                    break;
                case 5:
                    if (userService.isUserLoggedIn()) {
                        System.out.print("Введите месяц: ");
                        int viewMonth = scanner.nextInt();
                        System.out.print("Введите год: ");
                        int viewYear = scanner.nextInt();
                        scanner.nextLine();

                        List<MeterReading> readingsForMonth = userService.getMeterReadingsForMonth(viewMonth, viewYear);
                        if (readingsForMonth.isEmpty()) {
                            System.out.println("Нет показаний для указанного месяца и года.");
                        } else {
                            System.out.println("Показания счетчиков за " + viewMonth + "/" + viewYear + ":");
                            for (MeterReading reading : readingsForMonth) {
                                System.out.println("Месяц: " + reading.getMonth() + ", Год: " + reading.getYear());
                                System.out.println("Показания: " + reading.getReadings());
                            }
                        }
                    } else {
                        System.out.println("Вы не авторизованы. Авторизуйтесь для просмотра показаний за конкретный месяц.");
                    }
                    break;
                case 6:
                    if (userService.isUserLoggedIn()) {
                        List<MeterReading> history = userService.getMeterReadingHistory();
                        System.out.println("История показаний счетчиков:");
                        for (MeterReading reading : history) {
                            System.out.println("Месяц: " + reading.getMonth() + ", Год: " + reading.getYear());
                            System.out.println("Показания: " + reading.getReadings());
                        }
                    } else {
                        System.out.println("Вы не авторизованы. Авторизуйтесь для просмотра истории показаний.");
                    }
                    break;
                case 7:
                    System.out.println("Выход из программы.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
            }
        }
    }
}