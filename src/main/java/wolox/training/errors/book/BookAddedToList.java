package wolox.training.errors.book;

public class BookAddedToList extends Exception{

    public BookAddedToList() {
        super("The book was successfully added");
    }
}
