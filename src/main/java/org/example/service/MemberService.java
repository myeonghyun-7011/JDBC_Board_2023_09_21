package org.example.service;

import org.example.repository.MemberRepository;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;

public class MemberService {
  private MemberRepository memberRepository;
  public MemberService(Connection conn) {
    memberRepository = new MemberRepository(conn);
  }
  public boolean isLoginIdDup(String loginId) {
    return MemberRepository.isLoginIdDup(loginId);
  }


  public int join(String loginId, String loginPw, String name) {
    return memberRepository.join(loginId, loginPw, name);
  }
}
