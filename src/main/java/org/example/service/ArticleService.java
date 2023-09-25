package org.example.service;

import org.example.Article;
import org.example.repository.ArticleRepository;

import java.sql.Connection;
import java.util.List;

public class ArticleService {

  private ArticleRepository articleRepository;

  public ArticleService(Connection conn) {
    articleRepository = new ArticleRepository(conn);
  }

  public int write(String title, String body) {
    return articleRepository.write(title, body);
  }

  public boolean articleExists(int id) {
    return articleRepository.articleExists(id);
  }

  public void delete(int id) {
    articleRepository.delete(id);
  }

  public void update(int id, String title, String body) {
    articleRepository.update(id, title, body);
  }

  public Article getArticleById(int id) {
    return articleRepository.getArticleById(id);
  }

  public List<Article> getArticles() {
    return articleRepository.getArticles();
  }
}
