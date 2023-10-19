package com.naver.anytime.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.naver.anytime.domain.Board;
import com.naver.anytime.domain.Post;
import com.naver.anytime.service.BoardService;
import com.naver.anytime.service.PostService;
import com.naver.anytime.service.ReportService;

@Controller
//@RequestMapping(value = "/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	private BoardService boardService;
	private PostService postService;
	private ReportService reportService;

	@Autowired
	public AdminController(BoardService boardService, PostService postService, ReportService reportService) {
		this.boardService = boardService;
		this.postService = postService;
		this.reportService = reportService;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage() {
		return "/admin/dashboard";
	}

	@RequestMapping(value = "/boardAdmin", method = RequestMethod.GET)
	public ModelAndView getBoardAdmin(ModelAndView mv) {
		List<Board> boardrequest = null;
		boardrequest = boardService.getBoardRequest();

		mv.addObject("boardrequest", boardrequest);
		mv.setViewName("/admin/boardAdmin");
		return mv;
	}

	@RequestMapping(value = "/updateBoardStatus", method = RequestMethod.POST)
	@ResponseBody
	public int updateBoardStatus(@RequestParam("board_id") int board_id,
			@RequestParam("approvalStatus") int approvalStatus,
			@RequestParam("rejectionreason") String rejectionreason) {
		return boardService.updateApprovalStatus(board_id, approvalStatus, rejectionreason);
	}

	// "0 0/5 * 1/1 * ?" 5분마다
	// 0: 초 0/5: 5분 간격 (매 5분) 1/1: 매일 * : 매월 ?: 요일을 지정하지 않음
	@Scheduled(cron = "0 0 * * * ?" /* 매일마다 */)
	public int updateBoardStatusCompleteScheduled() {
		return boardService.updateBoardStatusComplete();
	}

	@RequestMapping(value = "/updateBoardStatusImmediately", method = RequestMethod.GET)
	@ResponseBody
	public int updateBoardStatusImmediately(@RequestParam("board_id") int board_id) {
		int approvalStatus = 0;
		int currentStatus = boardService.getBoardStatus(board_id);

		if (currentStatus == 1) {
			approvalStatus = 2;
		} else {
			approvalStatus = 1;
		}
		return boardService.updateBoardStatusImmediately(board_id, approvalStatus);
	}

	@RequestMapping(value = "/boardtotal", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBoardTotalList(
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam("searchKey") int searchKey,
			@RequestParam(value = "keyword", required = true) String keyword) {

		if (keyword.equals("null")) {
			keyword = null;
		}
		int limit = 10;

		int listcount = boardService.getListCount(searchKey, keyword);

		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;

		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
		int startpage = ((page - 1) / 10) * 10 + 1;

		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		List<Board> boardTotal = boardService.getBoardTotalList(page, limit, searchKey, keyword);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardTotal", boardTotal);
		map.put("page", page);
		map.put("maxpage", maxpage);
		map.put("startpage", startpage);
		map.put("endpage", endpage);
		map.put("listcount", listcount);
		map.put("limit", limit);

		return map;
	}

	@RequestMapping(value = "/reportAdmin", method = RequestMethod.GET)
//		@ResponseBody
	public String getReportAdmin(@RequestParam(value = "order", defaultValue = "1", required = false) int order) {

//		List<Report> reportrequest = null;
//		reportrequest = reportService.getReportRequest(order);

//		reportrequest = 

//		List<Board> boardrequest = null;
//		boardrequest = boardService.getBoardRequest();
//
//		mv.addObject("boardrequest", boardrequest);
//		mv.setViewName("/admin/boardAdmin");

		return "/admin/reportAdmin";
	}

	@RequestMapping(value = "/adminNotice", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getAdminNotice(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam("searchKey") int searchKey, @RequestParam(value = "keyword", required = true) String keyword,
			ModelAndView mv) {

		if (keyword.equals("null")) {
			keyword = null;
		}

		int board_id = 0;

		int limit = 10;

		int listcount = postService.getPostTotalListCount(board_id, searchKey, keyword);

		// 총 페이지 수
		int maxpage = (listcount + limit - 1) / limit;

		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21 등...)
		int startpage = ((page - 1) / 10) * 10 + 1;

		// 현재 페이지에 보여줄 마지막 페이지 수 (10, 20, 30 등...)
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage)
			endpage = maxpage;

		List<Post> notice = postService.getPostTotalList(board_id, page, limit, searchKey, keyword);

		mv.setViewName("/admin/adminNotice");
		return mv;
	}

}
