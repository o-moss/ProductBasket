import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        File textFile = new File("D://Apps//NewProductBasket", "basket.txt");
        String[] products = {"Хлеб", "Яйца", "Молоко", "Творог", "Сахар", "Мука", "Томаты", "Яблоки"};
        int[] prices = {70, 90, 80, 150, 80, 120, 300, 230};
        System.out.println("№ \tТовар \t Цена\n-------------------------");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". \t" + products[i] + " - \t" + prices[i] + " руб.");
        }
        System.out.println("-------------------------");
        Basket basicBasket = new Basket(products, prices);
        if (textFile.exists()) {
            basicBasket = basicBasket.loadFromTxtFile(textFile);
            System.out.println("Напоминаем, что в Вашей корзине уже лежат некоторые товары.");
            basicBasket.printCart();
        } else {
            try {
                textFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            System.out.println("Для добавления товара в корзину введите его номер и необходимое количество. " +
                    "\nДля завершения покупок введите \"end\".");
            String input = scanner.nextLine();
            String[] inputParts = input.split(" ");
            if ("end".equals(input)) {
                break;
            } else if (inputParts.length != 2) {
                System.out.println("Некорректный ввод! \n");
                continue;
            }
            int productNum = Integer.parseInt(inputParts[0]) - 1;
            int amount = Integer.parseInt(inputParts[1]);
            basicBasket.addToCart(productNum, amount);
            basicBasket.saveTxt(textFile);
        }
        System.out.println("Вы завершили покупки.");
        basicBasket.printCart();
    }
}

