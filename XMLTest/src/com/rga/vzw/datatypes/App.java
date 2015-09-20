package com.rga.vzw.datatypes;


public class App {

	private String title;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description ;
	}

	/**
	 * @return the developer
	 */
	public String getDeveloper() {
		return developer;
	}

	/**
	 * @return the largeImage
	 */
	public String getLargeImage() {
		return largeImage;
	}

	/**
	 * @return the smallImage
	 */
	public String getSmallImage() {
		return smallImage;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param developer
	 *            the developer to set
	 */
	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	/**
	 * @param largeImage
	 *            the largeImage to set
	 */
	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	/**
	 * @param smallImage
	 *            the smallImage to set
	 */
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	private String description;
	private String developer;
	private String largeImage;
	private String smallImage;

	

}
