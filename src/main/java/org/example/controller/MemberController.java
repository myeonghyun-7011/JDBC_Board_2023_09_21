package org.example.controller;

import org.example.Rq;
import org.example.service.MemberService;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController extends Controller {
  private MemberService memberService;

  public MemberController(Connection conn, Scanner sc, Rq rq) {
    super(conn, sc, rq);
    memberService = new MemberService(conn);
  }

  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println((" === 회원 가입  ==="));

    // 로그인 아이디 입력
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = scanner.nextLine().trim();

      if (loginId.length() == 0) {
        System.out.println("로그인 아이디를 입력해주세요. ");
        continue;
      }

      // 프론트 데시크 직원 "중복이 있나요?"  memberService 요청을 함 .
      boolean isloginIdDup = memberService.isLoginIdDup(loginId);

      if (isloginIdDup) {
        System.out.printf("%s는 이미 사용중인 로그인 아이디입니다.\n", loginId);
        continue;
      }
      break;
    }
    // 로그인 비밀번호 입력
    while (true) {
      System.out.printf("로그인 비밀번호 : ");
      loginPw = scanner.nextLine().trim();

      if (loginPw.length() == 0) {
        System.out.println("로그인 비밀번호를 입력해주세요. ");
        continue;
      }

      // 로그인 비밀번호 확인 입력
      boolean loginPwConfirmIsSame = true;

      while (true) {
        System.out.printf("로그인 비밀번호 확인 : ");
        loginPwConfirm = scanner.nextLine().trim();

        if (loginPw.length() == 0) {
          System.out.println("로그인 비밀번호를 입력해주세요. ");
          continue;

        }
        if (loginPw.equals(loginPwConfirm) == false) {
          System.out.println("로그인 비밀번호가 일치하지 않습니다.");
          loginPwConfirmIsSame = false;
          break;

        }
        break;
      }
      if (loginPwConfirmIsSame) {
        break;
      }
    }

    // 이름 입력
    while (true) {
      System.out.printf("이름 : ");
      name = scanner.nextLine().trim();

      if (name.length() == 0) {
        System.out.println("이름을 입력해주세요. ");
        continue;
      }
      break;
    }

    // memberService이동하여 확인하러감.
    int id = memberService.join(loginId, loginPw, name);

    System.out.printf("%d번 회원이 등록되었습니다.\n", id);
  }
}

