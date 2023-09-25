package org.example.repository;

import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;

public class MemberRepository {
  private static Connection conn;

  public MemberRepository(Connection conn) {
    this.conn = conn;
  }

  public static boolean isLoginIdDup(String loginId) {
    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ? ", loginId);

    return DBUtil.selectRowBooleanValue(conn, sql);
  }

  public int join(String loginId, String loginPw, String name) {
    SecSql sql = new SecSql();

    sql.append("INSERT INTO member");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);

    int id = DBUtil.insert(conn, sql);

    return id;
  }
}

