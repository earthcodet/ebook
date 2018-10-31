package Data;


import java.awt.Image;
import org.bson.types.ObjectId;

public class Book {

    public int eBookId;
    public String eBookName;
    public String eBookKinds;
    public String publisherName;
    public String authorName;
    public String fileBook;
    public int eBookPrice;
    public int eBookCoverPrice;
    public int fileBookSize;
    public int pages;
    public ObjectId eBookCover_id;
    public Book(int pages){
        this.pages = pages;
    }
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
        this.fileBookSize = fileEbookSize;
        this.pages = pages;
        this.eBookKinds = eBookKinds;
        this.eBookCover_id = eBookCover_id;
    }
    
     public Book(int eBookId,String eBookName,String eBookKinds,String publisherName,
            String authorName,String fileBook,int eBookPrice,int eBookCoverPrice,
            int fileEbookSize,int pages) {
        this.eBookId = eBookId;
        this.eBookName = eBookName;
        this.publisherName = publisherName;
        this.authorName = authorName;
        this.fileBook = fileBook;
        this.eBookPrice = eBookPrice;
        this.eBookCoverPrice = eBookCoverPrice;
        this.fileBookSize = fileEbookSize;
        this.pages = pages;
        this.eBookKinds = eBookKinds;
    }
}
