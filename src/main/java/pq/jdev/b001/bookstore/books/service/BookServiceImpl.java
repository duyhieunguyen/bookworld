package pq.jdev.b001.bookstore.books.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

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

	@PersistenceContext
	private EntityManager entityManager;

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
			book.setBookTitle(dto.getBookTitle());
			/** Set book.price */
			book.setBookPrice(dto.getBookPrice());
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
				Book bookFound = bookRepository.findByPersonIdandBookId(currentUser.getId(), book.getBookId());
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
			Long bookid = editBook.getBookId();

			/** Update book.title */
			bookRepository.saveUpdateTitle(bookid, dto.getBookTitle());
			/** Update book.price */
			bookRepository.saveUpdatePrice(bookid, dto.getBookPrice());
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
			for (Category o : categoryRepository.findCategoryByIdBook(editBook.getBookId())) {
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
			List<Category> lc = categoryRepository.findCategoryByIdBook(b.getBookId());
			for (Category c : lc) {
				if (c.getId() != idFrom)
					categorySet.add(c);
			}
			if (categorySet == null)
				categorySet.add(cateTo);
			Book book = bookRepository.findByid(b.getBookId());
			book.setCategories(categorySet);
			System.out.println(book.getBookId());
			bookRepository.save(book);
			categorySet = new HashSet<Category>();
		}
	}

	private void getPathBookInfo(CriteriaQuery<Object[]> query, Root<Book> rootBook) {
		Path<Long> bookId = rootBook.get("bookId");
		Path<String> bookTitle = rootBook.get("bookTitle");
		Path<Long> bookPrice = rootBook.get("bookPrice");
		Path<byte[]> picture = rootBook.get("picture");
		Path<String> bookTitleURL = rootBook.get("bookTitleURL");
		query.multiselect(bookId, bookTitle, bookPrice, picture, bookTitleURL);
	}

	private void querySortPrice(CriteriaBuilder builder, CriteriaQuery<Object[]> query, String sort,
			Path<Long> bookPrice) {
		Long lowestPrice = (long) 0;
		Long highestPrice = (long) 0;
		if (sort.equals("u3")) {
			lowestPrice = (long) 3000000;
			query.where(builder.lessThan(bookPrice, lowestPrice));
		} else if (sort.equals("f3t5")) {
			lowestPrice = (long) 3000000;
			highestPrice = (long) 5000000;
			query.where(builder.or(builder.ge(bookPrice, lowestPrice)), builder.le(bookPrice, highestPrice));
		} else if (sort.equals("f5t10")) {
			lowestPrice = (long) 5000000;
			highestPrice = (long) 10000000;
			query.where(builder.or(builder.ge(bookPrice, lowestPrice)), builder.le(bookPrice, highestPrice));
		} else if (sort.equals("f10t15")) {
			lowestPrice = (long) 10000000;
			highestPrice = (long) 15000000;
			query.where(builder.or(builder.ge(bookPrice, lowestPrice)), builder.le(bookPrice, highestPrice));
		} else {
			highestPrice = (long) 15000000;
			query.where(builder.greaterThan(bookPrice, highestPrice));
		}
	}

	private String trimString(String keyword) {
		String newString = keyword;
		if (keyword.contains("-")) {
			newString = keyword.replace("-", " ");
		}
		return newString;
	}

	private List<UploadInformationDTO> converObjectToProductInfo(List<Object[]> objects) {
		List<UploadInformationDTO> books = new ArrayList<>();
		UploadInformationDTO book = null;
		for (Object[] values : objects) {
			Long bookId = (Long) values[0];
			String bookTitle = (String) values[1];
			Long bookPrice = (Long) values[2];
			byte[] avatar = (byte[]) values[3];
			String bookTitleURL = (String) values[4];
			book = new UploadInformationDTO(bookId, bookTitle, bookPrice, avatar, bookTitleURL);
			books.add(book);
		}
		return books;
	}

	@Override
	public List<UploadInformationDTO> findAll() {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Book> rootBook = query.from(Book.class);
		getPathBookInfo(query, rootBook);
		query.orderBy(builder.asc(rootBook.get("bookTitle")));
		List<Object[]> objects = this.entityManager.createQuery(query).getResultList();
		List<UploadInformationDTO> books = converObjectToProductInfo(objects);
		return books;
	}

	@Override
	public Book getBookById(Long bookId) {
		Book book = this.entityManager.find(Book.class, bookId);
		return book;
	}

	@Override
	public Book getBookByTitle(String bookTitle) {
		bookTitle = trimString(bookTitle);
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> query = builder.createQuery(Book.class);
		Root<Book> rootBook = query.from(Book.class);
		query.select(rootBook).where(builder.like(rootBook.get("bookTitle"), bookTitle));
		rootBook.fetch("images");
		List<Book> books = this.entityManager.createQuery(query).getResultList();
		if (books.isEmpty()) {
			return null;
		}
		Book book = books.get(0);
		return book;
	}

	@Override
	public UploadInformationDTO getBookInfoById(Long bookId) {
		Book book = getBookById(bookId);
		if (book == null) {
			return null;
		}
		return new UploadInformationDTO(book.getBookId(), book.getBookTitle(), book.getBookPrice(), book.getPicture());
	}

	@Override
	public UploadInformationDTO getBookInfoByTitle(String bookTitle) {
		Book book = getBookByTitle(bookTitle);
		if (book == null) {
			return null;
		}
		return new UploadInformationDTO(book.getBookId(), book.getBookTitle(), book.getBookPrice(), book.getDomain(),
										book.getPicture(), book.getUploadedDate(), book.getAuthors(), book.getPublishedYear(),
										book.getDescription(), book.getImages());
	}

	@Override
	public List<UploadInformationDTO> searchAutocomplete(String keyword) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<UploadInformationDTO> query = builder.createQuery(UploadInformationDTO.class);
		Root<Book> rootBook = query.from(Book.class);
		Path<String> bookTitle = rootBook.get("bookTitle");
		Path<byte[]> picture = rootBook.get("picture");
		Path<String> bookTitleURL = rootBook.get("bookTitleURL");
		query.multiselect(bookTitle, picture, bookTitleURL);
		query.where(builder.like(bookTitle, "%" + keyword + "%"));
		List<UploadInformationDTO> books = this.entityManager.createQuery(query).setMaxResults(5).getResultList();
		return books;
	}

	@Override
	public List<UploadInformationDTO> searchBookInfo(String keyword) {
		keyword = trimString(keyword);
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Book> rootBook = query.from(Book.class);
		getPathBookInfo(query, rootBook);
		query.where(builder.like(rootBook.get("bookTitle"), "%" + keyword + "%"));
		List<Object[]> objects = this.entityManager.createQuery(query).getResultList();
		List<UploadInformationDTO> books = converObjectToProductInfo(objects);
		return books;
	}

	@Override
	public List<UploadInformationDTO> searchBookBySortAuthor(String sortAuthor) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Book> rootBook = query.from(Book.class);
		getPathBookInfo(query, rootBook);
		Path<String> author = rootBook.get("authors");
		query.where(builder.like(author, sortAuthor));
		List<Object[]> objects = this.entityManager.createQuery(query).getResultList();
		List<UploadInformationDTO> books = converObjectToProductInfo(objects);
		return books;
	}

	@Override
	public List<UploadInformationDTO> searchBookBySortPrice(String sort) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		Root<Book> rootBook = query.from(Book.class);
		getPathBookInfo(query, rootBook);
		query.orderBy(builder.asc(rootBook.get("bookPrice")));
		querySortPrice(builder, query, sort, rootBook.get("bookPrice"));
		List<Object[]> objects = this.entityManager.createQuery(query).getResultList();
		List<UploadInformationDTO> books = converObjectToProductInfo(objects);
		return books;
	}
}
