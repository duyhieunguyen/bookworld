package pq.jdev.b001.bookstore.books.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.User;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.model.SelectCategory;
import pq.jdev.b001.bookstore.books.web.dto.BookDTO;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.publishers.model.Publishers;
import pq.jdev.b001.bookstore.users.model.Person;

public interface BookService {


	Book findBookByID(Long id);

	UploadInformationDTO save(UploadInformationDTO dto, Person person, List<String> categoriesId)
			throws Exception;

	UploadInformationDTO update(UploadInformationDTO dto, Person person, List<String> categoriesId,
			Book editBook) throws Exception;

	public boolean checkRightInteraction(User user, Book book) throws Exception;

	List<BookDTO> viewAllBooks();

	List<Publishers> showAllPublishers();

	List<Category> showAllCategories();

	List<SelectCategory> showAllCategoriesWithFlag(Book editBook);

	public void changePublisher(Long idFrom, Long idTo);

	public void changeCategory(long idTo, long idFrom);
	
	List<Book> findBookByCategories(Collection<Category> categories);

	List<UploadInformationDTO> findAll();
	
	Book getBookById(Long bookId);
	
	Book getBookByTitle(String BookTitle);
	
	UploadInformationDTO getBookInfoById(Long bookId);
	
	UploadInformationDTO getBookInfoByTitle(String bookTitle);
	
	List<UploadInformationDTO> searchAutocomplete(String keyword);
	
	List<UploadInformationDTO> searchBookInfo(String keyword);
	
	List<UploadInformationDTO> searchBookBySortAuthor(String brand);
	
	List<UploadInformationDTO> searchBookBySortPrice(String sort);

}
