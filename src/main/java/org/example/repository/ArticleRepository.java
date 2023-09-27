package org.example.repository;

import org.example.Container;
import org.example.dto.Article;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  public int write(String title, String body) {
    SecSql sql = new SecSql();

    sql.append("INSERT INTO article");
    sql.append(" SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);

    int id = DBUtil.insert(Container.conn, sql);
    return id;
  }

  public boolean articleExists(int id) {
    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    return DBUtil.selectRowBooleanValue(Container.conn, sql);//중복확인
  }


  public void delete(int id) {
    SecSql sql = new SecSql();

    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    DBUtil.delete(Container.conn, sql);
  }

  public void update(int id, String title, String body) {
    SecSql sql = new SecSql();

    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.conn, sql);
  }

  public Article getArticleById(int id) {

    SecSql sql = new SecSql();

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql); //해당게시물가져오기

    if(articleMap.isEmpty()){
      return null;
    }
    return new Article(articleMap);
  }

  public List<Article> getArticles() {
    SecSql sql = new SecSql();

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Article> articles = new ArrayList<>();

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.conn, sql);

    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }

    return articles;

  }
}
