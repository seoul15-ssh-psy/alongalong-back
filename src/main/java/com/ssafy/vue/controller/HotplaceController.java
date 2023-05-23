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

import com.ssafy.vue.model.HotplaceDto;
import com.ssafy.vue.model.HotplaceParameterDto;
import com.ssafy.vue.model.CommentDto;
import com.ssafy.vue.model.FileDto;
import com.ssafy.vue.model.service.HotplaceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/hotplace")
@Api("게시판 컨트롤러  API V1")
public class HotplaceController {

	
	private static final Logger logger = LoggerFactory.getLogger(HotplaceController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Value("${file.path}")
	private String uploadPath;
	
	@Autowired
	private HotplaceService hotplaceService;

	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping(value="/withfile",consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> writeArticle(@RequestParam("img") MultipartFile img,@RequestParam("userid") String userid,@RequestParam("subject") String subject,@RequestParam("content") String content) throws Exception {
		logger.info("writeArticle - 호출");
		HotplaceDto hotplaceDto = new HotplaceDto();
		hotplaceDto.setUserid(userid);
		hotplaceDto.setContent(content);
		hotplaceDto.setSubject(subject);
		if (!img.isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + File.separator + today;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			List<FileDto> fileInfos = new ArrayList<FileDto>();
			
			FileDto fileInfoDto = new FileDto();

			String originalFileName = img.getOriginalFilename();
			if (!originalFileName.isEmpty()) {
				String saveFileName = UUID.randomUUID().toString()
						+ originalFileName.substring(originalFileName.lastIndexOf('.'));
				fileInfoDto.setSavefolder(today);
				fileInfoDto.setOriginalfile(originalFileName);
				fileInfoDto.setSavefile(saveFileName);
				logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", img.getOriginalFilename(), saveFileName);
				img.transferTo(new File(folder, saveFileName));
			}
			fileInfos.add(fileInfoDto);
			
			hotplaceDto.setFileInfos(fileInfos);
		}
		
		System.out.println(img.getOriginalFilename()+"/"+hotplaceDto.toString());
		if (hotplaceService.writeArticle(hotplaceDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping(value="/withoutfile",consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> writeArticle2(@RequestParam("userid") String userid,@RequestParam("subject") String subject,@RequestParam("content") String content) throws Exception {
		logger.info("writeArticle - 호출");
		HotplaceDto hotplaceDto = new HotplaceDto();
		hotplaceDto.setUserid(userid);
		hotplaceDto.setContent(content);
		hotplaceDto.setSubject(subject);
		
		if (hotplaceService.writeArticle(hotplaceDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<HotplaceDto>> listArticle(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) HotplaceParameterDto hotplaceParameterDto) throws Exception {
		logger.info("listArticle - 호출");
		return new ResponseEntity<List<HotplaceDto>>(hotplaceService.listArticle(hotplaceParameterDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = HotplaceDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<HotplaceDto> getArticle(@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("getArticle - 호출 : " + articleno);
		hotplaceService.updateHit(articleno);
		return new ResponseEntity<HotplaceDto>(hotplaceService.getArticle(articleno), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) HotplaceDto hotplaceDto) throws Exception {
		logger.info("modifyArticle - 호출 {}", hotplaceDto);
		
		if (hotplaceService.modifyArticle(hotplaceDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("deleteArticle - 호출");
		if (hotplaceService.deleteArticle(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판 글 수", notes = "모든 게시글의 개수를 반환한다.", response = Integer.class)
	@GetMapping("/getTotalCount") 
	public ResponseEntity<Map<String,Integer>> getTotalCount(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) HotplaceParameterDto hotplaceParameterDto) throws Exception {
		logger.info("getTotalCount - 호출");
		Map resultmap = new HashMap<>();
		resultmap.put("totalCount", hotplaceService.getTotalCount(hotplaceParameterDto)) ;
		return new ResponseEntity<Map<String,Integer>>(resultmap, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 댓글 작성", notes = "댓글을 작성한다.", response = Integer.class)
	@PostMapping("/comment")
	public ResponseEntity<String> writeComment(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) CommentDto commentDto) throws Exception {
		logger.info("writeComment - 호출");
		if (hotplaceService.writeComment(commentDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} 
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판 댓글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/comment/{articleno}")
	public ResponseEntity<List<CommentDto>> listComment(@PathVariable("articleno") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("getComments - 호출"+ articleno);
		System.out.println(articleno);
		return new ResponseEntity<List<CommentDto>>(hotplaceService.getComments(articleno), HttpStatus.OK);
	}
}