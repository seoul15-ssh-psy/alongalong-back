package com.ssafy.vue.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "FileDto : 파일정보", description = "파일의 상세 정보를 나타낸다.")
public class FileDto {
	
	@ApiModelProperty
	private String savefolder;
	@ApiModelProperty
	private String originalfile;
	@ApiModelProperty
	private String savefile;
	public String getSavefolder() {
		return savefolder;
	}
	public void setSavefolder(String savefolder) {
		this.savefolder = savefolder;
	}
	public String getOriginalfile() {
		return originalfile;
	}
	public void setOriginalfile(String originalfile) {
		this.originalfile = originalfile;
	}
	public String getSavefile() {
		return savefile;
	}
	public void setSavefile(String savefile) {
		this.savefile = savefile;
	}

	
}
