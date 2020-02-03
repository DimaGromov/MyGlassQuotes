import java.util.*;

public class BidTreeMap {
    Log log = new Log();
    Scanner scanner = new Scanner(System.in);

    private static int size;
    private static TreeMap<Double, LinkedList<Bid>> map;
    private TreeMap<Double, LinkedList<Ask>> ask_map;

    // Конструктор
    public BidTreeMap() {
        map = new TreeMap<>(Collections.<Double>reverseOrder());
        size = 0;
        ask_map = AskTreeMap.getTreeMap();
    }

    // Проверка пуста ли map
    public static boolean bidMapIsEmpty() {
        return map.isEmpty();
    }

    // Добавление нового элемента в map
    public static void addInMap(String name, Double prise, Integer count) {
        if ((bidMapIsEmpty()) || (!map.containsKey(prise))) {
            LinkedList<Bid> temp_list = new LinkedList<>();
            temp_list.add(new Bid(name, prise, count));
            map.put(prise, temp_list);
            size++;
        } else {
            map.get(prise).add(new Bid(name, prise, count));
        }
    }


    public static TreeMap<Double, LinkedList<Bid>> getBidMap() {
        return map;
    }

    @Override
    public String toString() {
        String str = "";
        if (!map.isEmpty()) {
            Set<Double> set = map.keySet();
            for (Double key : set) {
                LinkedList<Bid> list = map.get(key);
                for (Bid bid : list) {
                    str += bid.toString() + "\t";
                }
                if(key != map.lastKey()){
                    str += "\n";
                }

            }
        }
        return str;
    }

