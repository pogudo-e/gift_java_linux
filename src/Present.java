public class Present {
    private int id;
    private String name;
    private int quantity;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int dropFrequency;

    public Present(int id, String name, int quantity, int dropFrequency) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.dropFrequency = dropFrequency;
    }

    public void setDropFrequency(int dropFrequency) {
        this.dropFrequency = dropFrequency;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public int getDropFrequency() {
        return dropFrequency;
    }

    @Override
    public String toString() {
        return  "id: " + id +
                ", name: " + name +
                ", quantity: " + quantity +
                ", dropFrequency: " + dropFrequency +
                "\n";
    }


}
