package com.exam.bean;

import java.io.Serializable;

public class ImportedFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title, path;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
