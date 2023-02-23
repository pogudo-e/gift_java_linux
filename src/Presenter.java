import java.io.FileNotFoundException;

public class Presenter {
    private View view;
    private Model model;

    public Presenter(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public void onClick() throws FileNotFoundException {
        Boolean b = true;
        int i = 0;
        while (b) {
            if (i < 1) {
                System.out.println("Список команд:\n1: Посмотреть подарки для розыгрыша" +
                        "\n2: Изменить вероятность выпадения для подарка" +
                        "\n3: Провести розыгрыш" +
                        "\n4: Посмотреть выигранные подарки" +
                        "\n5: Вывести подарок" +
                        "\n0: Выход");
                i++;
            } else {
                System.out.println("\n____________\n1: Посмотреть подарки для розыгрыша" +
                        "\n2: Изменить вероятность выпадения для подарка" +
                        "\n3: Провести розыгрыш" +
                        "\n4: Посмотреть выигранные подарки" +
                        "\n5: Вывести подарок" +
                        "\n0: Выход");
            }
            model.setText(view.getText());
            b = model.execute();
        }


    }
}
