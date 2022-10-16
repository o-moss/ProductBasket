import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        boolean loadEnabled = true;
        boolean saveEnabled = true;
        boolean logEnabled = true;
        String fileNameForLoad = new String();
        String loadFormat = new String();
        String saveFileName = new String();
        String saveFormat = new String();
        String logFileName = new String();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builderDoc = factory.newDocumentBuilder();
        Document doc = builderDoc.parse(new File("shop.xml"));

        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName() == "load") {
                NodeList nodeListLoad = node.getChildNodes();
                for (int j = 0; j < nodeListLoad.getLength(); j++) {
                    Node nodeOfLoad = nodeListLoad.item(j);
                    if (nodeOfLoad.getNodeName() == "enabled") {
                        NodeList nodeListEnabledLoad = nodeOfLoad.getChildNodes();
                        for (int ii = 0; ii < nodeListEnabledLoad.getLength(); ii++) {
                            Node nodeOfEnabledLoad = nodeListEnabledLoad.item(ii);
                            if (nodeOfEnabledLoad.getNodeValue().equals("true")) {
                                loadEnabled = true;
                            } else {
                                loadEnabled = false;
                            }
                        }
                    } else if (nodeOfLoad.getNodeName() == "fileName") {
                        NodeList nodeListFileNameLoad = nodeOfLoad.getChildNodes();
                        for (int jj = 0; jj < nodeListFileNameLoad.getLength(); jj++) {
                            Node nodeOfFileNameLoad = nodeListFileNameLoad.item(jj);
                            fileNameForLoad = nodeOfFileNameLoad.getNodeValue();
                        }
                    } else if (nodeOfLoad.getNodeName() == "format") {
                        NodeList nodeListFormatLoad = nodeOfLoad.getChildNodes();
                        for (int kk = 0; kk < nodeListFormatLoad.getLength(); kk++) {
                            Node nodeOfFormatLoad = nodeListFormatLoad.item(kk);
                            loadFormat = nodeOfFormatLoad.getNodeValue();
                        }
                    }
                }
            } else if (node.getNodeName() == "save") {
                NodeList nodeListSave = node.getChildNodes();
                for (int j = 0; j < nodeListSave.getLength(); j++) {
                    Node nodeOfSave = nodeListSave.item(j);
                    if (nodeOfSave.getNodeName() == "enabled") {
                        NodeList nodeListEnabledSave = nodeOfSave.getChildNodes();
                        for (int ii = 0; ii < nodeListEnabledSave.getLength(); ii++) {
                            Node nodeOfEnabledSave = nodeListEnabledSave.item(ii);
                            if (nodeOfEnabledSave.getNodeValue().equals("true")) {
                                saveEnabled = true;
                            } else {
                                saveEnabled = false;
                            }
                        }
                    } else if (nodeOfSave.getNodeName() == "fileName") {
                        NodeList nodeListFileNameSave = nodeOfSave.getChildNodes();
                        for (int jj = 0; jj < nodeListFileNameSave.getLength(); jj++) {
                            Node nodeOfFileNameSave = nodeListFileNameSave.item(jj);
                            saveFileName = nodeOfFileNameSave.getNodeValue();
                        }
                    } else if (nodeOfSave.getNodeName() == "format") {
                        NodeList nodeListFormatSave = nodeOfSave.getChildNodes();
                        for (int kk = 0; kk < nodeListFormatSave.getLength(); kk++) {
                            Node nodeOfFormatSave = nodeListFormatSave.item(kk);
                            saveFormat = nodeOfFormatSave.getNodeValue();
                        }
                    }
                }
            } else if (node.getNodeName() == "log") {
                NodeList nodeListLog = node.getChildNodes();
                for (int j = 0; j < nodeListLog.getLength(); j++) {
                    Node nodeOfLog = nodeListLog.item(j);
                    if (nodeOfLog.getNodeName() == "enabled") {
                        NodeList nodeListEnabledLog = nodeOfLog.getChildNodes();
                        for (int ii = 0; ii < nodeListEnabledLog.getLength(); ii++) {
                            Node nodeOfEnabledLog = nodeListEnabledLog.item(ii);
                            if (nodeOfEnabledLog.getNodeValue().equals("true")) {
                                logEnabled = true;
                            } else {
                                logEnabled = false;
                            }
                        }
                    } else if (nodeOfLog.getNodeName() == "fileName") {
                        NodeList nodeListFileNameLog = nodeOfLog.getChildNodes();
                        for (int jj = 0; jj < nodeListFileNameLog.getLength(); jj++) {
                            Node nodeOfFileNameLog = nodeListFileNameLog.item(jj);
                            logFileName = nodeOfFileNameLog.getNodeValue();
                        }
                    }
                }
            }
        }
        File jsonFile = new File("D://Apps//NewProductBasket", saveFileName);
        File csvFile = new File("D://Apps//NewProductBasket", logFileName);
        Scanner scanner = new Scanner(System.in);
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
        if (loadEnabled == true) {
            if (loadFormat.equals("json")) {
                if (jsonFile.exists()) {
                    JSONParser parser = new JSONParser();
                    try {
                        Object obj = parser.parse(new FileReader(fileNameForLoad));
                        JSONObject jsonObject = (JSONObject) obj;
                        basicBasket = gson.fromJson(String.valueOf(jsonObject), Basket.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
        } else if (loadFormat.equals("text")) {
            basicBasket.loadFromTxtFile(jsonFile);
            System.out.println("Напоминаем, что в Вашей корзине уже лежат некоторые товары.");
            basicBasket.printCart();
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
            if (saveEnabled == true) {
                if (saveFormat.equals("json")) {
                    try (FileWriter file = new FileWriter(saveFileName)) {
                        file.write(gson.toJson(basicBasket));
                        file.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (saveFormat.equals("text")) {
                    basicBasket.saveTxt(jsonFile);
                }
            }
        }
        System.out.println("Вы завершили покупки.");
        basicBasket.printCart();
        if (logEnabled == true) {
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
}

