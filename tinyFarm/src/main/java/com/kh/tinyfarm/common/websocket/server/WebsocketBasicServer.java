package com.kh.tinyfarm.common.websocket.server;

import java.net.http.HttpRequest;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

//상속 또는 구현으로 websocket 관련 메소드 구현하기
//-WebsocketServer를 만들기 위해서는 TextWebSocketHandler 클래스 또는 WebSocketHandler 인터페이스 구현

//public class WebsocketBasicServer extends TextWebSocketHandler{
//public class WebsocketBasicServer implements WebSocketHandler
@Slf4j
public class WebsocketBasicServer extends TextWebSocketHandler{
	/*
	 * 웹소켓 - web에서 수행하는 socket통신 - socket통신은 연결형 통신
	 * 		  기본 통신은 HTTP로 진행되며 필요시에만 websocket을 사용한다
	 * 실시간 작업을 처리할때 유용하다
	 * ex)채팅, 시세변동사이트,알림기능, ...
	 * 
	 * WebSocketSession : 기존에 사용하던 HttpSession과는 다르다.
	 *  - 사용자의 웹소켓 정보가 담겨있음.
	 * 
	 * */
	
	private Set<WebSocketSession> users = new CopyOnWriteArraySet<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		users.add(session);
		//접속했을때
		log.info("접속");
		log.info("session : {}",session);
		
	}
	/*
	 * 메세지 수신시 실행될 메소드
	 * handleTextMessage() : 메세지 수신시 실행될 메소드
	 * -session : 사용자(전송한 사용자)의 웹소켓 정보
	 * -message : 사용자가 전송한 메세지 정보
	 *   - payload : 메세지내용
	 *   - byteCount : 보낸 메세지 크기
	 *   - last : 메세지 종료 여부
	 * */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 메세지를 받았을때
		//log.info("메세지 수신");
		log.info("session : {}",session);
		//log.info("message : {}",message);
		//메세지랑 userId 붙여서 보내기
		
		
		//새로만든 텍스트를 넣고 TextMessage 생성하기
		
		
		
		for(WebSocketSession ws : users) {
			if(ws.getId().equals(session.getId())) {
				TextMessage newMessage = new TextMessage("나 : "+message.getPayload());
				
				ws.sendMessage(newMessage);
				
			}else {
				TextMessage newMessage = new TextMessage("상대방 : "+message.getPayload());
				ws.sendMessage(newMessage);
			}
			
			
		}
	}
	
	/*
	 * 	afterConnectionClosed() : 사용자 접속 종료시 실행되는 메소드
	 *  - session : 사용자 웹소켓 정보
	 *  - status : 접속이 종료된 원인과 관련 정보
	 * 
	 * */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 접속 종료 됐을 때
		log.info("접속 종료");
		log.info("session : {}",session);
		log.info("status : {}",status);
		users.remove(session);
	}
	
}
