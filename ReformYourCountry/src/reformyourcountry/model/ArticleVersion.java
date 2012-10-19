package reformyourcountry.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class ArticleVersion extends BaseEntity{

    @ManyToOne
    private Article article;
    
    @Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.TextType")
    private String summary;  // Longer than Article.description, probably 1 page long.
    
    @Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.TextType")
    private String toClassify; // Section of text used to store a draft, or quotes that will be added.
    
    @Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.TextType")
    private String content;
    
    

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getSummary(){
        return summary;
    }

    public void setSummary(String summary){
        this.summary=summary;
    }

    public String getToClassify() {
        return toClassify;
    }

    public void setToClassify(String toClassify) {
        this.toClassify = toClassify;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
