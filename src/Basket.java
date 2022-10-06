import java.io.*;
import java.util.*;

public class Basket {
    private String[] productNames;
    private int[] productPrices;
    private int[] productAmount;

    public Basket(String[] productNames, int[] productPrices) {
        this.productNames = productNames;
        this.productPrices = productPrices;
        this.productAmount = new int[productNames.length];
    }

    public void addToCart(int productNum, int amount) {
        productAmount[productNum] += amount;
        System.out.println("В корзину добавлено: " + productNames[productNum] + " в количестве " + amount + " уп.");
    }

    public void printCart() {
        System.out.println("\nВ Вашей корзине сейчас:\n");
        System.out.println("№№ - \tТовар - \tКол-во - \tЦена\\уп. - \tИтого");
        System.out.println("--------------------------------------------");
        int pointNumber = 1;
        int total = 0;
        for (int i = 0; i < productNames.length; i++) {
            if (productAmount[i] != 0) {
                System.out.println(pointNumber + " - \t" + productNames[i] + " - \t" + productAmount[i] + " уп. - \t" +
                        +productPrices[i] + " руб. - \t" + (productAmount[i] * productPrices[i]) + " руб.");
                pointNumber++;
                total += productAmount[i] * productPrices[i];
            } else {
                continue;
            }
        }
        System.out.println("--------------------------------------------");
        System.out.println("Общая сумма: " + total + " руб.");
    }

    public void saveTxt(File textFile) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (String productName : productNames) {
                writer.print(productName + " ");
            }
            writer.println();
            for (int productPrice : productPrices) {
                writer.print(productPrice + " ");
            }
            writer.println();
            for (int amount : productAmount) {
                writer.print(amount + " ");
            }
        }
    }

    static Basket loadFromTxtFile(File textFile) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileReader(textFile))) {
            String[] savedNames = scanner.nextLine().split(" ");
            String[] stringPrices = scanner.nextLine().split(" ");
            String[] stringAmount = scanner.nextLine().split(" ");
            int[] savedPrices = Arrays.stream(stringPrices)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int[] savedAmount = Arrays.stream(stringAmount)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            Basket basket = new Basket(savedNames, savedPrices);
            basket.productAmount = savedAmount;
            return basket;
        }
    }
}