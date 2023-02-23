import java.io.FileNotFoundException;

public interface Model {
    public void setText(String s);
    Boolean execute() throws FileNotFoundException;
}
