package com.youymi.youymiframework.data;

import java.io.Serializable;

public class Pager implements Serializable {

	/**
	 * 分页实体
	 * 从 1 开始 ...
	 * 默认 每页 10条 
	 * 
	 */
	private static final long serialVersionUID = 7758990959529311865L;
	private int pageIndex;
	private int pageSize;

	public Pager() {
		pageIndex = 1;
		pageSize = 10;
	}

	public Pager(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public Pager nextPage() {
		pageIndex++;
		return this;
	}

	public Pager prevPage() {
		if (pageIndex > 1) {
			pageIndex--;
		}
		return this;
	}

	public Pager firstPage() {
		this.pageSize = 1;
		return this;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageBegin() {
		if (pageIndex < 1) {
			return 0;
		}
		return (pageIndex - 1) * pageSize;
	}
}
