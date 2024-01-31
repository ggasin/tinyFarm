package com.kh.tinyfarm.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.tinyfarm.admin.model.service.AdminService;
import com.kh.tinyfarm.board.model.service.BoardService;
import com.kh.tinyfarm.board.model.vo.BoardReport;
import com.kh.tinyfarm.common.model.service.BookService;
import com.kh.tinyfarm.common.model.vo.Book;
import com.kh.tinyfarm.common.model.vo.PageInfo;
import com.kh.tinyfarm.common.template.Pagination;
import com.kh.tinyfarm.member.model.service.MemberService;
import com.kh.tinyfarm.member.model.vo.Member;
import com.kh.tinyfarm.qna.model.service.QnaService;
import com.kh.tinyfarm.qna.model.vo.Qna;

@Controller
public class AdminController {
	
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private BookService bookService;
	
	

	
	
	
	//메인 페이지
	@GetMapping("/main.ad")
	public String adminMain(Model model) {
		
		//오늘의 통계
		int mCount = adminService.selectStaticCount("member", "count", "today");
		int qCount = adminService.selectStaticCount("qna", "count", "today");
		int pCount = adminService.selectStaticCount("product", "count", "today");
		int pmCount = adminService.selectStaticCount("payment", "count", "today");
		
		int bCount = adminService.selectStaticCount("board", "count", "today");
		int brCount = adminService.selectStaticCount("boardReply", "count", "today");
		int	breCount = adminService.selectStaticCount("boardReport", "count", "today");
		int rreCount = adminService.selectStaticCount("replyReport", "count", "today");
		
		HashMap<String, Integer> todayMap = new HashMap<String, Integer>();
		
		todayMap.put("mCount", mCount);
		todayMap.put("qCount", qCount);
		todayMap.put("pCount", pCount);
		todayMap.put("pmCount", pmCount);
		
		
		todayMap.put("bCount", bCount);
		todayMap.put("brCount", brCount);
		todayMap.put("breCount", breCount);
		todayMap.put("rreCount", rreCount);

		model.addAttribute("todayMap", todayMap);
		
		
		
		
		//회원가입 통계
		int memberAllCount = adminService.selectStaticCount("member", "count", "all");

		int activeCount = adminService.memberStaticCount("active");
		int dropCount = memberAllCount - activeCount;
		
		int snsCount = adminService.memberStaticCount("sns");
		int normalCount = memberAllCount - snsCount;
		
		
		HashMap<String, Integer> mMap = new HashMap<String, Integer>();
		
		mMap.put("allCount", memberAllCount);				//전체수
		mMap.put("activeCount", activeCount);		//활동회원수
		mMap.put("dropCount", dropCount);			//비활동회원수
		mMap.put("snsCount", snsCount);				//sns가입회원수
		mMap.put("normalCount", normalCount);		//일반회원수
		
		mMap.put("normalPercentage", (int)(normalCount * 100 / memberAllCount));
		mMap.put("activePercentage", (int)(activeCount * 100 / memberAllCount));
		
		model.addAttribute("mMap", mMap);
		
		

		//QNA 통계
		int qnaAllCount = adminService.selectStaticCount("qna", "count", "all");
		int qnaAnswerAllCount = adminService.selectStaticCount("qnaAnswer", "count", "all");
		
		HashMap<String, Integer> qnaMap = new HashMap<String, Integer>();
		
		qnaMap.put("qnaCount", qnaAllCount);				//전체수
		qnaMap.put("qnaAnswerCount", qnaAnswerAllCount);
		
		model.addAttribute("qnaMap", qnaMap);
		

		
		
		//상품 통계
		int productAllCount = adminService.selectStaticCount("product", "count", "all");
		int productOnSaleCount = adminService.selectStaticCount("product", "count", "onSale");

		int categoryCount1 = adminService.selectStaticCount("product", "count", "categoryNo__1");
		int categoryCount2 = adminService.selectStaticCount("product", "count", "categoryNo__2");
		int categoryCount3 = adminService.selectStaticCount("product", "count", "categoryNo__3");
		
		HashMap<String, Integer> productMap = new HashMap<String, Integer>();
		
		productMap.put("allCount", productAllCount);					//전체수
		productMap.put("onSaleCount", productOnSaleCount);				//판매수

		productMap.put("vegetable", categoryCount1);				
		productMap.put("fruit", categoryCount2);				
		productMap.put("grain", categoryCount3);				
		
		model.addAttribute("productMap", productMap);
		
		
		
		
		//판매 통계
		int paymentAllCount = adminService.selectStaticCount("payment", "count", "all");
		int paymentMax = adminService.selectStaticCount("payment", "max", "all");
		int paymentMin = adminService.selectStaticCount("payment", "min", "all");
		int paymentAvg = adminService.selectStaticCount("payment", "avg", "all");
		
		
		int paymentSum = adminService.selectStaticCount("payment", "sum", "all");
		int paymentTodaySum = adminService.selectStaticCount("payment", "sum", "today");
		int paymentTodayAvg = adminService.selectStaticCount("payment", "avg", "today");
		
		HashMap<String, Integer> paymentMap = new HashMap<String, Integer>();
		
		paymentMap.put("allCount", paymentAllCount);
		paymentMap.put("max", paymentMax);
		paymentMap.put("min", paymentMin);
		paymentMap.put("avg", paymentAvg);
		paymentMap.put("sum", paymentSum);
		paymentMap.put("todaySum", paymentTodaySum);
		paymentMap.put("todayAvg", paymentTodayAvg);

		model.addAttribute("paymentMap", paymentMap);
		
		
		
		
		//게시글 통계
		int boardAllCount = adminService.selectStaticCount("board", "count", "all");
		int boardReplyAllCount = adminService.selectStaticCount("boardReply", "count", "all");
		int boardReportAllCount = adminService.selectStaticCount("boardReport", "count", "all");
		int ReplyReportAllCount = adminService.selectStaticCount("replyReport", "count", "all");
		
		
		HashMap<String, Integer> boardMap = new HashMap<String, Integer>();
		
		boardMap.put("boardCount", boardAllCount);
		boardMap.put("boardReplyCount", boardReplyAllCount);
		boardMap.put("boardReportCount", boardReportAllCount);
		boardMap.put("replyReportCount", ReplyReportAllCount);
		
		model.addAttribute("boardMap", boardMap);
		

		return "admin/main";
	}
	
	
	
