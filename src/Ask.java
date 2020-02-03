public class Ask {
    // Поля
    private String user_name;           // Имя пользователя.
    private Double prise;               // Цена продажи.
    private Integer count;              // Объем продажи.
    private Integer id;                 // ID предложения продажи.
    private static Integer counter = 1; // Счётчик создаваемых объектов.

    // Конструктор класса
    protected Ask(String user_name, Double prise, Integer count){
        this.user_name = user_name;
        this.prise = prise;
        this.count = count;
        this.id = counter++;
    }

    public Integer getCount() {
        return count;
    }

    public Double getPrise() {
        return prise;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "\t\t" + prise + "\t" + count;
    }
}
