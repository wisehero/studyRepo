package hello.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TrafficController {

	private final HikariDataSource dataSource;
	private List<String> list = new ArrayList<>();

	public TrafficController(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@GetMapping("/cpu")
	public String cpu() {
		log.info("cpu");
		long value = 0;
		for (long i = 0; i < 100000000000L; i++) {
			value++;
		}
		return "ok value=" + value;
	}

	@GetMapping("/jvm")
	public String jvm() {
		log.info("jvm");
		for (int i = 0; i < 1000000; i++) {
			list.add("hello jvm! " + i);
		}

		return "ok";
	}

	@GetMapping("/jdbc")
	public String jdbc() throws SQLException {
		log.info("jdbc");
		Connection conn = dataSource.getConnection();
		// conn.close();
		return "ok";
	}

	@GetMapping("/error-log")
	public String errorLog() {
		log.error("error log");
		return "ok";
	}
}
