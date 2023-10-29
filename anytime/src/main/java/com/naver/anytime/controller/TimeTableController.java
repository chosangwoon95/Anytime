package com.naver.anytime.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naver.anytime.domain.TimeTable;
import com.naver.anytime.domain.TimeTable_detail;
import com.naver.anytime.service.MemberService;
import com.naver.anytime.service.TimeTableService;
import com.naver.anytime.service.TimeTable_detailService;

@Controller
public class TimeTableController {

	private static final Logger logger = LoggerFactory.getLogger(TimeTableController.class);

	private TimeTableService timeTableService;
	private TimeTable_detailService timeTable_detailService;
	private MemberService memberService;

	@Autowired
	public TimeTableController(TimeTableService timeTableService, TimeTable_detailService timeTable_detailService,
			MemberService memberService) {
		this.timeTableService = timeTableService;
		this.timeTable_detailService = timeTable_detailService;
		this.memberService = memberService;
	}

	// 시간표 View 출력
	@RequestMapping(value = "/timetable", method = RequestMethod.GET)
	public String timeTable() {
		return "timetable/timeTable";
	}
//	
//	@RequestMapping(value = "/timetable/{timetable_id}", method = RequestMethod.GET)
//	public String getTimeTable(@PathVariable("timetable_id") int timetable_id, Model model) {
//		logger.info("입장");
//		logger.info("시간표 아이디" + timetable_id);
//		// 시간표 ID에 해당하는 데이터 가져오기
//	    TimeTable timetable = timeTableService.getTimeTableById(timetable_id);
//	    
//	    if (timetable == null) {
//	        throw new RuntimeException("timetable_ID: " + timetable_id);
//	    }	
//	    
//	    // 모델에 데이터 추가
//	    model.addAttribute("timeTable", timetable);
//	    
//	    return "timetable/timeTable";  
//	}

	@RequestMapping(value = "/getTimetableByUserIdAndSemester", method = RequestMethod.POST)
	@ResponseBody
	public List<TimeTable> getTimetableBySemester(
			@RequestParam(value = "semester", defaultValue = "2023년 2학기", required = false) String semester,
			Principal userPrincipal) {
		String id = userPrincipal.getName();
		int user_id = memberService.getUserId(id);
		logger.info("유저아이디" + user_id);

		if (id == null) {
			throw new RuntimeException("사용자가 로그인하지 않았습니다.");
		}

		int timetable_check = timeTableService.checkTimetable(user_id, semester);
		List<TimeTable> timetable = new ArrayList<TimeTable>();

		if (timetable_check > 0) {
			timetable = timeTableService.getTimetableByUserIdAndSemester(user_id, semester);

		} else {
			int key = timeTableService.createDefaultTimeTable(user_id, semester);

			TimeTable new_timetable = timeTableService.getNewTimetable(key);
			logger.info("key 값 출력 : " + key);
			timetable.add(new_timetable);
		}
		return timetable;
	}

	@RequestMapping(value = "/updateTimetable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateTimetable(@RequestParam int timetable_id, 
											@RequestParam String newName,
											@RequestParam int  status,
											Principal userPrincipal) {
		try {
			logger.info("기본시간표 : "+status);
			String id = userPrincipal.getName();
			timeTableService.updateTimetable(memberService.getUserId(id), timetable_id, newName, status);
			return new ResponseEntity<>("이름 변경 성공", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("이름 변경 실패: ", e);
			return new ResponseEntity<>("이름 변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/createNewTimeTable", method = RequestMethod.POST)
	@ResponseBody
	public TimeTable createNewTimeTable(@RequestParam(value = "semester") String semester, Principal userPrincipal) {
		String id = userPrincipal.getName();
		int key = timeTableService.createNewTimeTable(memberService.getUserId(id), semester);

		return timeTableService.getNewTimetable(key);
	}
	
	// 생성 시간표 선택 
	@RequestMapping(value = "/loadTimetableDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> loadTimetableDetails(@RequestParam(value = "timetable_id") int timetable_id) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<TimeTable_detail> timetalbeDetails =timeTable_detailService.getTimetableDetails(timetable_id);
		TimeTable timetable = timeTableService.getTimeTableById(timetable_id);
		map.put("timetalbeDetails", timetalbeDetails);
		map.put("timetable", timetable);
		
		return map;
	}
	
	@RequestMapping(value="/addSubject", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addSubject(@RequestParam(value = "timetable_id", required = false) Integer timetable_id, 
                                           @RequestParam(value = "subject") String subject, 
                                           @RequestParam(value = "professor") String professor, 
                                           @RequestParam(value = "day") String day, 
                                           @RequestParam(value = "start_time") int start_time, 
                                           @RequestParam(value = "end_time") int end_time,
                                           @RequestParam(value = "classroom") String classroom) {
	   try {
	       TimeTable_detail detail=new TimeTable_detail();

	      detail.setTimetable_id(timetable_id);
	      detail.setSubject(subject);
	      detail.setProfessor(professor);
	      detail.setDay(day);
	      detail.setStart_time(start_time);
	      detail.setEnd_time(end_time);
	      detail.setClassroom(classroom);

	      
	      timeTable_detailService.addSubject(detail); 
	      
	     return new ResponseEntity<>("새로운 과목이 성공적으로 추가되었습니다.", HttpStatus.OK);
	  } catch(Exception e) {
	     logger.error("과목 추가 실패 : ", e);
	     return new ResponseEntity<>("과목을 추가하는 동안 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@RequestMapping(value = "/deleteTimetable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, String>> deleteTimetable(@RequestParam(value = "timetable_id") int timetable_id, 
																Principal userPrincipal) {
	    HashMap<String, String> result = new HashMap<>();
	    try {
	    	String id = userPrincipal.getName();
	         timeTableService.deleteTimetable(memberService.getUserId(id), timetable_id);
	        
	        result.put("message", "시간표 삭제 성공");
	        return new ResponseEntity<>(result, HttpStatus.OK);
	    } catch (Exception e) {
	        logger.error("시간표 삭제 실패: ", e);
	        
	        result.put("message", "시간표 삭제 실패");
	        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	

}
