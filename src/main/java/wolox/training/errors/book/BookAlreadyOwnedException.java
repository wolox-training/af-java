package wolox.training.errors.book;

public class BookAlreadyOwnedException extends Exception{

    public BookAlreadyOwnedException() {
        super("The book already exists");
    }
}
