package com.ssafy.vue.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PlanDto : 계획정보", description = "계획의 상세 정보를 나타낸다.")
public class PlanDto {
	
	@ApiModelProperty
	private int planid;
	
	@ApiModelProperty
	private String userid;
	
	@ApiModelProperty
	private int bookmarkid;

	public int getPlanid() {
		return planid;
	}

	public void setPlanid(int planid) {
		this.planid = planid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getBookmarkid() {
		return bookmarkid;
	}

	public void setBookmarkid(int bookmarkid) {
		this.bookmarkid = bookmarkid;
	}

	@Override
	public String toString() {
		return "PlanDto [planid=" + planid + ", userid=" + userid + ", bookmarkid=" + bookmarkid + "]";
	}
	
	
	


}