package com.ssafy.vue.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BookMarkDto : 북마크 정보", description = "북마크를 나타낸다.")
public class BookMarkDto {
	
	@ApiModelProperty
	private int bookmarkid;
	
	@ApiModelProperty
	private String userid;
	
	@ApiModelProperty
	private String contentid;
	
	@ApiModelProperty
	private String firstimage;
	
	@ApiModelProperty
	private String address;
	
	@ApiModelProperty
	private String title;

	public int getBookmarkid() {
		return bookmarkid;
	}

	public void setBookmarkid(int bookmarkid) {
		this.bookmarkid = bookmarkid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContentid() {
		return contentid;
	}

	public void setContentid(String contentid) {
		this.contentid = contentid;
	}

	public String getFirstimage() {
		return firstimage;
	}

	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "BookMarkDto [bookmarkid=" + bookmarkid + ", userid=" + userid + ", contentid=" + contentid
				+ ", firstimage=" + firstimage + ", address=" + address + ", title=" + title + "]";
	}
	
	


}