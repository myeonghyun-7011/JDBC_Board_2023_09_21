package org.example.service;

import org.example.Container;
import org.example.dto.Article;
import org.example.repository.ArticleRepository;

import java.util.List;

public class ArticleService {
  private ArticleRepository articleRepository;
  private int id;

  public ArticleService() {
    articleRepository = Container.articleRepository;
  }

  public int write(int memberId, String title, String body) {
    return articleRepository.write(memberId,title, body);
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

  public void increaseHit(int id) {
    articleRepository.increaseHit(id);
  }
}
