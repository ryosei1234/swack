package bean;

import java.io.Serializable;

public class ChatlogBean implements Serializable {

	//シーケンス番号
	private int no;
	//ルーム名
	private String roomname;
	//ユーザ名
	private String username;
	//メッセージ
	private String message;

	public ChatlogBean(int no, String roomname, String username, String message) {
		this.no = no;
		this.roomname = roomname;
		this.username = username;
		this.message = message;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
