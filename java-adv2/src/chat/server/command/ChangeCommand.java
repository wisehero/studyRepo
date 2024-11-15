package chat.server.command;

import java.io.IOException;

import chat.server.Session;
import chat.server.SessionManager;

public class ChangeCommand implements Command {

	private final SessionManager sessionManager;

	public ChangeCommand(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	@Override
	public void execute(String[] args, Session session) throws IOException {
		String changeName = args[1];
		sessionManager.sendAll(session.getUsername() + "님이 " + changeName + "으로 변경하였습니다.");
		session.setUsername(changeName);
	}
}
