package org.example.controller;

import org.example.Rq;

import java.sql.Connection;
import java.util.Scanner;

// 추상(abstract) 메서드는 private 로 만들수 없음. 외부 접근이 불가능함.
// protected를 사용하면 접근 가능
public abstract class Controller {
  protected Connection conn;
  protected Scanner scanner;
  protected Rq rq;

  public Controller(Connection conn, Scanner scanner, Rq rq) {
    this.conn = conn;
    this.scanner = scanner;
    this.rq = rq;
  }
}
// alt + ins 해서 setter로 생성

