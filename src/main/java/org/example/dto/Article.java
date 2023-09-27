package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;
@Data
public class Article {
  public int id;
  public String regDate;
  public String updateDate;
  public int memberId;
  public String title;
  public String body;
  public String extra__writerName;
  public int hit;


  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.memberId = (int) articleMap.get("memberId");
    this.title = (String) articleMap.get("title");
    this.body = (String) articleMap.get("body");
    this.hit = (int) articleMap.get("hit");

    if(articleMap.get("extra__writerName") != null){
      this .extra__writerName = (String) articleMap.get("extra__writerName");
    }
  }
}

