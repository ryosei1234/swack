var webSocket;
	window.onload = function() {
		var forRtoA = document.createElement('a');
		forRtoA.href = "loadMessage";
		webSocket = new WebSocket(forRtoA.href.replace("http://", "ws://")
				.replace("https://", "wss://"));
		var messageArea = document.getElementById("messageArea");
		var appendMessage = function(value, color) {
			var messageElement = document.createElement("div");
			messageElement.style.color = color;
			messageElement.innerText = value;
			messageArea.insertBefore(messageElement, messageArea.lastChild);
		}
		webSocket.onopen = function() {
			appendMessage("Opened", "blue");
		}
		webSocket.onclose = function() {
			appendMessage("Closed", "red");
		}
		webSocket.onmessage = function(message) {
			var data = JSON.parse(message.data);
			if ("message" == data.command) {
				appendMessage(data.messages, "black");
			} else if ("error" == data.command) {
				appendMessage(data.text, "red");
			}
		}
		webSocket.onerror = function(message) {
			appendMessage(message, "red");
		}
		var messageInput = document.getElementById("messageInput");
		var roomnameInput = document.getElementById("roomnameInput");
		var usernameInput = document.getElementById("usernameInput");
		var directInput = document.getElementById("directInput")
		messageInput.onkeypress = function(e) {
			if (13 == e.keyCode) {
				var roomname = roomnameInput.value;
				var username = usernameInput.value;
				var message = messageInput.value;
				var direct = directInput.value;
				var Info = roomname + " " + username + " " + message + " "
						+ direct;
				if (webSocket && "" != message) {
					webSocket.send(Info);
					messageInput.value = "";
				}

			}
		}
	}