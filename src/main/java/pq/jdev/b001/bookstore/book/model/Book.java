// package pq.jdev.b001.bookstore.book.model;

// import java.io.Serializable;
// import java.util.Set;
// import java.util.HashSet;

// import javax.persistence.CascadeType;
// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.FetchType;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.JoinTable;
// import javax.persistence.ManyToMany;
// import javax.persistence.ManyToOne;
// import javax.persistence.OneToMany;
// import javax.persistence.Table;
// import javax.validation.constraints.NotEmpty;
// import javax.validation.constraints.NotNull;

// import pq.jdev.b001.bookstore.category.model.Category;
// import pq.jdev.b001.bookstore.publishers.model.Publishers;
// import pq.jdev.b001.bookstore.users.model.Person;

// @Entity
// @Table(name = "book")
// public class Book implements Serializable {

// 	/**
// 	 * 
// 	 */
// 	private static final long serialVersionUID = 1L;

// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY)
// 	@Column(name = "ID")
// 	private Long id;
	
// 	@NotEmpty
// 	@Column(name = "TITLE", columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin")
// 	private String title;
	
// 	@NotNull
// 	@Column(name = "PRICE")
// 	private Long price;

// 	@Column(name = "OK")
// 	private int ok;

// 	@Column(name = "DOMAIN", columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin")
// 	private String domain;

// 	@Column(name = "AUTHORS", columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin")
// 	private String authors;

// 	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
// 	@JoinColumn(name = "PERSON_ID")
// 	private Person person;

// 	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
// 	@JoinColumn(name = "PUBLISHER_ID ")
// 	private Publishers publisher;

// 	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
// 	@JoinTable(name = "book_category", joinColumns = { @JoinColumn(name = "BOOK_ID") }, inverseJoinColumns = {
// 			@JoinColumn(name = "CATEGORY_ID") })
// 	private Set<Category> categories;
	
// 	@Column(name = "PUBLISHED_YEAR")
// 	private Integer publishedYear;

// 	@Column(name = "DESCRIPTION")
//     private String description;
    
//     @Column(name = "AVATAR", nullable = false, length = Integer.MAX_VALUE)
//     private byte[] avatar;
    
//     @Column(name = "BOOK_NAME_URL", nullable = false)
// 	private String productNameURL;

// 	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
// 	private Set<Image> images = new HashSet<>();
	
// 	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
// 	private Set<Comment> comments = new HashSet<>();

// 	public Long getId() {
// 		return id;
// 	}

// 	public void setId(Long id) {
// 		this.id = id;
// 	}

// 	public String getTitle() {
// 		return title;
// 	}

// 	public void setTitle(String title) {
// 		this.title = title;
// 	}

// 	public long getPrice() {
// 		return price;
// 	}

// 	public void setPrice(Long price) {
// 		this.price = price;
// 	}

// 	public String getDomain() {
// 		return domain;
// 	}

// 	public void setDomain(String domain) {
// 		this.domain = domain;
// 	}

// 	public String getAuthors() {
// 		return authors;
// 	}

// 	public void setAuthors(String authors) {
// 		this.authors = authors;
// 	}

// 	public Person getPerson() {
// 		return person;
// 	}

// 	public void setPerson(Person person) {
// 		this.person = person;
// 	}

// 	public Publishers getPublisher() {
// 		return publisher;
// 	}

// 	public void setPublisher(Publishers publisher) {
// 		this.publisher = publisher;
// 	}

// 	public Set<Category> getCategories() {
// 		return categories;
// 	}

// 	public void setCategories(Set<Category> categories) {
// 		this.categories = categories;
// 	}

// 	public Integer getPublishedYear() {
// 		return publishedYear;
// 	}

// 	public void setPublishedYear(Integer publishedYear) {
// 		this.publishedYear = publishedYear;
// 	}

// 	public int getOk() {
// 		return ok;
// 	}

// 	public void setOk(int ok) {
// 		this.ok = ok;
// 	}

// 	public String getDescription() {
// 		return description;
// 	}

// 	public void setDescription(String description) {
// 		this.description = description;
//     }
    
    
	
// }
