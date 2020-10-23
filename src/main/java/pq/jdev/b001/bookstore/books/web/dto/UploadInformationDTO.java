package pq.jdev.b001.bookstore.books.web.dto;

import java.sql.Date;
import java.util.List;
import java.util.Base64;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import pq.jdev.b001.bookstore.books.model.SelectCategory;
import pq.jdev.b001.bookstore.books.model.Image;
import pq.jdev.b001.bookstore.books.model.Comment;
import pq.jdev.b001.bookstore.category.model.Category;
import pq.jdev.b001.bookstore.publishers.model.Publishers;

public class UploadInformationDTO {
	@NotEmpty
	private Long bookId;

	@NotEmpty
	private String bookTitle;

	@NotEmpty
	private Long price;

	@Nullable
	private String domain;

	@Nullable
	private byte[] picture;

	private Date uploadedDate;

	@Nullable
	private String authors;

	private Long publisherId;

	private List<Publishers> publishers;

	@NotEmpty
	private Integer publishedYear;

	private List<String> categoriesId;

	@Nullable
	private List<Category> categories;

	private List<SelectCategory> selectCategories;

	private String description;

	private String bookTitleURL;

	private CommonsMultipartFile file;

	private Set<Image> images;
	
	private Set<Comment> comments;

	public UploadInformationDTO() {

	}

	public UploadInformationDTO(String bookTitle, byte[] picture, String bookTitleURL) {
		this.bookTitle = bookTitle;
		this.picture = picture;
		this.bookTitleURL = bookTitleURL;
	}

	public UploadInformationDTO(Long bookId, String bookTitle, Long price, byte[] picture) {
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.price = price;
		this.picture = picture;
	}

	public UploadInformationDTO(Long bookId, String bookTitle, Long price, byte[] picture, String bookTitleURL) {
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.price = price;
		this.picture = picture;
		this.bookTitleURL = bookTitleURL;
	}

	public UploadInformationDTO(Long bookId, String bookTitle, Long price,  byte[] picture, Set<Image> images) {
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.price = price;
		
		this.picture = picture;
		this.images = images;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public List<Publishers> getPublishers() {
		return publishers;
	}

	public void setPublishers(List<Publishers> publishers) {
		this.publishers = publishers;
	}

	public Integer getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(Integer publishedYear) {
		this.publishedYear = publishedYear;
	}

	public List<String> getCategoriesId() {
		return categoriesId;
	}

	public void setCategoriesId(List<String> categoriesId) {
		this.categoriesId = categoriesId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<SelectCategory> getSelectCategories() {
		return selectCategories;
	}

	public void setSelectCategories(List<SelectCategory> selectCategories) {
		this.selectCategories = selectCategories;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBase64() {
		return Base64.getEncoder().encodeToString(picture);
	}

	public String getbookTitleURL() {
		return bookTitleURL;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
}
