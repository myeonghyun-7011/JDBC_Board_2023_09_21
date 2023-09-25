package org.example.controller;

import org.example.Article;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleController extends Controller {
  public void showWrite() {
    System.out.println((" == 게시물 등록  ==="));
    System.out.printf("제목 : ");
    String title = scanner.nextLine();
    System.out.printf("내용 : ");
    String body = scanner.nextLine();

    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append(" SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);

    int id = DBUtil.insert(conn, sql);

    System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
  }


  public void showList() {
    List<Article> articles = new ArrayList<>();

    SecSql sql = new SecSql();

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }

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
  public void showDetail() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }
//
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

  public void modify() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }
    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    int articlesCount = DBUtil.selectRowIntValue(conn, sql);

    if (articlesCount == 0) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }
    System.out.println((" == 게시물 수정  ==="));
    System.out.printf("새 제목 : ");
    String title = scanner.nextLine();
    System.out.printf("새 내용 : ");
    String body = scanner.nextLine();

    sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);
    sql.append("WHERE id = ?", id);

    DBUtil.update(conn, sql);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void delete() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }
    System.out.printf("== %d번 게시글 삭제 ==\n", id);

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

    System.out.printf("%d번 게시물이 삭제 되었습니다.\n", id);
  }


}





