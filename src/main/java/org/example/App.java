package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;

    List<Article> articles = new ArrayList<>();
    int articleLastId = 0;

    while (true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      if (cmd.equals("/usr/article/write")) {
        System.out.println((" == 게시물 등록  ==="));
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();
        int id = ++articleLastId;//같은뜻
        /*int id = articleLastId + 1 ;
        articleLastId++;*/

//-------------------연결 시작문----------------------------------------------------------------------------------------------
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

          String sql = "INSERT INTO article";
          sql += " SET regDate = NOW()";
          sql += ", updateDate = NOW()";
          sql += " , title = \"" + title + "\" ";
          sql += " , `body` = \"" + body + "\" ";

          System.out.println("sql : " + sql);

          pstmt = conn.prepareStatement(sql);
          int affectedRows = pstmt.executeUpdate();


          System.out.println("affectedRows : " + affectedRows);

        }
        catch(ClassNotFoundException e){
          System.out.println("드라이버 로딩 실패");
        }
        catch(SQLException e){
          System.out.println("에러: " + e);
        }
        finally{
          try{
            if( conn != null && !conn.isClosed()){
              conn.close();
            }
          }
          catch( SQLException e){
            e.printStackTrace();
          }
          try{
            if( pstmt != null && !pstmt.isClosed()){
              pstmt.close();
            }
          }
          catch( SQLException e){
            e.printStackTrace();
          }
        }
//-----------------연결끝문-------------------------------------------------------------------------------------------------


        Article article = new Article(id, title, body);
        articles.add(article);
        // 바깥쪽에 List<Article> articles = new ArrayList<>(); 생성해서 하나씩정장

        System.out.println("생성된 게시물 객체 : " + article);
        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
      } else if (cmd.equals("/usr/article/list")) {
        System.out.println((" == 게시물 리스트  ==="));

        if (articles.isEmpty()) {
          System.out.println("등록된 게시물이 없습니다.");
          continue;
        }
        System.out.println("번호 / 제목");
        for (Article article : articles) {
          System.out.printf("%d / %s\n", article.id, article.title);
        }

      } else if (cmd.equals("system exit")) {
        System.out.println("시스템 종료");
        break;
      } else {
        System.out.println("명령어를 확인해주세요.");
      }
    }
    sc.close();
  }
}

class Article {
  public int id;
  public String title;
  public String body;

  public Article(int id, String title, String body) {
    this.id = id;
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
