import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final MoneyAcceptor moneyAcceptor;

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        moneyAcceptor = new MoneyAcceptor(100);
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Монет на сумму: " + moneyAcceptor.getAmount());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (moneyAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        Scanner sc = new Scanner(System.in);
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            print("Как вы хотите пополнить баланс? 1)Наличкой / 2) Картой");
            String answer =  sc.nextLine();
            while (answer.isEmpty() || answer.isEmpty()){
                print("Как вы хотите пополнить баланс? 1)Картой/ 2) Наличкой");
                answer =  sc.nextLine();
            }
            if (answer.equalsIgnoreCase("1")){
                moneyAcceptor.setAmount(moneyAcceptor.getAmount() + 10);
                print("Вы пополнили баланс на 10");
                return;
            } else if (answer.equalsIgnoreCase("2")) {
                print("Пожалуйста введите номер карточки");
                while (true){
                    String cardNum = sc.nextLine();
                    if (!(cardNum.isEmpty()) && !(cardNum.isBlank()) && (cardNum.length() == 16) ){
                        break;
                    }
                    else {
                        print("Пожалуйста введите корректный номер карты!");
                    }
                }

                print("Введите 4-х значный пароль от карты");
                while (true){
                    String cardPas = sc.nextLine();
                    if (!(cardPas.isEmpty()) && !(cardPas.isBlank()) && (cardPas.length() ==4) ){
                        break;
                    }
                    else {
                        print("Пожалуйста введите корректный номер карты!");
                    }
                }


                print("На какую сумму вы хотите пополнить?");
                print("1) 100");
                print("2) 50");
                print("3) 10");
                String ans = sc.nextLine();
                while (ans.isEmpty() || ans.isBlank()){
                    print("На какую сумму вы хотите пополнить?");
                    print("1) 100");
                    print("2) 50");
                    print("3) 10");
                    ans = sc.nextLine();
                }

                while (true){
                    if (ans.equalsIgnoreCase("1")){
                        moneyAcceptor.setAmount(moneyAcceptor.getAmount() + 100);
                        print("Вы пополнили баланс на 100");
                        return;
                    } else if (ans.equalsIgnoreCase("2")) {
                        moneyAcceptor.setAmount(moneyAcceptor.getAmount() + 50);
                        print("Вы пополнили баланс на 50");
                        return;
                    } else if (ans.equalsIgnoreCase("3")) {
                        moneyAcceptor.setAmount(moneyAcceptor.getAmount() + 10);
                        print("Вы пополнили баланс на 10");
                        return;
                    }
                    else {
                        print("Пожалуйста выбирайте лишь ворзможные варианты ответа! ");
                        print("На какую сумму вы хотите пополнить?");
                        print("1) 100");
                        print("2) 50");
                        print("3) 10");
                        ans = sc.nextLine();
                    }
                }
            }
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    moneyAcceptor.setAmount(moneyAcceptor.getAmount() - products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попрбуйте еще раз.");
                chooseAction(products);
            }
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
