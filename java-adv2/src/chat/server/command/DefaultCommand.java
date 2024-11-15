package chat.server.command;

import java.io.IOException;
import java.util.Arrays;

import chat.server.Session;

public class DefaultCommand implements Command {

	@Override
	public void execute(String[] args, Session session) throws IOException {
		session.send("Unknown command: " + Arrays.toString(args));
	}
}
