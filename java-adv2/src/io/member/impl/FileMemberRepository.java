package io.member.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import io.member.Member;
import io.member.MemberRepository;

public class FileMemberRepository implements MemberRepository {

	private static final String FILE_PATH = "temp/members-txt.dat";
	private static final String DELIMITER = ",";

	@Override
	public void add(Member member) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, StandardCharsets.UTF_8, true))) {
			bw.write(member.getId() + DELIMITER + member.getName() + DELIMITER + member.getAge());
			bw.newLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Member> findAll() {
		List<Member> members = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH, StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] memberData = line.split(DELIMITER);
				members.add(new Member(memberData[0], memberData[1], Integer.parseInt(memberData[2])));
			}
			return members;
		} catch (FileNotFoundException e) {
			return new ArrayList<>();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
