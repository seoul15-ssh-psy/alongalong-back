package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.HotplaceDto;
import com.ssafy.vue.model.HotplaceParameterDto;
import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.FileDto;

public interface HotplaceService {
	public boolean writeArticle(HotplaceDto hotplaceDto) throws Exception;
	public List<HotplaceDto> listArticle(HotplaceParameterDto hotplaceParameterDto) throws Exception;
	public PageNavigation makePageNavigation(HotplaceParameterDto hotplaceParameterDto) throws Exception;
	public HotplaceDto getArticle(int articleno) throws Exception;
	public void updateHit(int articleno) throws Exception;
	public boolean modifyArticle(HotplaceDto hotplaceDto) throws Exception;
	public boolean deleteArticle(int articleno) throws Exception;
	FileDto getFileInfoList(int articleno) throws Exception;
	int getTotalCount(HotplaceParameterDto hotplaceParameterDto) throws SQLException;
	public boolean writeComment(CommentDto commentDto) throws Exception;
	public List<CommentDto> getComments(int articleno) throws SQLException;
}
