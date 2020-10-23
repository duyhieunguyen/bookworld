package pq.jdev.b001.bookstore.cart.model;

import pq.jdev.b001.bookstore.books.model.Book;

public class Cart {
	private Book book;
	private Integer quantity;

	public Cart() {
	}

	public Cart(Book book, Integer quantity) {
		this.book = book;
		this.quantity = quantity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
