package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.HotplaceDto;
import com.ssafy.vue.model.HotplaceParameterDto;
import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.FileDto;
import com.ssafy.vue.model.mapper.HotplaceMapper;

@Service
public class HotplaceServiceImpl implements HotplaceService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeArticle(HotplaceDto hotplaceDto) throws Exception {
		
		if(hotplaceDto.getSubject() == null || hotplaceDto.getContent() == null) {
			throw new Exception();
		}
		
		boolean result = sqlSession.getMapper(HotplaceMapper.class).writeArticle(hotplaceDto) == 1;
		List<FileDto> fileInfos = hotplaceDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			sqlSession.getMapper(HotplaceMapper.class).registerFile(hotplaceDto);
		}
		
		return result;
	}

	@Override
	public List<HotplaceDto> listArticle(HotplaceParameterDto hotplaceParameterDto) throws Exception {
		int start = hotplaceParameterDto.getPg() == 0 ? 0 : (hotplaceParameterDto.getPg() - 1) * hotplaceParameterDto.getSpp();
		hotplaceParameterDto.setStart(start);
		return sqlSession.getMapper(HotplaceMapper.class).listArticle(hotplaceParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(HotplaceParameterDto hotplaceParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(hotplaceParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(HotplaceMapper.class).getTotalCount(hotplaceParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / hotplaceParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = hotplaceParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < hotplaceParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public HotplaceDto getArticle(int articleno) throws Exception {
		return sqlSession.getMapper(HotplaceMapper.class).getArticle(articleno);
	}
	
	@Override
	public FileDto getFileInfoList(int articleno) throws Exception {
		return sqlSession.getMapper(HotplaceMapper.class).getFileInfoList(articleno);
	}
	
	@Override
	public void updateHit(int articleno) throws Exception {
		sqlSession.getMapper(HotplaceMapper.class).updateHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyArticle(HotplaceDto hotplaceDto) throws Exception {
		return sqlSession.getMapper(HotplaceMapper.class).modifyArticle(hotplaceDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int articleno) throws Exception {
		sqlSession.getMapper(HotplaceMapper.class).deleteMemo(articleno);
		return sqlSession.getMapper(HotplaceMapper.class).deleteArticle(articleno) == 1;
	}

	@Override
	public int getTotalCount(HotplaceParameterDto hotplaceParameterDto) throws SQLException{
		return sqlSession.getMapper(HotplaceMapper.class).getTotalCount(hotplaceParameterDto);
	}

	@Override
	public boolean writeComment(CommentDto commentDto) throws Exception {
		if(commentDto.getContent() == null) {
			throw new Exception();
		}
		System.out.println("여기까진 드가쓰"+ commentDto.toString());
		boolean result = sqlSession.getMapper(HotplaceMapper.class).writeComment(commentDto) == 1;
		return result;
	}

	@Override
	public List<CommentDto> getComments(int articleno) throws SQLException {
		return sqlSession.getMapper(HotplaceMapper.class).getComments(articleno);
	}
}