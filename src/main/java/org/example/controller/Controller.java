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


  // alt + ins 해서 setter로 생성

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }
  public void setRq(Rq rq) {
    this.rq = rq;
  }

}