	//QNA 목록
	@GetMapping("/qnaList.ad")
	public String selectQnaList(@RequestParam(value="currentPage", defaultValue="1") int currentPage, @RequestParam(value="answerYn", defaultValue="2") int answerYn, Model model, HttpSession session) {
		
		HashMap<String, Integer> qMap = new HashMap<String, Integer>();
		qMap.put("answerYn", answerYn);
		qMap.put("userNo", ((Member)session.getAttribute("loginUser")).getUserNo());
		
		// 전체 게시글 개수(listCount) - selectListCount() 메소드 명
		int qnaListCount = qnaService.qnaListCount(qMap);

		// 한 페이지에서 보여줘야 하는 게시글 개수(boardLimit)
		int boardLimit = 5;
		// 페이징 바 개수(pageLimit)
		int pageLimit = 5;

		PageInfo pi = Pagination.getPageInfo(qnaListCount, currentPage, pageLimit, boardLimit);

		// 페이징 처리된 게시글 목록 조회해서 boardListView에 보여주기
		ArrayList<Qna> qList = qnaService.selectQnaList(pi, qMap);
		
		
		System.out.println("답변여부" + answerYn);
		

		model.addAttribute("qList", qList);
		model.addAttribute("pi", pi);
		model.addAttribute("answerYn", answerYn);


		return "admin/qnaList";
	}
	
	
	//QNA 답변 페이지 Form
	@PostMapping("/qnaAnswerForm.ad")
	public String qnaAnswerForm(int qnaNo, Model model) {

		Qna qna = qnaService.selectQnaDetail(qnaNo);
		
		model.addAttribute("qna", qna);
		
		return "admin/qnaAnswerForm";
	}
	
	
	
