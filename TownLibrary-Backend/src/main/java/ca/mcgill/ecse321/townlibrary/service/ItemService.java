package ca.mcgill.ecse321.townlibrary.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.townlibrary.repository.*;
import ca.mcgill.ecse321.townlibrary.model.*;

@Service
public class ItemService {
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	ArchiveRepository archiveRepository;
	@Autowired
	NewspaperRepository newspaperRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	MusicAlbumRepository musicAlbumRepository;
	
	/**
     * Retrieves an item by its id.
     * @param id    The item id
     * @return      The item or null if no such id exists
     */
	@Transactional
	public Item getItem(int id) {
		return itemRepository.findItemById(id);
	}
	
	/**
     * Retrieves an item by its associated transaction.
     * @param transaction	A transaction
     * @return      		The associated item or null if no such transaction exists
     */
	@Transactional
	public Item getItemByTransaction(Transaction transaction) {
		return itemRepository.findItemByTransaction(transaction);
	}
	
	/**
     * Retrieves all items in the system.
     * @return all items
     */
	@Transactional
	public List<Item> getAllItems() {
		return toList(itemRepository.findAll());
	}
	
	/**
     * Retrieves all items of a specific type in the system.
     * @return all items of specified type
     */
	@Transactional
	public List<Archive> getAllArchives() {
		return toList(archiveRepository.findAll());
	}
	@Transactional
	public List<Newspaper> getAllNewspapers() {
		return toList(newspaperRepository.findAll());
	}
	@Transactional
	public List<Book> getAllBooks() {
		return toList(bookRepository.findAll());
	}
	@Transactional
	public List<Movie> getAllMovies() {
		return toList(movieRepository.findAll());
	}
	@Transactional
	public List<MusicAlbum> getAllMusicAlbums() {
		return toList(musicAlbumRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	/**
     * Retrieves a list of a specific type of item by a fragment of their name.
     * @param name    The fragment of the item's name
     * @return        All items with the specified name fragment or null if no such item exists
     */
	@Transactional
	public List<Archive> getArchiveByName(String name) {
		return archiveRepository.findByNameContaining(name);
	}
	@Transactional
	public List<Newspaper> getNewspaperByName(String name) {
		return newspaperRepository.findNewspaperByNameContaining(name);
	}
	@Transactional
	public List<Book> getBookByName(String name) {
		return bookRepository.findByNameContaining(name);
	}
	@Transactional
	public List<Movie> getMovieByName(String name) {
		return movieRepository.findByNameContaining(name);
	}
	@Transactional
	public List<MusicAlbum> getMusicAlbumByName(String name) {
		return musicAlbumRepository.findByNameContaining(name);
	}
	
	/**
     * Retrieves a list of a specific type of item by their status.
     * @param name    The item's status
     * @return        All items with the specified status or null if no such item exists
     */
//	@Transactional
//	public List<Archive> getArchiveByStatus(Status status) {
//		return archiveRepository.findByStatus(status);
//	}
//	@Transactional
//	public List<Newspaper> getNewspaperByStatus(Status status) {
//		return newspaperRepository.findNewspaperByStatus(status);
//	}
	@Transactional
	public List<Book> getBookByStatus(Status status) {
		return bookRepository.findByStatus(status);
	}
	@Transactional
	public List<Movie> getMovieByStatus(Status status) {
		return movieRepository.findByStatus(status);
	}
	@Transactional
	public List<MusicAlbum> getMusicAlbumByStatus(Status status) {
		return musicAlbumRepository.findByStatus(status);
	}
	
	/**
	 * Reserves an appropriate item
	 * @param id	The item's id
	 * @return		The reserved item
	 */
	public Item reserveItem(int id) {
		Item item = itemRepository.findItemById(id);
		if (item instanceof Archive || item instanceof Newspaper) {
			throw new IllegalArgumentException("Cannot reserve archives or newspapers.");
		} 
		else if (item.getStatus() != Status.AVAILABLE){
			throw new IllegalArgumentException("This item is unavailable.");
		} 
		else {
			item.setStatus(Status.RESERVED);
			return item;
		}
	}
	
	/**
	 * Checks out an appropriate item
	 * @param id	The item's id
	 * @return		The checked out item
	 */
	public Item checkoutItem(int id) {
		Item item = itemRepository.findItemById(id);
		if (item instanceof Archive || item instanceof Newspaper) {
			throw new IllegalArgumentException("Cannot checkout archives or newspapers.");
		} 
		else if (item.getStatus() != Status.AVAILABLE){
			throw new IllegalArgumentException("This item is unavailable.");
		} 
		else {
			item.setStatus(Status.CHECKED_OUT);
			return item;
		}
	}

}
