package chat.server.command;

import java.io.IOException;

import chat.server.Session;

public interface Command {
	void execute(String[] args, Session session) throws IOException;
}
