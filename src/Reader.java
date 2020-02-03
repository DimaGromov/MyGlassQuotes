import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Reader {
    private static String file_name;
    private static FileReader fileReader;
    static Log log = new Log();
    public static void readFromFile() {
        Scanner console_scanner = new Scanner(System.in);
        System.out.print("Введите имя файла: ");
        file_name = console_scanner.next();
        try{
            fileReader = new FileReader(file_name);
            Scanner file_scanner = new Scanner(fileReader);
            while (file_scanner.hasNext()){
                String type = file_scanner.next();

                switch (type){
                    case "pr":
                        Double prise = file_scanner.nextDouble();
                        Integer count = file_scanner.nextInt();
                        AskTreeMap.addInMap("Dima", prise, count);
                        break;
                    case "po":
                        Double prise1 = file_scanner.nextDouble();
                        Integer count1 = file_scanner.nextInt();
                        BidTreeMap.addInMap("Dima", prise1, count1);
                        break;
                    default:
                        System.out.println("Ошибка! Неверный идентификатор сделки.");
                        log.warning("Ошибка! Неверный идентификатор сделки.");

                }
            }
            fileReader.close();
            System.out.println("Данные успешно считаны из файла.");
            log.info("Данные успешно считаны из файла.");
        } catch (FileNotFoundException ex){
            log.warning("Не удалось открыть файл!");
            System.out.println("Не удалось открыть файл!");
        } catch (IOException e){
            log.warning("Не удалось закрыть файл!");
            System.out.println("Не удалось закрыть файл!");
        }
    }
}
