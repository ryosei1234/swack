package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import dao.ChatlogDAO;
import dao.DChatlogDAO;
import security.SecurityUtil;

/**
 * WebsocketServer<br>
 * リアルタイムチャットを実現するクラス<br>
 *
 * @param Info データ配列
 * @param roomname ルーム名
 * @param username ユーザー名
 * @param messages メッセージ
 * @param direct チャット識別
 */
@ServerEndpoint("/loadMessage")
public class Websocket {
	public static List<Session> sessions = new ArrayList<Session>();

	@OnOpen
	public void onOpen(Session session) {
		// 開始時
		sessions.add(session);
	}

	@OnMessage
	public void onMessage(String message) throws IOException {
		System.out.println(message);
		// クライアントからの受信時

		String[] Info = message.split(" ", 0);//文字列分割
		String roomname = Info[0];
		String username = Info[1];
		String messages = SecurityUtil.getESCEncodingString(Info[2]);//クロスサイト対策
		String direct = Info[3];

		/* DB書き込み */
		if (direct.equals("false")) {
			ChatlogDAO chatlogDAO = new ChatlogDAO();
			chatlogDAO.saveChatlog(roomname, username, messages);
		} else {
			DChatlogDAO dchatlogDAO = new DChatlogDAO();
			dchatlogDAO.saveDChatlog(roomname, username, messages);
		}

		messages = "[" + username + "]" + messages;
		for (Session session : sessions) {
			session.getBasicRemote().sendText("{\"command\":\"message\", \"text\": \"" + message.replace("\\", "\\\\").replace("\"", "\\\"") + "\",\"messages\":\"" + messages + "\"}");
		}
	}

	@OnError
	public void onError(Throwable t) throws IOException {
		// エラー発生時
		for (Session session : sessions) {
			session.getBasicRemote().sendText("{\"command\":\"error\", \"text\": \"" + t.getMessage().replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
		}
	}

	@OnClose
	public void onClose(Session session) {
		// 完了時
		sessions.remove(session);
	}
}
