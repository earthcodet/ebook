
import java.awt.Image;
import org.bson.types.ObjectId;

public class Book {

    int eBookId;
    String eBookName;
    String eBookKinds;
    String publisherName;
    String authorName;
    String fileBook;
    int eBookPrice;
    int eBookCoverPrice;
    int fileEbookSize;
    int pages;
    ObjectId eBookCover_id;

    public Book(int eBookId,String eBookName,String eBookKinds,String publisherName,
            String authorName,String fileBook,int eBookPrice,int eBookCoverPrice,
            int fileEbookSize,int pages, ObjectId eBookCover_id) {
        this.eBookId = eBookId;
        this.eBookName = eBookName;
        this.publisherName = publisherName;
        this.authorName = authorName;
        this.fileBook = fileBook;
        this.eBookPrice = eBookPrice;
        this.eBookCoverPrice = eBookCoverPrice;
        this.fileEbookSize = fileEbookSize;
        this.pages = pages;
        this.eBookCover_id = eBookCover_id;
    }
}
