// package pq.jdev.b001.bookstore.book.model;

// import java.io.Serializable;
// import java.util.Date;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.FetchType;
// import javax.persistence.ForeignKey;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.Table;

// @Entity
// @Table(name = "COMMENT")
// public class Comment implements Serializable {

// 	private static final long serialVersionUID = 1L;

// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY)
// 	@Column(name = "COMMENT_ID")
// 	private Long commentId;

// 	@Column(name = "CONTENT", nullable = false, length = 500)
// 	private String content;

// 	@Column(name = "DATE", nullable = false)
// 	private Date date;

// 	@Column(name = "USERNAME", nullable = false)
// 	private String username;

// 	@ManyToOne(fetch = FetchType.LAZY)
// 	@JoinColumn(name = "BOOK_ID", nullable = false, foreignKey = @ForeignKey(name = "COMMENT_BOOK_FK"))
// 	private Book book;

// 	public Comment() {
// 	}

// 	public Comment(Long commentId, String content, Date date, String username) {
// 		this.commentId = commentId;
// 		this.content = content;
// 		this.date = date;
// 		this.username = username;
// 	}

// 	public Long getCommentId() {
// 		return commentId;
// 	}

// 	public void setCommentId(Long commentId) {
// 		this.commentId = commentId;
// 	}

// 	public String getContent() {
// 		return content;
// 	}

// 	public void setContent(String content) {
// 		this.content = content;
// 	}

// 	public Date getDate() {
// 		return date;
// 	}

// 	public void setDate(Date date) {
// 		this.date = date;
// 	}

// 	public String getUsername() {
// 		return username;
// 	}

// 	public void setUsername(String username) {
// 		this.username = username;
// 	}

// 	public Book getBook() {
// 		return book;
// 	}

// 	public void setBook(Book book) {
// 		this.book = book;
// 	}
// }
