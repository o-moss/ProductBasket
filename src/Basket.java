import java.io.*;

public class Basket implements Serializable {
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

    public void saveBin(File file) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(this);
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Basket basket = (Basket) in.readObject();
            return basket;
        }
    }
}