package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.BookMarkDto;
import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.FileDto;
import com.ssafy.vue.model.PlanDto;
import com.ssafy.vue.model.mapper.BoardMapper;
import com.ssafy.vue.model.mapper.HotplaceMapper;
import com.ssafy.vue.model.mapper.MapMapper;

@Service
public class MapServiceImpl implements MapService {
	
	@Autowired
	private SqlSession sqlSession;
	
	

	@Override
	public List<BookMarkDto> getIfBookMarked(String userid, String contentid) {
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userid", userid);
		map.put("contentid", contentid);

		return sqlSession.getMapper(MapMapper.class).getIfBookMarked(map);
	}



	@Override
	public int addIntoBookMarked(BookMarkDto bookmarkdto) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userid", bookmarkdto.getUserid());
		map.put("contentid", bookmarkdto.getContentid());
		map.put("title", bookmarkdto.getTitle());
		map.put("firstimage", bookmarkdto.getFirstimage());
		map.put("address", bookmarkdto.getAddress());

		return sqlSession.getMapper(MapMapper.class).addIntoBookMarked(map);
	
	}



	@Override
	public int deleteFromBookMarked(String userid, String contentid) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userid", userid);
		map.put("contentid", contentid);
		return sqlSession.getMapper(MapMapper.class).deleteFromBookMarked(map);
	}



	@Override
	public int addIntoPlan(PlanDto plandto) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userid", plandto.getUserid());
		map.put("bookmarkid", plandto.getBookmarkid());
		return sqlSession.getMapper(MapMapper.class).addIntoPlan(map);
	}



	@Override
	public int deleteFromPlan(PlanDto plandto) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("userid", plandto.getUserid());
		map.put("bookmarkid", plandto.getBookmarkid());
		return sqlSession.getMapper(MapMapper.class).deleteFromPlan(map);
	}

	
}