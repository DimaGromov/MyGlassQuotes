import java.util.*;

public class AskTreeMap {
    Log log = new Log();
    Scanner scanner = new Scanner(System.in);
    // Поля
    private static int size;
    private static TreeMap<Double, LinkedList<Ask>> map;
    private TreeMap<Double, LinkedList<Bid>> bid_map;

    // Конструктор
    public AskTreeMap() {
        map = new TreeMap<>(Collections.<Double>reverseOrder());
        size = 0;
    }

    public void setBid_map() {
        bid_map = BidTreeMap.getBidMap();
    }

    // Добавление новго элемента
    public static void addInMap(String name, Double prise, Integer count) {
        if ((askMapIsEmpty()) || (!map.containsKey(prise))) {
            LinkedList<Ask> temp_list = new LinkedList<>();
            temp_list.add(new Ask(name, prise, count));
            map.put(prise, temp_list);
            size++;
        } else {
            map.get(prise).add(new Ask(name, prise, count));
        }
    }

    // Проверка пуста ли map
    public static boolean askMapIsEmpty() {
        return map.isEmpty();
    }

    // Создание новой заявки на продажу
    public void createAskDeal() {
        log.info("Создание заявки на продажу");
        System.out.println("Создание заявки на продажу");
        //System.out.print("Введите имя: ");
        //String name = scanner.next();
        String name = "Dima";
        Double prise = 0.0;
        Integer count = 0;
        try {
            System.out.print("Введите цену: ");
            prise = (Double) scanner.nextDouble();
            if (prise < 0) {
                System.out.println("Ошибка ввода! Цена должна быть больше 0.");
                log.warning("Ошибка ввода! Цена должна быть больше 0.");
                return;
            }

            System.out.print("Введите количество: ");
            count = (Integer) scanner.nextInt();
            if (count < 0) {
                System.out.println("Ошибка ввода! Количество должно быть больше 0.");
                log.warning("Ошибка ввода! Количество должно быть больше 0.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода! " + e.toString());
            log.warning("Ошибка ввода! " + e.toString());
            return;
        }


        while (true) {
            if (bid_map.isEmpty() || prise > bid_map.firstKey().doubleValue()) {
                addInMap(name, prise, count);
                return;
            } else {
                LinkedList<Bid> current_list = bid_map.get(bid_map.firstKey());
                Bid current_bid = current_list.getFirst();

                if (current_bid.getCount().intValue() > count) {
                    current_bid.setCount(current_bid.getCount().intValue() - count);
                    System.out.println("Сделка состоялась: продано " + count + " акц. по цене " + current_bid.getPrise());
                    log.info("  Сделка состоялась: продано " + count + " акц. по цене " + current_bid.getPrise());
                    return;
                } else {
                    count -= current_bid.getCount().intValue();
                    System.out.println("Сделка состоялась: продано " + current_bid.getCount() + " акц. по цене " + current_bid.getPrise());
                    log.info("  Сделка состоялась: продано " + current_bid.getCount() + " акц. по цене " + current_bid.getPrise());
                    log.info("Удаление первого Bid в bidLinkedList с ценой " + prise);
                    current_list.removeFirst();
                    if (current_list.isEmpty()) {
                        bid_map.remove(bid_map.firstKey());
                        log.info("Удаление пустого bidLinkedList c ценой " + prise);
                    }
                }
            }
        }


        /*while (true){
            if(bitMapIsEmpty() || (prise > getFirstKeyBidMap())){
                addInMap(name, prise, count);
                return;
            } else {
                Bid tempBid = getFirstBid();

                if(tempBid.getCount() > count){
                    tempBid.setCount(tempBid.getCount() - count);
                    return;
                } else {
                    count -= tempBid.getCount();
                    removeFirstBid();
                    if(firstBidListIsEmpty()){
                        removeFirstBidList();
                    }
                }
            }
        }*/
    }

    // Создание сделки на продажу, для получения наибольшей выгоды банком
    public void createAskDealForBank() {
        log.info("Создание заявки на продажу");
        System.out.println("Создание заявки на продажу");
        String name = "Dima";
        Double prise = 0.0;
        Integer count = 0;

        try {
            System.out.print("Введите цену: ");
            prise = scanner.nextDouble();
            if (prise < 0) {
                System.out.println("Ошибка ввода! Цена должна быть больше 0.");
                log.warning("Ошибка ввода! Цена должна быть больше 0.");
                return;
            }

            System.out.print("Введите количество: ");
            count = scanner.nextInt();
            if (count < 0) {
                System.out.println("Ошибка ввода! Количество должно быть больше 0.");
                log.warning("Ошибка ввода! Количество должно быть больше 0.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода! " + e.toString());
            log.warning("Ошибка ввода! " + e.toString());
            return;
        }

        while (true) {
            if (bid_map.isEmpty() || prise > bid_map.firstKey().doubleValue()) {
                addInMap(name, prise, count);
                return;
            } else {
                LinkedList<Bid> current_list = bid_map.get(bid_map.firstKey());
                Bid current_bid = current_list.getFirst();

                if (current_bid.getCount() > count) {
                    MyGlassQuotes.revenue += (count * (current_bid.getPrise() - prise));
                    current_bid.setCount(current_bid.getCount() - count);
                    System.out.println("Сделка состоялась: продано " + count + " акц. по цене " + prise);
                    log.info("Сделка состоялась: продано " + count + " акц. по цене " + prise);
                    return;
                } else {
                    MyGlassQuotes.revenue += (current_bid.getCount() * (current_bid.getPrise() - prise));
                    count -= current_bid.getCount();
                    System.out.println("Сделка состоялась: продано " + current_bid.getCount() + " акц. по цене " + current_bid.getPrise());
                    log.info("Сделка состоялась: продано " + current_bid.getCount() + " акц. по цене " + current_bid.getPrise());
                    log.info("Удаление первого Bid в bidLinkedList с ценой " + current_bid.getPrise());
                    current_list.removeFirst();

                    if (current_list.isEmpty()) {
                        log.info("Удаление пустого bidLinkedList c ценой " + bid_map.firstKey());
                        bid_map.remove(bid_map.firstKey());
                    }
                }
            }
        }
    }

    // getTreeMap
    public static TreeMap<Double, LinkedList<Ask>> getTreeMap() {
        return map;
    }

    @Override
    public String toString() {
        String str = "";
        if (!map.isEmpty()) {
            Set<Double> set = map.keySet();
            for (Double key : set) {
                LinkedList<Ask> list = map.get(key);
                for (Ask ask : list) {
                    str += ask.toString() + "\t";
                }
                if(key!= map.lastKey()){
                    str += "\n";
                }

            }
        }
        return str;
    }

    /* // Получение ключа последнего элемента AskMap
    public static Double getLastKey(){
        return map.lastKey();
    }

    // Получение массива ключей AskMap
    public static Set<Double> getKeyArr(){
        return map.keySet();
    }

    // Получение Ask в текущем листе
    public static Ask getAsk(Double prise){
        return map.get(prise).getFirst();
    }

    // Удаление первого элемента в текущем списке
    public static void removeFirstAsk(Double prise){
        map.get(prise).removeFirst();
    }

    // Проверка пуст ли текущий лист
    public static boolean currentListIsEmpty(Double prise){
        return map.get(prise).isEmpty();
    }

    // Удаление текущеко листа из AskMap
    public static void removeCurrentList(Double prise){
        map.remove(prise);
    }

    // Получение List по ключу
    public static LinkedList<Ask> getCurrentList(Double prise){
        return map.get(prise);
    }*/
    /*  //------------------------------------------------------------//
    //------------------Работа с BitTreeMap-----------------------//

    // Проверка путса ли BitMap
    private boolean bitMapIsEmpty(){
        return BidTreeMap.bidMapIsEmpty();
    }

    // Получение цены первого элемента
    private Double getFirstKeyBidMap(){
        return BidTreeMap.getFirstKey();
    }

    // Получение первого Bid в верхнем листе
    private Bid getFirstBid(){
        return BidTreeMap.getFirstBid();
    }

    // Удаление первого Bid из верхнего листа
    private void removeFirstBid(){
        BidTreeMap.removeFirstBid();
    }

    // Удаление верхнего листа из BidMap
    private void removeFirstBidList(){
        BidTreeMap.removeFirstList();
    }

    // Проверка пуст ли верхний лист
    private boolean firstBidListIsEmpty(){
        return BidTreeMap.firstListIsEmpty();
    }

    //------------------------------------------------------------//
    //------------------------------------------------------------//*/
}
