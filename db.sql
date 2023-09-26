#DB 생성
DROP DATABASE IF EXISTS text_board;
CREATE DATABASE `text_board`;
#DB선택
USE `text_board`;

#게시물 테이블 생성
CREATE TABLE article(
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
title CHAR(100) NOT NULL,
`body` TEXT NOT NULL
);

#회원 테이블 생성
CREATE TABLE `member`(
id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
regDate DATETIME NOT NULL,
updateDate DATETIME NOT NULL,
loginId CHAR(20) NOT NULL,
loginPw CHAR(100) NOT NULL,
`name` CHAR(200) NOT NULL
);

SELECT COUNT(*) > 0
FROM `member`
WHERE loginId = 'user';

#테스트 회원 데이터
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`name` = '관리자';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = '홍길동';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user2',
loginPw = 'user2',
`name` = '홍길순';

SELECT * FROM `member`;
SELECT * FROM article;