    // Создание новой сделки на покупку
    public void createBidDeal() {
        log.info("Создание заявки на покупку");
        System.out.println("Создание заявки на покупку");
        //System.out.print("Введите имя: ");
        //String name = scanner.next();
        String name = "Dima";
        Double prise = 0.0;
        Integer count = 0;
        try{
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


        // Если ask_map пуста или цена нижнего элемента больше цены в заявке на покупку, просто создаем в map новое
        // поле Bid.
        if (ask_map.isEmpty() || ask_map.lastKey() > prise) {
            addInMap(name, prise, count);
        } else {
            // В противном случае в ask_map необходимо найти заявку на продажу, цена которой меньше или равна цене в
            // текущей заявке на покупку.
            // Для нахождения такой цены, необходимо пройтись по всем ценам в ask_map. Для удобства получим set ключей
            // ask_map, к качестве которого используется множество prise.
            Set<Double> prise_set = ask_map.keySet();
            Double[] arr = new Double[prise_set.size()];
            int j = 0;
            for (Double key : prise_set) {
                arr[j] = key;
                j++;
            }
            // Пройдёмся по полученному множеству с помощью цикла foreach.
            for (Double key : arr) {
                if (key <= prise) {

                    // В том случае, если мы нашли цену Ask, которая меньше или равна нашей prise, можно осуществлять
                    // продажу. Для этого необходимо получить из AskMap лист в котором храняться все предложения
                    // продажи, цена которых равна key.
                    LinkedList<Ask> current_list = ask_map.get(key);

                    Ask current_ask; // ссылка на текущее предложение по продаже
                    int size = current_list.size();
                    for (int i = 0; i < size; i++) {
                        current_ask = current_list.getFirst();

                        // Возможны два случая, в первом количество акций на продажу  в current_ask больше или равно
                        // count, и нам необходимо осуществить сделку и уменьшить количество акций в current_ask. Во
                        // Втором случае, количесво акций на продажу в current_ask меньше count, и нам необходимо
                        // уменьшить count и удалить current_ask.
                        if (current_ask.getCount() >= count) {
                            current_ask.setCount(current_ask.getCount().intValue() - count);
                            System.out.println("Сделка состоялась: купленно " + count + " акц. по цене " + prise);
                            log.info("  Сделка состоялась: купленно " + count + " акц. по цене " + key);
                            return;
                        } else {
                            count -= current_ask.getCount().intValue();
                            System.out.println("Сделка состоялась: купленно " + current_ask.getCount() + " акц. по цене " + prise);
                            log.info("  Сделка состоялась: купленно " + current_ask.getCount() + " акц. по цене " + prise);
                            log.info("Удаление первого Ask в askLinkedList с ценой " + key);
                            current_list.removeFirst();
                        }
                    }
                    // Если current_list пустой, удаляем его из AskTreeMap
                    if (current_list.isEmpty()) {
                        log.info("Удаление пустого askLinkedList c ценой " + key);
                        ask_map.remove(key);

                    }
                }
            }

            // Если не произошел выход из метода, значит мы у нас остались акции, которые необходимо купить.
            // Создаем предложение на покупку.
            if (count > 0) {
                addInMap(name, prise, count);
            }
        }
        /*if (askMapIsEmpty() || getLastKeyAskMap() > prise) {
            addInMap(name, prise, count);
        } else {
            LinkedList<Double> temp_list = getAskKeyList();
            while (temp_list.getFirst()>prise){
                temp_list.removeFirst();
            }

            while (true){
                Ask currentAsk = getCurrentAsk(temp_list.getFirst());

                if(currentAsk.getCount() > count){
                    currentAsk.setCount(currentAsk.getCount()-count);
                    return;
                } else {
                    count -=currentAsk.getCount();
                    removeFirstAsk(temp_list.getFirst());

                    if(currentAskListIsEmpty(temp_list.getFirst())){
                        removeCurrentAskList(temp_list.getFirst());
                        if(askMapIsEmpty()){
                            addInMap(name, prise, count);
                            return;
                        } else {
                            temp_list.removeFirst();
                        }
                    }

                }
            }

        }*/
        /*if((askMapIsEmpty()) || (getLastKeyAskMap().doubleValue()>prise)){
            addInMap(name, prise, count);
        } else {
            Double[] askMapKeyArr = getAskKeyArr(); // Массив для хранения ключей AskMap
            int startBay = findStartBayIndex(askMapKeyArr, prise); // Индекс элемента в массиве, с которого начнется продажа

            for (int i=0; i<askMapKeyArr.length; i++){
                // Получение текущего листа
                LinkedList<Ask> currentList = getCurrentAskList(askMapKeyArr[i]);
                // Получение первого элемента в текущем листе


                one:
                {
                    Ask currentAsk = currentList.getFirst();

                    if (currentAsk.getCount() > count) {
                        currentAsk.setCount(currentAsk.getCount() - count);
                        return;
                    } else {
                        count -= currentAsk.getCount();
                        currentList.removeFirst();

                        if (currentList.isEmpty()) {
                            removeCurrentAskList(askMapKeyArr[i]);
                        } else {
                            break one;
                        }

                    }
                }
            }

            if(count > 0){
                addInMap(name, prise, count);
            }

        }*/
    }

    // Создание новой сделки на покупку, для получения наибольшей выгоды банком
    public void createBidDealForBank(){
        log.info("Создание заявки на покупку");
        System.out.println("Создание заявки на покупку");
        //System.out.print("Введите имя: ");
        //String name = scanner.next();
        String name = "Dima";
        Double prise = 0.0;
        Integer count = 0;
        try{
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
            if (ask_map.isEmpty() || prise < ask_map.lastKey()) {
                addInMap(name, prise, count);
                return;
            } else {
                LinkedList<Ask> current_list = ask_map.get(ask_map.lastKey());
                Ask current_ask = current_list.getFirst();

                if(current_ask.getCount() > count){
                    MyGlassQuotes.revenue += (count * (prise - current_ask.getPrise()));
                    current_ask.setCount(current_ask.getCount() - count);
                    System.out.println("Сделка состоялась: купленно " + count + " акц. по цене " + prise);
                    log.info("Сделка состоялась: купленно " + count + " акц. по цене " + prise);
                    return;
                } else {
                    MyGlassQuotes.revenue += (current_ask.getCount() * (prise - current_ask.getPrise()));
                    count -= current_ask.getCount();
                    System.out.println("Cделка состоялась: купленно " + current_ask.getCount() + " акц. по цене " + prise);
                    log.info("Cделка состоялась: купленно " + current_ask.getCount() + " акц. по цене " + prise);
                    log.info("Удаление первого Ask в askLinkedList с ценой " + current_ask.getPrise());
                    current_list.removeFirst();

                    if(current_list.isEmpty()){
                        log.info("Удаление пустого bidLinkedList c ценой " + ask_map.firstKey());
                        ask_map.remove(ask_map.lastKey());

                    }

                }
            }
        }
    }

    /* // Получение первого Bid
    public static Bid getFirstBid() {
        return map.get(map.firstKey()).getFirst();
    }

    // Получение ключа первого элемента
    public static Double getFirstKey() {
        return map.firstKey();
    }

    // Удаление первого Bid в верхнем листе
    public static void removeFirstBid() {
        map.get(getFirstKey()).removeFirst();
    }

    // Удаление верхнего листа
    public static void removeFirstList() {
        map.remove(getFirstKey());
    }

    // Проверка пуст ли верхний лист
    public static boolean firstListIsEmpty() {
        return map.get(getFirstKey()).isEmpty();
    }*/
    /* //--------------------------------------------------------------------//
    //-------------------------Работа с AskMap----------------------------//

           // Получение AskTreeMap
            private TreeMap<Double, LinkedList<Ask>> getAskTreeMap(){
                return AskTreeMap.getTreeMap();
            }

            // Пуста ли AskMap
            private boolean askMapIsEmpty() {
                return AskTreeMap.askMapIsEmpty();
            }

            // Получение цены последнего элемента
            private Double getLastKeyAskMap() {
                return AskTreeMap.getLastKey();
            }

            // Получить массив ключей AskMap
            private Double[] getAskKeyArr(){
                return AskTreeMap.getKeyArr();
            }

            // Получение первого элемента в списке с ключом, указанным в параметре
            private Ask getCurrentAsk(Double prise){
                return AskTreeMap.getAsk(prise);
            }

            // Удаление первого Ask в текущем листе, указанном в качестве параметра
            private void removeFirstAsk(Double prise){
                AskTreeMap.removeFirstAsk(prise);
            }

            // Проверка пуст ли текущий лист
            private boolean currentAskListIsEmpty(Double prise){
                return AskTreeMap.currentListIsEmpty(prise);
            }

            // Удаление текущего листа из AskMap
            private void removeCurrentAskList(Double prise){
                AskTreeMap.removeFirstAsk(prise);
            }

            // Поиск первого с конца индекса элемента, который больше prise
            private int findStartBayIndex(Double[] arr, Double prise){
                int i = 0;
                while (arr[i]>prise){
                    i++;
                }
                return i;
            }

            // Получение LinkedList<Ask> по индексу
            private LinkedList<Ask> getCurrentAskList(Double prise){
                return AskTreeMap.getCurrentList(prise);
            }
    //--------------------------------------------------------------------//
    //--------------------------------------------------------------------//*/

}
