import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Draw implements Model, Iterable, Iterator<Present> {
    int i = 0;
    private final List<Present> presentList;
    private final List<Present> giftList;
    private String[] data;
    private String inputData;

    public Draw() {
        this.presentList = new ArrayList<Present>();
        this.giftList = new ArrayList<Present>();
        toList(readFile());
    }

    public void addPresent(int id, String name, int dropFrequency) {
        int quantity = 1;
        for (Present p : presentList) {
            if (id == p.getId()) {
                p.setQuantity(quantity + 1);
                return;
            }
        }
        this.presentList.add(new Present(id, name, quantity, dropFrequency));

    }


    /*
    Конструктор добавления выпавшего подарка
     */
    public void addGift(int id, String name, int dropFrequency) {
        int quantity = 1;
        for (int i = 0; i < presentList.size(); i++) {
            Present p = presentList.get(i);
            if (id == p.getId() && p.getQuantity() > 1) {
                p.setQuantity(p.getQuantity() - 1);
            } else if (id == p.getId() && p.getQuantity() <= 1) {
                presentList.remove(i);
            }
        }

        quantity = 1;
        for (Present g : giftList) {
            if (id == g.getId()) {
                g.setQuantity(quantity + 1);
                return;
            }
        }
        this.giftList.add(new Present(id, name, quantity, dropFrequency));
    }


    /*
    Изменение вероятности
     */
    public boolean setDropPers() {
        try {
            if (parseInt(data[2]) > 5){
                return false;
            }
            for (Present p : presentList) {
                if (parseInt(data[1]) == p.getId()) {
                    p.setDropFrequency(parseInt(data[2]));
                    return true;
                }
            }

        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
        return false;
    }

    /*
    Чтение из файла
     */
    public ArrayList readFile() {
        Scanner scanner = null;
        try {
            String path = "gift.txt";

            File file = new File(path);

            scanner = new Scanner(file);
            ArrayList<String> list = new ArrayList<String>();

            while (scanner.hasNextLine()) {
                list.add(Arrays.toString(scanner.nextLine().split("\n")));

            }
            return list;

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        } finally {
            scanner.close();
        }


        return null;
    }

    /*
    Парсер массива из файла
     */
    public void toList(ArrayList<String> list) {
        for (String s : list) {
            String[] words = s.split(" | ");
            int id = parseInt(words[0].replace("[", ""));
            int dropFrequency = parseInt(words[2]);
            String name = words[4].replace("]", "");
            addPresent(id, name, dropFrequency);
        }

    }


    /*
    Сопостовление вероятности и найденых указателей в сущности present
     */
    public void rozigrysh() {
        boolean plus = true;
        int id = 0;
        String name = null;
        int frequency = 0;
        while (plus) {
            int ver = randVer();
            for (Present p : presentList) {
                if (ver == p.getDropFrequency()) {
                    id = p.getId();
                    name = p.getName();
                    frequency = p.getDropFrequency();
                    plus = false;
                }
            }
        }
        addGift(id, name, frequency);
    }


    /*
    Получение рандомной вероятности в int от 1 до 5
     */
    private int randVer() {
        int ver = 0;
        for (int i = 0; i < 2; i++) {
            double random = Math.floor(Math.random() * 100) + 1;
            if (random < 36) {
                ver = 1;
            } else if (random < 61) {
                ver = 2;
            } else if (random < 81) {
                ver = 3;
            } else if (random < 96) {
                ver = 4;
            } else if (random < 98) {
                ver = 5;
            }
        }
        return ver;
    }


    /*
    Запись в файл по id
     */
    public boolean toFileGift() {
        int delId = -1;
        for (Present g : giftList) {
            try {
                if (parseInt(data[1]) == g.getId()) {
                    try (FileWriter writer = new FileWriter("by.txt", true)) {
                        String text = g.getId() + " | " + g.getDropFrequency() + " | " + g.getName() + "\n";
                        writer.write(text);
                        writer.flush();
                        delId = parseInt(data[1]);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }
        return delGift(delId);

    }

    public boolean delGift(int id) {
        if (id != -1) {
            for (int i = 0; i < giftList.size(); i++) {
                Present g = giftList.get(i);
                if (id == g.getId() && g.getQuantity() > 1) {
                    g.setQuantity(g.getQuantity() - 1);
                } else if (id == g.getId() && g.getQuantity() <= 1) {
                    giftList.remove(i);
                }
            }
        } else {
            System.out.println("Нет подарка с таким идентификатором!");
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return presentList.toString();
    }

    private Boolean Command() {
        Boolean runProgram = true;

        switch (data[0]) {
            case ("1"):
                printList((ArrayList<Present>) presentList);
                break;
            case ("2"):
                if (setDropPers()) {
                    System.out.println("Успешно обновлено");
                } else {
                    System.out.println("Ошибка ввода! Формат ввода: 1 [id] [DropFrequency 1-5]");
                }
                break;
            case ("3"):
                rozigrysh();
                System.out.println("Розыгрыш успешно произведен!");
                break;
            case ("4"):
                printList((ArrayList<Present>) giftList);
                break;
            case ("5"):
                if (toFileGift()) {
                    System.out.println("Записал в файл");
                } else {
                    System.out.println("Ошибка ввода! Формат ввода: 5 [id]");
                }
                break;
            case ("0"):
                runProgram = false;
                break;
        }
        return runProgram;
    }

    private void printList(ArrayList<Present> l) {
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i).getId() + ": "
                    + l.get(i).getName() + ", Колличество "
                    + l.get(i).getQuantity() + ", Вероятность выпадения "
                    + l.get(i).getDropFrequency());
        }
    }

    @Override
    public void setText(String s) {
        inputData = s;
    }

    private String[] getDataFromText() {
        data = inputData.split("\\s+");
        return data;
    }

    @Override
    public Boolean execute() {
        getDataFromText();
        return Command();
    }

    @Override
    public Iterator iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return i < presentList.size();
    }

    @Override
    public Present next() {
        return presentList.get(i++);
    }
}
