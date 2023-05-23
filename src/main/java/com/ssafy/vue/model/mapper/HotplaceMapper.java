package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.HotplaceDto;
import com.ssafy.vue.model.HotplaceParameterDto;
import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.FileDto;

@Mapper
public interface HotplaceMapper {
	
	public int writeArticle(HotplaceDto hotplaceDto) throws SQLException;
	public List<HotplaceDto> listArticle(HotplaceParameterDto hotplaceParameterDto) throws SQLException;
	public int getTotalCount(HotplaceParameterDto hotplaceParameterDto) throws SQLException;
	public HotplaceDto getArticle(int articleno) throws SQLException;
	public void updateHit(int articleno) throws SQLException;
	public int modifyArticle(HotplaceDto hotplaceDto) throws SQLException;
	public void deleteMemo(int articleno) throws SQLException;
	public int deleteArticle(int articleno) throws SQLException;
	public void registerFile(HotplaceDto hotplaceDto);
	public FileDto getFileInfoList(int articleno) throws SQLException;
	public int writeComment(CommentDto commentDto) throws SQLException;
	public List<CommentDto> getComments(int articleno) throws SQLException;
	
}