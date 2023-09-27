package org.example;

import org.example.controller.ArticleController;
import org.example.controller.MemberController;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.*;
import java.util.*;

public class App {
  //private int articleLastId = 0;
  public void run() {
    Container.scanner = new Scanner(System.in);
    Container.init(); // init 객체 생성

    while (true) {
      System.out.printf("명령어) ");
      String cmd = Container.scanner.nextLine();

      Container.rq = new Rq(cmd);

      // DB 연결관리
      Connection conn = null;

      try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        System.out.println(" 예외 : MYSQL 드라이버 로딩 실패");
        System.out.println("프로그램을 종료합니다. ");
        break;
      }

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      // DB 연결
      try {
        conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

        Container.conn =conn;

        // action 메서드 실행
        action(Container.rq, cmd);

      } catch (SQLException e) {
        System.out.println(" 예외 : MYSQL 드라이버 로딩 실패");
        System.out.println("프로그램을 종료합니다. ");
        break;

      }
      // DB 종료
      finally {
        try {
          if (conn != null && !conn.isClosed()) {
            conn.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    Container.scanner.close();
  }

  private void action(Rq rq, String cmd) {
    if (rq.getUrlPath().equals("/usr/member/join")) {
      Container.memberController.join();
    }
    else if (rq.getUrlPath().equals("/usr/member/login")) {
      Container.memberController.login();
    }
    else if (rq.getUrlPath().equals("/usr/member/whoami")) {
      Container.memberController.whoami();
    }
    else if (rq.getUrlPath().equals("/usr/article/write")) {
      Container.articleController.showWrite();
    }
    else if (rq.getUrlPath().equals("/usr/article/list")) {
      Container.articleController.showList();
    }
    else if (rq.getUrlPath().equals("/usr/article/detail")) {
      Container.articleController.showDetail();
    }
    else if (rq.getUrlPath().equals("/usr/article/modify")) {
      Container.articleController.modify();
    }
    else if (rq.getUrlPath().equals("/usr/article/delete")) {
      Container.articleController.delete();
    }

//-------------------------- 시스템 종료---------------------------------------------------------------------------------
    else if (cmd.equals("system exit")) {
      System.out.println("시스템 종료");
      System.exit(0);
    } else {
      System.out.println("명령어를 확인해주세요.");
    }
    return;
  }

}

