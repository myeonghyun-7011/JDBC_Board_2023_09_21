package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  private int articleLastId = 0;
  public void run() {
    Scanner sc = Container.scanner;

    while (true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

      Connection conn = null;

      try { // DB연결관리
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        System.out.println(" 예외 : MYSQL 드라이버 로딩 실패");
        System.out.println("프로그램을 종료합니다. ");
        break;
      }

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      try { // DB연결
        conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

        int actionResult = doAction(conn, sc, rq, cmd);

        if (actionResult == -1) {
          break;
        }

      } catch (SQLException e) {
        System.out.println(" 예외 : MYSQL 드라이버 로딩 실패");
        System.out.println("프로그램을 종료합니다. ");
        break;

      } finally { //DB종료
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

  private int doAction(Connection conn, Scanner sc, Rq rq, String cmd) {

    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println((" == 게시물 등록  ==="));
      System.out.printf("제목 : ");
      String title = sc.nextLine();
      System.out.printf("내용 : ");
      String body = sc.nextLine();
      int id = ++articleLastId;//같은뜻
        /*int id = articleLastId + 1 ;
        articleLastId++;*/

//--------------------------   게시물 등록 JDBCincline 연결 시작문  ----------------------------------------------------------------------

      PreparedStatement pstmt = null;
      try {
        String sql = "INSERT INTO article";
        sql += " SET regDate = NOW()";
        sql += ", updateDate = NOW()";
        sql += " , title = \"" + title + "\" ";
        sql += " , `body` = \"" + body + "\" ";

        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        // System.out.println("affectedRows : " + affectedRows);
      } catch (SQLException e) {
        System.out.println("에러 :" + e);
      } finally {
        try {
          if (pstmt != null && !pstmt.isClosed()) {
            pstmt.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
//-----------------JDBCincline끝문-------------------------------------------------------------------------------------------------

        /*Article article = new Article(id, title, body);
        articles.add(article);
       // 바깥쪽에 List<Article> articles = new ArrayList<>(); 생성해서 하나씩저장
        System.out.println("생성된 게시물 객체 : " + article);*/

      System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
    }

//----------------- -----------------게시물 리스트 ---------------------------------------------------------------------------
    else if (rq.getUrlPath().equals("/usr/article/list")) {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List<Article> articles = new ArrayList<>();

//---------------------------게시물 리스트 JDBCSELECTTEST 시작문-----------------------------------------------------------------------
      try {
        String sql = " SELECT *";
        sql += " FROM article";
        sql += " ORDER BY id DESC";

        System.out.println("sql : " + sql);

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery(sql);


        while (rs.next()) {  // 다음장으로 넘김.
          int id = rs.getInt("id");

          String regDate = rs.getString("regDate");
          String updateDate = rs.getString("updateDate");
          String title = rs.getString("title");
          String body = rs.getString("body");

          Article article = new Article(id, regDate, updateDate, title, body);
          articles.add(article);
        }
      } catch (SQLException e) {
        System.out.println("에러 :" + e);
      } finally {
        try {
          if (pstmt != null && !pstmt.isClosed()) {
            pstmt.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      System.out.println("== 게시물 리스트 == ");
//--------------------------------- JDBCSELECTTEST 끝문 ---------------------------------------------------------------------------------

// ---------------------- --------- 게시물 modify --------------------------------------------------------------------------------
      if (articles.isEmpty()) {
        System.out.println("등록된 게시물이 없습니다.");
        return -1;
      }
      System.out.println("번호 / 제목");
      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }

    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return -1;
      }
      System.out.println((" == 게시물 수정  ==="));
      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String body = sc.nextLine();
//-----------------------------------------------------------------------------------------------------------------
      PreparedStatement pstmt = null;

      try {
        String sql = "Update article";
        sql += " SET regDate = NOW()";
        sql += ", updateDate = NOW()";
        sql += " , title = \"" + title + "\" ";
        sql += " , `body` = \"" + body + "\" ";
        sql += " WHERE id = " + id;

        System.out.println("sql : " + sql);

        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        // System.out.println("affectedRows : " + affectedRows);
      } catch (SQLException e) {
        System.out.println("에러 :" + e);
      } finally {
        try {
          if (pstmt != null && !pstmt.isClosed()) {
            pstmt.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
//------------------------------------------------------------------------------------------------------------------
      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    }
//-------------------------- 시스템 종료---------------------------------------------------------------------------------
    else if (cmd.equals("system exit")) {
      System.out.println("시스템 종료");
      System.exit(0);
    } else {
      System.out.println("명령어를 확인해주세요.");
    }
    return 0;
  }

}

class Article {
  public int id;
  public String title;
  public String body;
  public String regDate;
  public String updateDate;

  public Article(int id, String title, String body) {
    this.id = id;
    this.title = title;
    this.body = body;
  }

  public Article(int id, String regDate, String updateDate, String title, String body) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.title = title;
    this.body = body;
  }

  @Override //  개발자 확인겸 디버깅활동에 좋은거
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", body='" + body + '\'' +
        '}';
  }
}
