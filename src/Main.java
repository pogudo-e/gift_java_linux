import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Draw draw = new Draw();
        Presenter presenter = new Presenter(new MyView(), draw);

        presenter.onClick();
    }
}