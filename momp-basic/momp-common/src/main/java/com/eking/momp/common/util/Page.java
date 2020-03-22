package com.eking.momp.common.util;

import java.util.List;

import lombok.Data;

@Data
public class Page<T> {
	private List<T> rows;
	private long total;
	private long pages;

	private Page(List<T> rows, long total, long pages) {
		this.rows = rows;
		this.total = total;
		this.pages = pages;
	}

	public static <T> Page<T> of(List<T> rows, long total, long pages) {
		return new Page<>(rows, total, pages);
	}
}
