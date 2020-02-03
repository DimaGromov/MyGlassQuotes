import java.io.IOException;
import java.util.Scanner;
import java.util.logging.*;


public class MyGlassQuotes {
    static AskTreeMap askTreeMap;
    static BidTreeMap bidTreeMap;
    static Double revenue = 0.0; // выручка, полученная банком за сессию
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        Log log = new Log();


        askTreeMap = new AskTreeMap();
        bidTreeMap = new BidTreeMap();
        askTreeMap.setBid_map();

        printHelloMenu();
        String choise = scanner.next();
        while (true){
            switch (choise){
                case "1":
                    boolean val = standart_config();
                    if(val == false) {
                        return;
                    }
                    break;
                case "2":
                    boolean val1 = bank_config();
                    if(val1 == false) {
                        return;
                    }
                    break;
                case "0":
                    return;
            }
        }




         /*logger = Logger.getLogger("My Log");
        FileHandler fh;

        try{
            fh = new FileHandler("MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setUseParentHandlers(false);
            logger.info("My first log");
        } catch (SecurityException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static void printGlass(){
       System.out.println("--------------------------");
       System.out.println("\tСТАКАН КОТИРОВОК");
       System.out.println("--------------------------");
       System.out.println("AskMap\tЦена\tКоличество\n" + askTreeMap);
       System.out.println("BidMap\tЦена\tКоличество\n" + bidTreeMap);
       System.out.println("--------------------------");
    }
    private static void printMenu(){
        System.out.println("Команды:");
        System.out.println("1 - создание заявки на продажу");
        System.out.println("2 - создание заявки на покупку");
        System.out.println("3 - напечатать стакан");
        System.out.println("4 - напечатать меню помощи");
        System.out.println("5 - считать данные из файла");
        System.out.println("0 - выход из программы");

    }
    private static void printBankMenu(){
        System.out.println("Команды:");
        System.out.println("1 - создание заявки на продажу");
        System.out.println("2 - создание заявки на покупку");
        System.out.println("3 - напечатать стакан");
        System.out.println("4 - напечатать меню помощи");
        System.out.println("5 - считать данные из файла");
        System.out.println("6 - показать прибыль банка на текущий момент");
        System.out.println("0 - выход из программы");
    }
    private static void printHelloMenu(){
        System.out.println("\tНачало сеанса.");
        System.out.println("\tДоступны две конфигурации стакана котировок:");
        System.out.println("\t1. Без учёта прибыли банка (предложение продажи с наивысшей подходящей ценой имеет приоритет).");
        System.out.println("\t2. C учётом прибыли банка.");
        System.out.print("\tВыберите конфигурацию для работы:");
    }

    private static boolean standart_config(){
        printMenu();
        while (true) {
            System.out.print("Введите команду: ");
            String str = scanner.next();
            switch (str) {
                case "1":
                    askTreeMap.createAskDeal();
                    printGlass();
                    break;
                case "2":
                    bidTreeMap.createBidDeal();
                    printGlass();
                    break;
                case "3":
                    printGlass();
                    break;
                case "4":
                    printMenu();
                    break;
                case "5":
                    Reader.readFromFile();
                    break;
                case "0":
                    return false;
                default:
                    System.out.println("Ошибка ввода! Неопознанная команда.");
            }
        }
    }
    private static boolean bank_config(){
        printBankMenu();

        while (true) {
            System.out.print("Введите команду: ");
            String str = scanner.next();
            switch (str) {
                case "1":
                    askTreeMap.createAskDealForBank();
                    printGlass();
                    break;
                case "2":
                    bidTreeMap.createBidDealForBank();
                    printGlass();
                    break;
                case "3":
                    printGlass();
                    break;
                case "4":
                    printBankMenu();
                    break;
                case "5":
                    Reader.readFromFile();
                    break;
                case "6":
                    System.out.println("Банк заработал: " + revenue);
                    break;
                case "0":
                    return false;
                default:
                    System.out.println("Ошибка ввода! Неопознанная команда.");
            }
        }
    }
}
