package org.example;

import java.util.Map;

public class Article {
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

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.title = (String) articleMap.get("title");
    this.body = (String) articleMap.get("body");
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

