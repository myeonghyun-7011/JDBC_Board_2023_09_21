package org.example.service;

import org.example.Container;
import org.example.dto.Member;
import org.example.repository.MemberRepository;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;

public class MemberService {
  private MemberRepository memberRepository;
  public MemberService() {
    memberRepository = Container.memberRepository;
  }
  public boolean isLoginIdDup(String loginId) {

    return MemberRepository.isLoginIdDup(loginId);
  }


  public int join(String loginId, String loginPw, String name) {
    return memberRepository.join(loginId, loginPw, name);
  }

  public Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }
}
