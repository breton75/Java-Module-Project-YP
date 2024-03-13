import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        calculator.start();
    }
}

    class Calculator {
        static final String INCORRECT_INPUT_MSG = "Нверный ввод. Попробуйте еще раз.";

        Scanner scanner = new Scanner(System.in);
        int personCount;

      public void start() {

        System.out.println("Добро пожаловать в калькулятор!\n");

        personCount = 0;

        while(personCount < 2) {

            System.out.println("Укажите на скольких человек необходимо разделить счёт:");

            try {

                personCount = scanner.nextInt();

                if (personCount < 2)
                    System.out.println(INCORRECT_INPUT_MSG);

            } catch (InputMismatchException e) {

                System.out.println(INCORRECT_INPUT_MSG);
            }
        }

        System.out.println("Теперь давайте сформируем список товаров. Для завершения ввода наберите 'Завершить'");

        CommodArray commodArray = new CommodArray();

        while(true) {

            System.out.println("Введите имя товара");

            String name = scanner.next();
            float price;

            if(name.equalsIgnoreCase("завершить") || name.equals("e"))
                break;


            try {
                System.out.println(" ... и его стоимость в формате [рубли.копейки]");
                price = Float.parseFloat(scanner.next());

                if (price <= 0.0f) {

                    System.out.println(INCORRECT_INPUT_MSG);
                    continue;
                }

                // добавляем товар
                commodArray.add(new Commodity(name, price));

            } catch (NumberFormatException e) {

                System.out.println(INCORRECT_INPUT_MSG);
            }
        }

        commodArray.print();

        System.out.printf("Общая стоимость товаров: %.2f %s\nС каждого человека по %.2f %s\n",
                commodArray.sum,
                Formatter.format(commodArray.sum),
                (float)(commodArray.sum / personCount),
                Formatter.format(commodArray.sum / personCount));

        System.out.println("Программа завершена!");
    }
}

class CommodArray {

    ArrayList<Commodity> commods = new ArrayList<>();
    float sum = 0.0f;

    public void add(Commodity commod)
    {
        commods.add(commod);
        sum += commod.price;
    }

    public void print()
    {
        System.out.println("Добавленные товары:");

        Iterator<Commodity> it = commods.iterator();
        while(it.hasNext()) {
            Commodity c = it.next();
            System.out.printf("%s %.2f %s\n", c.name, c.price, Formatter.format(c.price));
        }
    }
}

class Commodity {
    String name;
    float price;

    public Commodity(String name, float price)
    {
        this.name = name;
        this.price = price;
    }
}

class Formatter {
    static String format(float price)
    {
        String rubFormat = "рублей";

        int tmp = Math.round((float) Math.floor(price)) % 100;

        if((tmp < 10) || (tmp > 20))
        {
            switch (Math.round((float) Math.floor(price)) % 10) {
                case 1:
                    rubFormat = "рубль";
                    break;

                case 2:
                case 3:
                case 4:
                    rubFormat = "рубля";
                    break;
            }
        }

        return rubFormat;
    }
}
