public class Bid{
        // Поля
        private String user_name;           // Имя пользователя.
        private Double prise;               // Цена покупки.
        private Integer count;              // Объем покупки.
        private Integer id;                 // ID предложения покупки.
        private static Integer counter = 1; // Счётчик создаваемых объектов.

        // Конструктор класса.
        public Bid(String user_name, Double prise, Integer count){
            this.user_name = user_name;
            this.prise = prise;
            this.count = count;
            this.id = counter++;
        }

    public Double getPrise() {
        return prise;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return  "\t\t" + prise + "\t" + count;
    }
}
