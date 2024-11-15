package chat.server.command;

import java.io.IOException;

import chat.server.Session;
import chat.server.SessionManager;

public class MessageCommand implements Command {

	private final SessionManager sessionManager;

	public MessageCommand(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	@Override
	public void execute(String[] args, Session session) throws IOException {
		String message = args[1];
		sessionManager.sendAll("[" + session.getUsername() + "] " + message);
	}
}
