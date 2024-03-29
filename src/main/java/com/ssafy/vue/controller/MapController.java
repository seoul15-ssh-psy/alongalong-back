package com.ssafy.vue.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.vue.model.BoardDto;
import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.BookMarkDto;
import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.FileDto;
import com.ssafy.vue.model.MapDto;
import com.ssafy.vue.model.PlanDto;
import com.ssafy.vue.model.service.BoardService;
import com.ssafy.vue.model.service.MapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/map")
@Api("지도 컨트롤러  API V1")
public class MapController {

	
	private static final Logger logger = LoggerFactory.getLogger(MapController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";


	@Autowired
	private MapService mapService;

	@ApiOperation(value = "북마크 여부 가져오기", notes = "북마크 여부 가져오기. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping(value="/bookmark/isbooked")
	public ResponseEntity<Map<String,Object>> getBookMark(@RequestParam("userid") String userid, @RequestParam("contentid") String contentid) throws Exception {
		logger.info("북마크여부 - 호출");
		Map<String, Object> map = new HashMap();
		List<BookMarkDto> list = mapService.getIfBookMark(userid,contentid);
		if (list.size()>0) {
			System.out.println("suc");
			map.put("msg",SUCCESS);
			map.put("bookmarks",list);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}else {
			System.out.println("fai");
			map.put("msg",FAIL);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}
		
	}
	
	@ApiOperation(value = "북마크 추가하기", notes = "북마크 추가. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping(value="/bookmark")
	public ResponseEntity<String> addBookMark(BookMarkDto bookmarkdto) throws Exception {
		logger.info("북마크 추가 - 호출");
		System.out.println(bookmarkdto.toString());
		if (mapService.addIntoBookMark(bookmarkdto)>0) {
			//bookmark에 추가하고 바로 plan에 추가한다.
			int bookmarkId = mapService.getIfBookMark(bookmarkdto.getUserid(),bookmarkdto.getContentid()).get(0).getBookmarkid();
			PlanDto plandto = new PlanDto();
			plandto.setBookmarkid(bookmarkId);
			plandto.setUserid(bookmarkdto.getUserid());
			plandto.setPlandate(1);
			
			mapService.addIntoPlan(plandto);
			
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			System.out.println("fai");
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
		
	}
	
	@ApiOperation(value = "북마크 삭제하기", notes = "북마크 삭제. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping(value="/bookmark")
	public ResponseEntity<String> deleteBookMark(@RequestParam("userid") String userid, @RequestParam("contentid") String contentid) throws Exception {
		logger.info("북마크 삭제 - 호출");
		int bookmarkId = mapService.getIfBookMark(userid,contentid).get(0).getBookmarkid();
		PlanDto plandto = new PlanDto();
		plandto.setBookmarkid(bookmarkId);
		plandto.setUserid(userid);
		plandto.setPlandate(1);
		mapService.deleteFromPlan(plandto);
		System.out.println("bd"+bookmarkId);

		if (mapService.deleteFromBookMark(userid, contentid)>0) {
			//bookmark에 추가하고 바로 plan에 추가한다.
			System.out.println("suc");
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}else {
			System.out.println("fai");
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "계획 목록 가져오기", notes = "북마크 여부 가져오기. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping(value="/bookmark")
	public ResponseEntity<Map<String,Object>> getBookMark(@RequestParam("userid") String userid) throws Exception {
		logger.info("북마크여부 - 호출");
		Map<String, Object> map = new HashMap();
		List<BookMarkDto> list = mapService.getBookMarks(userid);
		if (list.size()>0) {
			map.put("msg",SUCCESS);
			map.put("bookmarks",list);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}else {
			map.put("msg",FAIL);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}
		
	}
	
	@ApiOperation(value = "계획 가져오기", notes = "북마크 여부 가져오기. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping(value="/plan")
	public ResponseEntity<Map<String,Object>> getPlan(@RequestParam("userid") String userid) throws Exception {
		logger.info("계획여부 - 호출");
		Map<String, Object> map = new HashMap();
		List<PlanDto> list = mapService.getPlans(userid);
		if (list.size()>0) {
			map.put("msg",SUCCESS);
			map.put("plans",list);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}else {
			map.put("msg",FAIL);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}
		
	}
	
	@ApiOperation(value = "계획 가져오기", notes = "북마크 여부 가져오기. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@GetMapping(value="/plan/bydate")
	public ResponseEntity<Map<String,Object>> getPlanByDate(@RequestParam("userid") String userid,@RequestParam("plandate") int plandate) throws Exception {
		logger.info("계획여부 - 호출");
		Map<String, Object> map = new HashMap();
		List<PlanDto> list = mapService.getPlanByDate(userid,plandate);
		if (list.size()>0) {
			map.put("msg",SUCCESS);
			map.put("plans",list);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}else {
			map.put("msg",FAIL);
			return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		}
		
	}
}