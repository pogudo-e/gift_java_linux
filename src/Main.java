import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Draw draw = new Draw();
        Presenter presenter = new Presenter(new MyView(), draw);

        presenter.onClick();
    }
}