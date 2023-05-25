package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	public void register(MemberDto memberDto) throws Exception;
	public List<CommentDto> getComments(String userid);
	public List<BoardDto> getArticles(String userid);
}
