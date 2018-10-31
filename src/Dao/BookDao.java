
package Dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import java.awt.print.Book;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;

public class BookDao {
    static MongoClient connectMlab = new MongoClient(new MongoClientURI("mongodb://admin:admin02@ds247223.mlab.com:47223/ebook"));
    static MongoDatabase db = connectMlab.getDatabase("ebook");
    static MongoCollection<Document> Collection;
    static GridFSBucket gridFSBucket = GridFSBuckets.create(db);
    public static Data.Book getBookById(int eBookId){
        Data.Book result = null;
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
                result = new Data.Book(0b1,eBookName,eBookKinds,publisherName,authorName,fileBook,
                        eBookPrice, eBookCoverPrice,fileEbookSize,pages,eBookCover_id);
            }
            return result;
        } catch (Exception eX) {
            eX.printStackTrace();
        }
           return result;
    }
    public static boolean getImgeProduc(int eBookId){
        ObjectId imageBook = null;
          try {
            Path path = FileSystems.getDefault().getPath("src/img/product.png").toAbsolutePath();
            OutputStream streamToDownloadTo = new FileOutputStream(path.toString());
            Collection = db.getCollection("Book");
            try {
            Document searchuser = new Document("eBookId", eBookId);
            FindIterable<Document> iterateDoc = Collection.find(searchuser);
            Iterator<Document> iterator = iterateDoc.iterator();
            while (iterator.hasNext()) {
                Document data = new Document(iterator.next());
                if (data.getInteger("eBookId") == eBookId) {
                   imageBook = data.getObjectId("eBookCover_id");
                }
            }
            } catch (Exception ex) {
                return false;
            }
            gridFSBucket.downloadToStream(imageBook, streamToDownloadTo);
            streamToDownloadTo.close();
            System.out.println("Download to File Success");
            return true;
        } catch (Exception ex) {
            System.err.println("Download to File Failure = "+ex);
        }
        return false;
    }
}
