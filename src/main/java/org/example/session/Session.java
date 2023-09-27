package org.example.session;

import org.example.dto.Member;

public class Session {
  public int loginedMemberId;
  public Member loginedMember;

  public Session(){
    loginedMemberId = -1; //로그인이 안되어있는 상태
  }

  public boolean isLogined() {
    return loginedMemberId != -1; // 로그인이 되어있음.
  }

  public void login(Member member) {
    loginedMemberId = member.getId();
    loginedMember = member;
  }
  public void logout() {
    loginedMemberId = -1;
    loginedMember = null;
  }
}