	//QNA 답변 등록
	@PostMapping("/qnaAnswer.ad")
	public String qnaAnswerEnroll(Qna qnaAnswer, Model model, HttpSession session) {
		
		int result = qnaService.qnaAnswerEnroll(qnaAnswer);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "답변을 등록하였습니다.");
		}else {
			session.setAttribute("alertMsg", "답변 등록에 실패하였습니다.");
		}
		
		return "redirect:qnaList.ad";
	}

	
	//QNA 답변 수정 Form
	@PostMapping("/qnaAnswerUpdateForm.ad")
	public String qnaAnswerUpdateForm(int qnaNo, Model model) {
		
		Qna qna = qnaService.selectQnaDetail(qnaNo);
		model.addAttribute("qna", qna);
		
		return "admin/qnaAnswerUpdateForm";
	}
	
	
	
	//QNA 답변 수정 등록
	@PostMapping("/qnaAnswerUpdate.ad")
	public String qnaAnswerUpdate(Qna updateAnswer, Model model, HttpSession session) {
		
		int qnaNo = updateAnswer.getQnaNo();
		
		int result = qnaService.qnaAnswerUpdate(updateAnswer);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "답변을 수정하였습니다.");
		}else {
			session.setAttribute("alertMsg", "답변 수정에 실패하였습니다.");
		}
		
		return "redirect:qnaList.ad";
	}
	
	
	
	//사용자 QNA 일괄 삭제
	@PostMapping("/qnaAnswerDelete.ad")
	public String chkQnaDelete(String chkQnaList, Model model, HttpSession session) {
				
		String[] ckList = chkQnaList.split(",");
		
		ArrayList<Integer> qnaList = new ArrayList<>();
		
		for(int i = 0; i < ckList.length; i++) {
			qnaList.add(i, Integer.parseInt(ckList[i]));
		}
		
		int result = qnaService.chkQnaDelete(qnaList);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "선택한 QNA 내역을 삭제하였습니다.");
		}else {
			session.setAttribute("alertMsg", "삭제 실패!");
		}
		
		return "redirect:qnaList.ad";
	}
	
	
	
	// ###################################################################
	
	
	
	
	//회원 게시글 페이지
	@GetMapping("/memberList.ad")
	public String memberList(@RequestParam(value="currentPage", defaultValue="1") int currentPage, Model model){
		model.addAttribute("currentPage", currentPage);
		return "admin/memberList";
		
	}
	
	
	@ResponseBody
	@GetMapping("/selectMemberList.ad")
	public  ResponseEntity<Map<String, Object>> selectMemberList(@RequestParam(value="currentPage", defaultValue="1") int currentPage, String searchId, Model model) {

		// 전체 게시글 개수(listCount) - selectListCount() 메소드 명
		int memberListCount = memberService.memberListCount(searchId);

		// 한 페이지에서 보여줘야 하는 게시글 개수(boardLimit)
		int boardLimit = 5;
		// 페이징 바 개수(pageLimit)
		int pageLimit = 5;

		PageInfo pi = Pagination.getPageInfo(memberListCount, currentPage, pageLimit, boardLimit);

		// 페이징 처리된 게시글 목록 조회해서 boardListView에 보여주기
	    ArrayList<Member> mList = memberService.selectMemberList(pi, searchId);

	    
	    // 데이터를 Map에 담아서 전송
	    Map<String, Object> resultMap = new HashMap<>();
	    resultMap.put("mList", mList);
	    resultMap.put("pi", pi);

	    
	    // ResponseEntity로 감싸서 전송
	    return ResponseEntity.ok(resultMap);
		
	}
	
	
	
	//선택한 회원 일괄중지
	@ResponseBody
	@GetMapping("/memberStatus.ad")
	public String memberStatus(String chkMemberList, String status, Model model, HttpSession session) {
		
		int statusNm = 0;
		
		if(status.equals("Y")) {
			statusNm = 1;
		}
		
		
		String[] ckList = chkMemberList.split(",");
		
		ArrayList<Integer> mList = new ArrayList<>();
		
		for(int i = 0; i < ckList.length; i++) {
			mList.add(i, Integer.parseInt(ckList[i]));
		}
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("status", statusNm);
		map.put("mList", mList);

		
		int result = memberService.memberStatus(map);
		
		System.out.println("일괄 어쩌구 결과: " + result);
		
		String resultStr = "";
		
		if(result > 0) {
			resultStr = "NNNY";
		}else {
			resultStr = "NNNN";
		}
		
		return resultStr;
		
	}
	
	
	
	//회원 상세조회
	@ResponseBody
	@RequestMapping(value="memberDetailInfo.ad", produces = "application/json; charset=utf-8")
	public Member selectMemberDetailInfo(int userNo) {
		return memberService.selectMemberDetailInfo(userNo);
	}
	
	
	
	//admin - 회원상태 업데이트
	@ResponseBody
	@RequestMapping(value="memberInfoUpdate.ad", produces = "application/json; charset=utf-8")
	public Member memberStatusUpdate(Member m, Model model) {
		
		int result = memberService.memberStatusUpdate(m);
		
		if(result > 0) {
			return memberService.selectMemberDetailInfo(m.getUserNo());
		}else {
			return m;
		}
		
	}
	
	
	
	
	
	
	// ###################################################################
	
	
	
	//신고 게시글 페이지
	@GetMapping("/boardReportList.ad")
	public String boardReportList(@RequestParam(value="currentPage", defaultValue="1") int currentPage, Model model){
		model.addAttribute("currentPage", currentPage);
		return "admin/boardReportList";
		
	}
	
	
	//신고 댓글 페이지
	@GetMapping("/replyReportList.ad")
	public String boardList(@RequestParam(value="currentPage", defaultValue="1") int currentPage, Model model){
		model.addAttribute("currentPage", currentPage);
		return "admin/replyReportList";
		
	}
	
	
	
	//신고글 목록 조회하기
	@ResponseBody
	@GetMapping("selectReportList.ad")
	public  ResponseEntity<Map<String, Object>> selectReportList(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
																 @RequestParam(value="category", defaultValue="0") int category,
																 String type,
																 Model model) {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("category", String.valueOf(category));
		map.put("type", type);
		
		
		// 전체 게시글 개수(listCount) - selectListCount() 메소드 명
		int listCount = adminService.reportListCount(map);
		

		// 한 페이지에서 보여줘야 하는 게시글 개수(boardLimit)
		int boardLimit = 5;
		// 페이징 바 개수(pageLimit)
		int pageLimit = 5;

		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

		// 페이징 처리된 게시글 목록 조회해서 boardListView에 보여주기
	    ArrayList<BoardReport> reportList = adminService.selectReportList(pi, map);
	    
	    
	    // 데이터를 Map에 담아서 전송
	    Map<String, Object> resultMap = new HashMap<>();
	    
	    resultMap.put("reportList", reportList);
	    resultMap.put("pi", pi);

	    
	    // ResponseEntity로 감싸서 전송
	    return ResponseEntity.ok(resultMap);
		
	}

	
	
	
	//신고 취소, 게시글 삭제
	@ResponseBody
	@GetMapping("/reportStatus.ad")
	public String reportStatus(String chkReportList, String status, String type, Model model, HttpSession session) {
		
		
		String[] ckList = chkReportList.split(",");
		
		ArrayList<Integer> chkArrList = new ArrayList<>();
		
		for(int i = 0; i < ckList.length; i++) {
			chkArrList.add(i, Integer.parseInt(ckList[i]));
		}
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("type", type);
		map.put("status", status);
		map.put("rList", chkArrList);

		
		int result = adminService.reportStatus(map);
		
		System.out.println("result " + result);
		
		String resultStr = "";
		
		if(result > 0) {
			resultStr = "NNNY";
		}else {
			resultStr = "NNNN";
		}
		
		return resultStr;
		
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="reportDetailInfo.ad", produces = "application/json; charset=utf-8")
	public HashMap<String, Object> reportDetailInfo(int replyNo) {
		return adminService.reportDetailInfo(replyNo);
	}
	
	
	
	
	// #########################################################
	
	@GetMapping("/bookList.ad")
	public String selectBookList(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
								@RequestParam(value="bookCategory", defaultValue="all") String bookCategory,
								Model model) {
		
		HashMap<String, String> bookMap = new HashMap<String, String>();
		bookMap.put("bookCategory", bookCategory);
		
		
		
		// 전체 게시글 개수(listCount) - selectListCount() 메소드 명
		int bookListCount = bookService.bookListCount(bookMap);

		// 한 페이지에서 보여줘야 하는 게시글 개수(boardLimit)
		int boardLimit = 5;
		// 페이징 바 개수(pageLimit)
		int pageLimit = 5;

		PageInfo pi = Pagination.getPageInfo(bookListCount, currentPage, pageLimit, boardLimit);

		// 페이징 처리된 게시글 목록 조회해서 boardListView에 보여주기
		ArrayList<Book> bookList = bookService.selectBookList(pi, bookMap);
		

		model.addAttribute("bookCategory", bookCategory);
		model.addAttribute("bookList", bookList);
		model.addAttribute("pi", pi);

		
		return "admin/bookList";
	}
	

	
}
