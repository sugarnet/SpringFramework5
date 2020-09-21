package com.dss.data.jpa.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	private int totalPages;
	private int numElementsPerPage;
	private int currentPage;
	private List<PageItem> pages;

	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;

		this.totalPages = page.getTotalPages();
		this.numElementsPerPage = page.getSize();
		this.currentPage = page.getNumber() + 1;
		
		this.pages = new ArrayList<>();

		int from, to;
		if (this.totalPages <= this.numElementsPerPage) {
			from = 1;
			to = this.totalPages;
		} else if (this.currentPage <= this.numElementsPerPage / 2) {
			from = 1;
			to = this.numElementsPerPage;
		} else if (this.currentPage >= this.totalPages - this.numElementsPerPage / 2) {
			from = this.totalPages - this.numElementsPerPage + 1;
			to = this.numElementsPerPage;
		} else {
			from = this.currentPage - this.numElementsPerPage + 1;
			to = this.numElementsPerPage;
		}
		
		for (int i = 0; i < to; i++) {
			pages.add(new PageItem(i + 1, currentPage == from + i));
		}
		
	}

	public String getUrl() {
		return url;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}

	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean hasNext() {
		return page.hasNext();
	}
	
	public boolean hasPrevious() {
		return page.hasPrevious();
	}
}
