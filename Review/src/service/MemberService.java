package service;

import java.util.ArrayList;

import entity.Member;

public interface MemberService {
	
	String greet(int i);
	
	ArrayList<Member> getAll();
	
	int sumOf(int x, int y);

}
