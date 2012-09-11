package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Book;

@Repository
@SuppressWarnings("unchecked")
public class BookRepository extends BaseRepository<Book> {

    public List <Book> findAll(){
        List<Book> bookList = em.createQuery("select b from Book b order by b.title").getResultList();
        return bookList;
    }
    
    public List <Book> findAllTop(){
        List<Book> bookList = em.createQuery("select b from Book b where b.top = 'true' order by b.title").getResultList();
        return bookList;
    }
    
    public List <Book> findAllOther(){
        List<Book> bookList = em.createQuery("select b from Book b where b.top = 'false' order by b.title").getResultList();
        return bookList;
    }
    
    
    public Book findBookByAbrev(String abrev){
        return getSingleOrNullResult( em.createQuery("select b from Book b where b.abrev = :abrev").setParameter("abrev",abrev) );
    }
}
