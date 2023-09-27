package org.example.session;

import org.example.dto.Member;

public class Session {
  public int loginedMemberId;
  public Member loginedMember;

  public Session(){
    loginedMemberId = -1;
  }
}
