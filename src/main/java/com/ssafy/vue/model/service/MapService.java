package com.ssafy.vue.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.BookMarkDto;
import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.FileDto;
import com.ssafy.vue.model.PlanDto;

public interface MapService {

	List<BookMarkDto> getIfBookMarked(String userid, String contentid);
	int addIntoBookMarked(BookMarkDto bookmarkdto);
	int deleteFromBookMarked(String userid, String contentid);
	int addIntoPlan(PlanDto plandto);
	int deleteFromPlan(PlanDto plandto);
	
}
