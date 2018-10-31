package Dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import static com.mongodb.client.model.Filters.eq;
import java.awt.print.Book;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class BookDao {

    static MongoClient connectMlab = new MongoClient(new MongoClientURI("mongodb://admin:admin02@ds247223.mlab.com:47223/ebook"));
    static MongoDatabase db = connectMlab.getDatabase("ebook");
    static MongoCollection<Document> Collection;
    static GridFSBucket gridFSBucket = GridFSBuckets.create(db);

    public static Data.Book getBookById(int eBookId) {
        Data.Book result = null;
        try {
            Collection = db.getCollection("Book");
            Document search = new Document("eBookId", eBookId);
            FindIterable<Document> iterateDoc = Collection.find(search);
            Iterator<Document> iterator = iterateDoc.iterator();
            while (iterator.hasNext()) {
                Document data = new Document(iterator.next());
                String eBookName = data.getString("eBookName");
                String eBookKinds = data.getString("eBookKinds");
                String publisherName = data.getString("publisherName");
                String authorName = data.getString("authorName");
                String linkFile = data.getString("linkFile");
                String fileFormat = data.getString("fileFormat");
                int eBookPrice = data.getInteger("eBookPrice");
                int eBookCoverPrice = data.getInteger("eBookCoverPrice");
                int fileEbookSize = data.getInteger("fileEbookSize");
                int pages = data.getInteger("pages");
                ObjectId eBookCover_id = data.getObjectId("eBookCover_id");
                result = new Data.Book(0b1, eBookName, eBookKinds, publisherName, authorName, linkFile,
                        eBookPrice, eBookCoverPrice, fileEbookSize, pages, fileFormat, eBookCover_id);
            }
            return result;
        } catch (Exception eX) {
            eX.printStackTrace();
        }
        return result;
    }

    public static boolean getImgeProduc(int eBookId) {
        try {
            Path path = FileSystems.getDefault().getPath("src/img/product.png").toAbsolutePath();
            OutputStream streamToDownloadTo = new FileOutputStream(path.toString());
            Collection = db.getCollection("Book");
            Data.Book book = getBookById(eBookId);
            gridFSBucket.downloadToStream(book.eBookCover_id, streamToDownloadTo);
            streamToDownloadTo.close();
            System.out.println("Download to File Success");
            return true;
        } catch (Exception ex) {
            System.err.println("Download to File Failure = " + ex);
        }
        return false;
    }

    public static boolean deleteBook(int eBookId) {
        Data.Book book = getBookById(eBookId);
        Collection = db.getCollection("Book");
        try {
            gridFSBucket.delete(book.eBookCover_id);
            Collection.deleteOne(eq("eBookId", book.eBookId));
            System.out.println("Delete Success");
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    public static ObjectId uploadImageToChunk(File file, ObjectId eBookId) {
        Collection = db.getCollection("Book");
        try {
            InputStream streamToUploadFrom = new FileInputStream(file);
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(1024)
                    .metadata(new Document("eBookId", eBookId));

            ObjectId key = gridFSBucket.uploadFromStream("eBookImage", streamToUploadFrom, options);
            streamToUploadFrom.close();
            System.out.println("Upload Success");
            return key;
        } catch (IOException ex) {
            System.err.println("Upload Failure" + ex);
        }
        return null;
    }

    public static boolean insertBook(Data.Book book, File filePic) {
        Collection = db.getCollection("Book");
        ObjectId keyBookId;
        try {
            Document doc = new Document();
            doc.put("eBookId", getMaxId());
            doc.put("eBookName", book.eBookName);
            doc.put("eBookKinds", book.eBookKinds);
            doc.put("publisherName", book.publisherName);
            doc.put("authorName", book.authorName);
            doc.put("eBookPrice", book.eBookPrice);
            doc.put("eBookCoverPrice", book.eBookCoverPrice);
            doc.put("fileBookSize", book.fileBookSize);
            doc.put("linkFile", book.linkFile);
            doc.put("fileFormat", book.fileFormat);
            doc.put("pages", book.pages);
            doc.put("eBookCover_id", "");
            Collection.insertOne(doc);
            keyBookId = (ObjectId) doc.get("_id");
            ObjectId keyBookImage = uploadImageToChunk(filePic, keyBookId);
            Collection.updateOne(eq("eBookName", book.eBookName), new Document("$set", new Document("eBookCover_id", keyBookImage)));
            return true;
        } catch (Exception ex) {
            System.err.println("insert Failure");
        }
        return false;
    }

    public static boolean updateBook(Data.Book book, File filePic) {
        ObjectId keyBookId = null;
        try {
            Collection = db.getCollection("Book");
            Document found = (Document) Collection.find(new Document("eBookId", book.eBookId)).first();
            if (found != null) {
                Document document = new Document();
                document.put("eBookId", book.eBookId);
                document.put("eBookName", book.eBookName);
                document.put("eBookKinds", book.eBookKinds);
                document.put("publisherName", book.publisherName);
                document.put("authorName", book.authorName);
                document.put("eBookPrice", book.eBookPrice);
                document.put("eBookCoverPrice", book.eBookCoverPrice);
                document.put("fileBookSize", book.fileBookSize);
                document.put("linkFile", book.linkFile);
                document.put("fileFormat", book.fileFormat);
                document.put("pages", book.pages);
                if (filePic == null) {
                    Bson bsonUpdate = document;
                    Bson updateOpearation = new Document("$set", bsonUpdate);
                    Collection.updateOne(found, updateOpearation);
                } else {
                    try {
                        Document search = new Document("eBookId", book.eBookId);
                        FindIterable<Document> iterateDoc = Collection.find(search);
                        Iterator<Document> iterator = iterateDoc.iterator();
                        while (iterator.hasNext()) {
                            Document data = new Document(iterator.next());
                            keyBookId = data.getObjectId("_id");
                            gridFSBucket.delete(data.getObjectId("eBookCover_id"));
                        }
                    } catch (Exception exp) {
                    }
                    ObjectId keyBookImage = uploadImageToChunk(filePic, keyBookId);
                    document.put("eBookCover_id", keyBookImage);
                    Bson bsonUpdate = document;
                    Bson updateOpearation = new Document("$set", bsonUpdate);
                    Collection.updateOne(found, updateOpearation);
                }
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return false;
    }

    public static int getMaxId() {
        int Max = Integer.MIN_VALUE;
        try {
            Collection = db.getCollection("Book");
            FindIterable<Document> iterateDoc = Collection.find();
            Iterator<Document> iterator = iterateDoc.iterator();
            while (iterator.hasNext()) {
                Document data = new Document(iterator.next());
                if (data.getInteger("eBookId") > Max) {
                    Max = data.getInteger("eBookId");
                }
            }
            return Max + 1;
        } catch (Exception eX) {
            eX.printStackTrace();
            return 0;
        }
    }
    
    public static boolean findBookById(int eBookId) {
        try {
            Collection = db.getCollection("Book");
            Document iterateDoc = Collection.find(new Document("eBookId", eBookId)).first();
            return iterateDoc.isEmpty();
        } catch (Exception eX) {
            eX.printStackTrace();
        }
        return false;
    }
}
