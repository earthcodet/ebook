
package Dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.awt.print.Book;
import java.util.Iterator;
import org.bson.Document;
import org.bson.types.ObjectId;

public class BookDao {
    static MongoClient connectMlab = new MongoClient(new MongoClientURI("mongodb://admin:admin02@ds247223.mlab.com:47223/ebook"));
    static MongoDatabase db = connectMlab.getDatabase("ebook");
    static MongoCollection<Document> Collection;
    public Book getBookById(int eBookId){
        Book result ;
          try {
            Collection = db.getCollection("Book");
            Document search = new Document("eBookId", eBookId);
             FindIterable<Document> iterateDoc = Collection.find(search);
            Iterator<Document> iterator = iterateDoc.iterator();
            while (iterator.hasNext()) {
                Document data = new Document(iterator.next());
                String eBookName = data.getString("eBookName");
                String eBookKinds= data.getString("eBookKinds");
                String publisherName= data.getString("publisherName");
                String authorName= data.getString("authorName");
                String fileBook= data.getString("fileBook");
                int eBookPrice = data.getInteger("eBookPrice");
                int eBookCoverPrice= data.getInteger("eBookCoverPrice");
                int fileEbookSize = data.getInteger("fileEbookSize");
                int pages= data.getInteger("pages");
                ObjectId eBookCover_id= data.getObjectId("eBookCover_id");
                result = new Book(eBookId,eBookName,eBookKinds,publisherName,authorName,fileBook,eBookPrice,eBookCoverPrice,fileEbookSize,pages,eBookCover_id);
            }
            return result;
        } catch (Exception eX) {
            eX.printStackTrace();
        }
        return result;
    }
}
