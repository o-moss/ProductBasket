import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        File jsonFile = new File("D://Apps//NewProductBasket", "basket.json");
        File csvFile = new File("D://Apps//NewProductBasket", "log.csv");
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String[] products = {"Хлеб", "Яйца", "Молоко", "Творог", "Сахар", "Мука", "Томаты", "Яблоки"};
        int[] prices = {70, 90, 80, 150, 80, 120, 300, 230};
        System.out.println("№ \tТовар \t Цена\n-------------------------");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". \t" + products[i] + " - \t" + prices[i] + " руб.");
        }
        System.out.println("-------------------------");
        Basket basicBasket = new Basket(products, prices);
        ClientLog clientLog = new ClientLog();
        if (jsonFile.exists()) {
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader("basket.json"));
                JSONObject jsonObject = (JSONObject) obj;
                basicBasket = gson.fromJson(String.valueOf(jsonObject), Basket.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Напоминаем, что в Вашей корзине уже лежат некоторые товары.");
            basicBasket.printCart();
        } else {
            try {
                jsonFile.createNewFile();
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
            clientLog.log(productNum, amount);
            try (FileWriter file = new FileWriter("basket.json")) {
                file.write(gson.toJson(basicBasket));
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Вы завершили покупки.");
        basicBasket.printCart();
        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clientLog.exportAsCSV(csvFile);
    }
}

