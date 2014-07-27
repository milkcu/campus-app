package com.milkcu.app.entity;

import java.util.List;

import com.milkcu.app.entity.base.BaseResponseData;

public class WikiResponseEntity extends BaseResponseData {

	private List<WikiCategoryListEntity> list;

	public List<WikiCategoryListEntity> getList() {
		return list;
	}

	public void setList(List<WikiCategoryListEntity> list) {
		this.list = list;
	}
	
	
}
