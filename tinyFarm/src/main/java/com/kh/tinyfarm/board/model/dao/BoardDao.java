package com.kh.tinyfarm.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.tinyfarm.board.model.vo.Board;
import com.kh.tinyfarm.board.model.vo.BoardLike;
import com.kh.tinyfarm.board.model.vo.BoardReply;
import com.kh.tinyfarm.board.model.vo.BoardReport;
import com.kh.tinyfarm.board.model.vo.ReplyReport;
import com.kh.tinyfarm.common.model.vo.PageInfo;

@Repository
public class BoardDao {

	
	
	//게시글리스트
	public ArrayList<Board> selectBoardList(SqlSessionTemplate sqlSession) {

		return (ArrayList)sqlSession.selectList("boardMapper.selectBoardList");
	}

	
	/*
	
	//게시글 리스트 총 개수 
	public int boardListCount(SqlSessionTemplate sqlSession) {
		
		return sqlSession.selectOne("boardMapper.boardListCount");
	}

	//페이지바가 있는 게시글 리스트 조회
	public ArrayList<Board> selectBoardList(SqlSessionTemplate sqlSession, PageInfo pi) {
		int limit = pi.getBoardLimit();
		int offset= (pi.getCurrentPage()-1)*pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectBoardList", null, rowBounds);
	}
	*/
	//게시글 상세보기 전 조회수 증가 메소드
	public int boardIncreaseCount(SqlSessionTemplate sqlSession, int boardNo) {
		
		return sqlSession.update("boardMapper.boardIncreaseCount", boardNo);
	}
	
	//게시글 상세보기
	public Board boardDetail(SqlSessionTemplate sqlSession, int boardNo) {
	
		return sqlSession.selectOne("boardMapper.boardDetail", boardNo);
	}

	//게시물 추가
	public int insertBoard(SqlSessionTemplate sqlSession, Board b) {
		
		return sqlSession.insert("boardMapper.insertBoard", b);
	}

	//게시글 수정
	public int boardUpdate(SqlSessionTemplate sqlSession, Board boardInfo) {
		
		return sqlSession.update("boardMapper.boardUpdate",boardInfo);
	}

	//게시글 삭제
	public int boardDelete(SqlSessionTemplate sqlSession, int boardNo) {
		
		return sqlSession.delete("boardMapper.boardDelete", boardNo);
	}

	//댓글 리스트 조회
	public ArrayList<BoardReply> boardReplyList(SqlSessionTemplate sqlSession, int boardNo) {
		
		return (ArrayList)sqlSession.selectList("boardMapper.boardReplyList", boardNo);
	}

	//댓글 작성
	public int insertReply(SqlSessionTemplate sqlSession, BoardReply br) {
		
		return sqlSession.insert("boardMapper.insertReply", br);
	}
	
	//댓글 수정
	public int updateReply(SqlSessionTemplate sqlSession, BoardReply br) {
		
		return sqlSession.update("boardMapper.updateReply",br);
	}

	//댓글 삭제
	public int deleteReply(SqlSessionTemplate sqlSession, int replyNo) {
		
		return sqlSession.delete("boardMapper.deleteReply", replyNo);
	}
	
	
	//유저가 좋아요했는지 안했는지 확인
	/*
	 * public int findLike(SqlSessionTemplate sqlSession, BoardLike br) {
	 * 
	 * return sqlSession.selectOne("boardMapper.findLike", br); }
	 */	
	

	public ArrayList<BoardLike> findLike(SqlSessionTemplate sqlSession, BoardLike br) {
		// TODO Auto-generated method stub
		return (ArrayList)sqlSession.selectList("boardMapper.findLike", br);
	}
	
	
	
	//좋아요수 증가
	public int likeIncreaseCount(SqlSessionTemplate sqlSession, BoardLike bl) {
		
		return sqlSession.update("boardMapper.likeIncreaseCount", bl);
	}
	//좋아요 하기
	public int doLike(SqlSessionTemplate sqlSession, BoardLike bl) {
		
		return sqlSession.insert("boardMapper.doLike", bl);
	}

	
	//좋아요 취소누르면 카운트 감소
	public int likeDecreaseCount(SqlSessionTemplate sqlSession, BoardLike bl) {

		return sqlSession.update("boardMapper.likeDecreaseCount", bl);
	}

	//좋아요 취소
	public int cancelLike(SqlSessionTemplate sqlSession, BoardLike bl) {

		return sqlSession.delete("boardMapper.cancelLike", bl);
	}


	//게시글 신고하기 기능
	public int boardReport(SqlSessionTemplate sqlSession, BoardReport bp) {

		return sqlSession.insert("boardMapper.boardReport", bp);
	}

	//댓글 신고
	public int replyReport(SqlSessionTemplate sqlSession, ReplyReport rp) {
		System.out.println(rp);
		// TODO Auto-generated method stub
		return sqlSession.insert("boardMapper.replyReport", rp);
	}

	//게시글 검색
	public ArrayList<Board> searchBoardList(SqlSessionTemplate sqlSession, Board b) {
		
		return (ArrayList)sqlSession.selectList("boardMapper.searchBoardList",b);
	}


	


}
