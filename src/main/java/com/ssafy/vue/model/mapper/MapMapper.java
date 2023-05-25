package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.BookMarkDto;
import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.FileDto;
import com.ssafy.vue.model.PlanDto;

@Mapper
public interface MapMapper {

	List<BookMarkDto> getIfBookMark(Map<String, Object> map);
	int addIntoBookMark(Map<String, Object> map);
	int deleteFromBookMark(Map<String, Object> map);
	int addIntoPlan(Map<String, Object> map);
	int deleteFromPlan(Map<String, Object> map);
	List<PlanDto> getPlanByDate(Map<String, Object> map);
	List<PlanDto> getPlans(String userid);
	List<BookMarkDto> getBookMarks(String userid);
}