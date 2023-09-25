package org.example;

import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.*;
import java.util.*;

public class App {
  //private int articleLastId = 0;
  public void run() {
    Scanner sc = Container.scanner;
    while (true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

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

        // action 메서드 실행
        doAction(conn, sc, rq, cmd);

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
    sc.close();
  }

  private void doAction(Connection conn, Scanner sc, Rq rq, String cmd) {

//-------------------------------- Member ----------------------------------------------------------------
    if (rq.getUrlPath().equals("/usr/member/join")) {
      String loginId;
      String loginPw;
      String loginPwConfirm;
      String name;

      System.out.println((" == 회원 가입  ==="));

      // 로그인 아이디 입력
      while (true) {
        System.out.printf("로그인 아이디 : ");
        loginId = sc.nextLine().trim();

        if (loginId.length() == 0) {
          System.out.println("로그인 아이디를 입력해주세요. ");
          continue;
        }
        break;
      }
      // 로그인 비밀번호 입력
      while (true) {
        System.out.printf("로그인 비밀번호 : ");
        loginPw = sc.nextLine().trim();

        if (loginPw.length() == 0) {
          System.out.println("로그인 비밀번호를 입력해주세요. ");
          continue;
        }

        // 로그인 비밀번호 확인 입력
        boolean loginPwConfirmIsSame = true;

        while (true) {
          System.out.printf("로그인 비밀번호 확인 : ");
          loginPwConfirm = sc.nextLine().trim();

          if (loginPw.length() == 0) {
            System.out.println("로그인 비밀번호를 입력해주세요. ");
            continue;

          }
          if(loginPw.equals(loginPwConfirm) == false ) {
            System.out.println("로그인 비밀번호가 일치하지 않습니다.");
            loginPwConfirmIsSame = false;
            break;

          }
          break;
        }
        if(loginPwConfirmIsSame) {
          break;
        }
      }

      // 이름 입력
      while (true) {
        System.out.printf("이름 : ");
        name = sc.nextLine().trim();

        if (name.length() == 0) {
          System.out.println("이름을 입력해주세요. ");
          continue;
        }
        break;
      }


      SecSql sql = new SecSql();

      sql.append("INSERT INTO member");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", loginId = ?", loginId);
      sql.append(", loginPw = ?", loginPw);
      sql.append(", name = ?", name);

      int id = DBUtil.insert(conn, sql);

      System.out.printf("%d번  회원이 등록되었습니다.\n", id);
    }
//--------------------------------게시물 Write----------------------------------------------------------------

    else if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println((" == 게시물 등록  ==="));
      System.out.printf("제목 : ");
      String title = sc.nextLine();
      System.out.printf("내용 : ");
      String body = sc.nextLine();
      //int id = ++articleLastId;//같은뜻
      /* int id = articleLastId + 1 ;
        articleLastId++; */

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append(" SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `body` = ?", body);

      int id = DBUtil.insert(conn, sql);

//------------------------------------------------------------------------------------------------------------------
        /*Article article = new Article(id, title, body);
        articles.add(article);
       // 바깥쪽에 List<Article> articles = new ArrayList<>(); 생성해서 하나씩저장
        System.out.println("생성된 게시물 객체 : " + article);*/

      System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
    }
//----------------- -----------------게시물 리스트 ---------------------------------------------------------------------------
    else if (rq.getUrlPath().equals("/usr/article/list")) {

      List<Article> articles = new ArrayList<>();
//---------------------------게시물 리스트 JDBCSELECTTEST 시작문-----------------------------------------------------------------------
      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
      for (Map<String, Object> articleMap : articleListMap) {
        articles.add(new Article(articleMap));
      }
// ---------------------------------------------------------------------------------------------------------------
      System.out.println("== 게시물 리스트 == ");
      if (articles.isEmpty()) {
        System.out.println("등록된 게시물이 없습니다.");
        return;
      }
      System.out.println("번호 / 제목");
      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    }
// ---------------------- --------- 게시물 detail  --------------------------------------------------------------------------------
    else if (rq.getUrlPath().equals("/usr/article/detail")) {
      int id = rq.getIntParam("id", 0);


      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }
//-----------------------------------------------------------------------------------------------------------------
      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);
      if (articleMap.isEmpty()) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      Article article = new Article(articleMap);

      System.out.printf("번호 : %d\n", article.id);
      System.out.printf("글쓴날짜 : %s\n", article.regDate);
      System.out.printf("수정날짜: %s\n", article.updateDate);
      System.out.printf("제목 : %s\n", article.title);
      System.out.printf("내용 : %s\n", article.body);
    }
// ---------------------- --------- 게시물 modify --------------------------------------------------------------------------------
    else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }
//-----------------------------------------------------------------------------------------------------------------
      SecSql sql = new SecSql();

      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      int articlesCount = DBUtil.selectRowIntValue(conn, sql);

      if (articlesCount == 0) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }
//-----------------------------------------------------------------------------------------------------------------
      System.out.println((" == 게시물 수정  ==="));
      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String body = sc.nextLine();
//-----------------------------------------------------------------------------------------------------------------
      sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `body` = ?", body);
      sql.append("WHERE id = ?", id);

      DBUtil.update(conn, sql);
//------------------------------------------------------------------------------------------------------------------
      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    }
//---------------------------------게시물 Delete ---------------------------------------------------------------------------------
    else if (rq.getUrlPath().equals("/usr/article/delete")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }
      System.out.printf("== %d번 게시글 삭제 ==\n", id);
//-----------------------------------------------------------------------------------------------------------------
      SecSql sql = new SecSql();

      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      int articlesCount = DBUtil.selectRowIntValue(conn, sql);

      if (articlesCount == 0) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      sql = new SecSql();

      sql.append("DELETE FROM article");
      sql.append("WHERE id = ?", id);

      DBUtil.delete(conn, sql);
//------------------------------------------------------------------------------------------------------------------
      System.out.printf("%d번 게시물이 삭제 되었습니다.\n", id);
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

