package pq.jdev.b001.bookstore.books.service;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pq.jdev.b001.bookstore.books.model.Book;
import pq.jdev.b001.bookstore.books.model.SelectCategory;
import pq.jdev.b001.bookstore.books.repository.BookRepository;
import pq.jdev.b001.bookstore.books.web.dto.BookDTO;
import pq.jdev.b001.bookstore.books.web.dto.UploadInformationDTO;
import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.category.repository.CategoryRepository;
import pq.jdev.b001.bookstore.publishers.model.Publishers;
import pq.jdev.b001.bookstore.publishers.repository.PublisherRepository;
import pq.jdev.b001.bookstore.users.model.Person;
import pq.jdev.b001.bookstore.users.service.UserService;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private UserService userService;

	@Autowired
	private PublisherRepository publisherRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BookRepository bookRepository;


	@Autowired
	private ServletContext context;

	

	/**
	 * Method checkInput is used to check if user didn't miss any important
	 * information
	 */


	/** Method save is used to insert a new book to database */
	public UploadInformationDTO save(UploadInformationDTO dto, Person person, List<String> categoriesId)
			throws Exception {
		try {
			Book book = new Book();
		
			/** Handling book first */
			/** Set book.title */
			book.setTitle(dto.getTitle());
			/** Set book.price */
			book.setPrice(dto.getPrice());
			/** Set book.domain */
			book.setDomain(dto.getDomain());
			/** Set book.uploadedDate */
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			book.setUploadedDate(date);
			/** Set book.authors */
			book.setAuthors(dto.getAuthors());
			/** Set book.person */
			book.setPerson(person);
			/** Set book.publisher */
			Publishers dtoPublisher = publisherRepository.getOne(dto.getPublisherId());
			book.setPublisher(dtoPublisher);
			/** Set book.publishedYear */
			book.setPublishedYear(dto.getPublishedYear());
			/** Set book.description */
			book.setDescription(dto.getDescription());
			/** Save book to get book.id */
			Book dbBook = bookRepository.save(book);
			
			/** Set book.categories */
			Set<Category> categories = new HashSet<Category>();
			Category t = new Category();
			for (String categoryStringId : categoriesId) {
				Long categoryId = Long.parseLong(categoryStringId);
				t = categoryRepository.getOne(categoryId);
				categories.add(t);
				t = new Category();
			}
			dbBook.setCategories(categories);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** Complete handling with book */
		return dto;
	}

	public boolean checkRightInteraction(User user, Book book) throws Exception {
		Person currentUser = userService.findByUsername(user.getUsername());
		if (currentUser.getPower() == 2) {
			return true;
		} else if (currentUser.getPower() == 1) {
			try {
				Book bookFound = bookRepository.findByPersonIdandBookId(currentUser.getId(), book.getId());
				if (bookFound == null) {
					return false;
				} else {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public UploadInformationDTO update(UploadInformationDTO dto, Person person, List<String> categoriesId,
			Book editBook) throws Exception {
		try {
			Long bookid = editBook.getId();
			
			/** Update book.title */
			bookRepository.saveUpdateTitle(bookid, dto.getTitle());
			/** Update book.price */
			bookRepository.saveUpdatePrice(bookid, dto.getPrice());
			/** Update book.domain */
			bookRepository.saveUpdateDomain(bookid, dto.getDomain());
			/** Update book.uploadedDate */
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			System.out.println(date);
			bookRepository.saveUpdateUploadedDate(bookid, date);
			/** Update book.authors */
			bookRepository.saveUpdateAuthors(bookid, dto.getAuthors());
			/** Update book.person */
			bookRepository.saveUpdatePerson(bookid, person);
			/** Update book.publisher */
			Publishers dtoPublisher = publisherRepository.getOne(dto.getPublisherId());
			bookRepository.saveUpdatePublisher(bookid, dtoPublisher);
			/** Update book.publishedYear */
			bookRepository.saveUpdatePublishedYear(bookid, dto.getPublishedYear());
			/** Update book.description */
			bookRepository.saveUpdateDescription(bookid, dto.getDescription());
		
			/** Set book.categories */
			Set<Category> categories = new HashSet<Category>();
			Category t = new Category();
			for (String categoryStringId : categoriesId) {
				Long categoryId = Long.parseLong(categoryStringId);
				t = categoryRepository.getOne(categoryId);
				categories.add(t);
				t = new Category();
			}
			editBook.setCategories(categories);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public List<BookDTO> viewAllBooks() {
		List<Book> allBooks = bookRepository.findAll();
		List<BookDTO> books = new ArrayList<BookDTO>();
		BookDTO temp = new BookDTO();
		for (Book book : allBooks) {
			String stringCategories = "";
			for (Category category : book.getCategories()) {
				stringCategories = stringCategories + category.getName() + ", ";
			}
			temp.setCurrentBook(book);
			books.add(temp);
			temp = new BookDTO();
		}
		return books;
	}

	public Book findBookByID(Long id) {
		Book book = bookRepository.getOne(id);
		return book;
	}

	public List<Publishers> showAllPublishers() {
		List<Publishers> publishers = publisherRepository.findAll();
		return publishers;
	}

	public List<Category> showAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories;
	}

	public List<SelectCategory> showAllCategoriesWithFlag(Book editBook) {
		List<Category> categories = categoryRepository.findAll();
		List<SelectCategory> selectCategories = new ArrayList<SelectCategory>();
		SelectCategory temp = new SelectCategory();
		for (int i = 0; i < categories.size(); i++) {
			temp.setCategory(categories.get(i));
			for (Category o : categoryRepository.findCategoryByIdBook(editBook.getId())) {
				if (o.getId() == categories.get(i).getId()) {
					temp.setFlag(1);
				}
			}
			selectCategories.add(temp);
			temp = new SelectCategory();
		}
		return selectCategories;
	}

	@Override
	public void changePublisher(Long idFrom, Long idTo) {
		bookRepository.changePublisher(idFrom, idTo);
	}

	@Override
	public List<Book> findBookByCategories(Collection<Category> categories) {
		return bookRepository.findByCategories(categories);
	}

	@Override
	public void changeCategory(long idTo, long idFrom) {
		Category cateTo = categoryRepository.findById(idTo);
		Collection<Category> categoryCollection = new HashSet<Category>();
		Set<Category> categorySet = new HashSet<Category>();
		categoryCollection.add(categoryRepository.findById(idFrom));
		List<Book> lb = findBookByCategories(categoryCollection);
		for (Book b : lb) {
			List<Category> lc = categoryRepository.findCategoryByIdBook(b.getId());
			for (Category c : lc){
				if (c.getId() != idFrom)
					categorySet.add(c);
			}
			if (categorySet==null)
				categorySet.add(cateTo);
			Book book = bookRepository.findByid(b.getId());
			book.setCategories(categorySet);
			System.out.println(book.getId());
			bookRepository.save(book);
			categorySet = new HashSet<Category>();
		}
	}
}
