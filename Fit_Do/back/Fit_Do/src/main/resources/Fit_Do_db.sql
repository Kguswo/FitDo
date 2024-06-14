DROP DATABASE IF EXISTS Fit_Do_db;
CREATE DATABASE Fit_Do_db DEFAULT CHARACTER SET utf8mb4;

USE Fit_Do_db;

CREATE TABLE user (
   id INT NOT NULL AUTO_INCREMENT,
   user_id VARCHAR(200) NOT NULL,
    user_password VARCHAR(200) NOT NULL,
    user_birth DATE NOT NULL,
    user_name VARCHAR(200) NOT NULL,
    user_gender VARCHAR(30) NOT NULL,
    user_profile_img TEXT NOT NULL,
    user_mbti varchar(30) NOT NULL,
    PRIMARY KEY(id)
);
INSERT INTO user (user_id, user_password, user_birth, user_name, user_gender, user_profile_img, user_mbti)
VALUES ('KGC8923', 'q1w2e3', '1999-01-01', '김기창', 'Male', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/김수현.jpg', 'Beginner'),
       ('KJH8846', 'qwe123', '2000-02-02', '김주형', 'Male', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/박보검.jpg', 'Beginner'),
       ('KHJ7752', 'abc123', '2000-03-03', '김현재', 'Male', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/송중기.jpg', 'Beginner'),
       ('PHY9601', 'a1b2c3', '2000-04-04', '박희연', 'Female', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/김유정.jpg', 'Beginner'),
       ('SSH9023', 'abc123', '2000-05-05', '신승호', 'Male', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/원빈.jpg', 'Beginner'),
       ('CJW8529', 'qwe123', '1998-06-06', '최장우', 'Male', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/이민호.jpg', 'Beginner'),
       ('SCY8134', 'q1w2e3', '1998-06-06', '송창용', 'Male', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/차은우.jpg', 'Beginner'),
       ('HDH9917', 'a1b2c3', '1998-06-06', '한도형', 'Male', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/현빈.jpg', 'Beginner'),
       ('YYJ9378', 'abc123', '1999-06-06', '육예진', 'Female', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/수지.jpg', 'Beginner'),
       ('HALEY9503', 'q1w2e3', '1997-06-06', '헤일리', 'Female', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/장원영sh.jpg', 'Beginner'),
       ('LGH9265', 'abc123', '2004-11-11', '임가현', 'Female', 'https://fit-do.s3.ap-southeast-2.amazonaws.com/카리나.jpg', 'Beginner');
       
       INSERT INTO user (user_id, user_password, user_birth, user_name, user_gender, user_profile_img, user_mbti) VALUES
('user1', '1234', '1999-01-01', 'John Doe', 'Male', 'https://i.pravatar.cc/150?img=1', 'Beginner'),
('user2', '1234', '2000-02-02', 'Jane Doe', 'Female', 'https://i.pravatar.cc/150?img=2', 'Intermediate'),
('user3', '1234', '2001-03-03', 'Jim Beam', 'Male', 'https://i.pravatar.cc/150?img=3', 'Expert'),
('user4', '1234', '2002-04-04', 'Jack Daniels', 'Male', 'https://i.pravatar.cc/150?img=4', 'Beginner'),
('user5', '1234', '2003-05-05', 'Johnny Walker', 'Male', 'https://i.pravatar.cc/150?img=5', 'Intermediate'),
('user6', '1234', '2004-06-06', 'Jessica Rabbit', 'Female', 'https://i.pravatar.cc/150?img=6', 'Expert'),
('user7', '1234', '2005-07-07', 'Jill Valentine', 'Female', 'https://i.pravatar.cc/150?img=7', 'Beginner'),
('user8', '1234', '2006-08-08', 'Jake Blues', 'Male', 'https://i.pravatar.cc/150?img=8', 'Intermediate'),
('user9', '1234', '2007-09-09', 'Jules Winnfield', 'Male', 'https://i.pravatar.cc/150?img=9', 'Expert'),
('user10', '1234', '2008-10-10', 'John Wick', 'Male', 'https://i.pravatar.cc/150?img=10', 'Beginner'),
('user11', '1234', '2009-11-11', 'Jane Smith', 'Female', 'https://i.pravatar.cc/150?img=11', 'Intermediate'),
('user12', '1234', '2010-12-12', 'Jack Black', 'Male', 'https://i.pravatar.cc/150?img=12', 'Expert'),
('user13', '1234', '1991-01-01', 'Jennifer Aniston', 'Female', 'https://i.pravatar.cc/150?img=13', 'Beginner'),
('user14', '1234', '1992-02-02', 'Julia Roberts', 'Female', 'https://i.pravatar.cc/150?img=14', 'Intermediate'),
('user15', '1234', '1993-03-03', 'James Bond', 'Male', 'https://i.pravatar.cc/150?img=15', 'Expert'),
('user16', '1234', '1994-04-04', 'Jessica Alba', 'Female', 'https://i.pravatar.cc/150?img=16', 'Beginner'),
('user17', '1234', '1995-05-05', 'Jack Sparrow', 'Male', 'https://i.pravatar.cc/150?img=17', 'Intermediate'),
('user18', '1234', '1996-06-06', 'Jason Bourne', 'Male', 'https://i.pravatar.cc/150?img=18', 'Expert'),
('user19', '1234', '1997-07-07', 'Judy Garland', 'Female', 'https://i.pravatar.cc/150?img=19', 'Beginner'),
('user20', '1234', '1998-08-08', 'John Lennon', 'Male', 'https://i.pravatar.cc/150?img=20', 'Intermediate'),
('user21', '1234', '1999-09-09', 'Jimi Hendrix', 'Male', 'https://i.pravatar.cc/150?img=21', 'Expert'),
('user22', '1234', '2000-10-10', 'Jerry Seinfeld', 'Male', 'https://i.pravatar.cc/150?img=22', 'Beginner'),
('user23', '1234', '2001-11-11', 'James Cameron', 'Male', 'https://i.pravatar.cc/150?img=23', 'Intermediate'),
('user24', '1234', '2002-12-12', 'Jim Carrey', 'Male', 'https://i.pravatar.cc/150?img=24', 'Expert'),
('user25', '1234', '2003-01-01', 'Jennifer Lopez', 'Female', 'https://i.pravatar.cc/150?img=25', 'Beginner'),
('user26', '1234', '2004-02-02', 'Justin Bieber', 'Male', 'https://i.pravatar.cc/150?img=26', 'Intermediate'),
('user27', '1234', '2005-03-03', 'Jon Bon Jovi', 'Male', 'https://i.pravatar.cc/150?img=27', 'Expert'),
('user28', '1234', '2006-04-04', 'Janet Jackson', 'Female', 'https://i.pravatar.cc/150?img=28', 'Beginner'),
('user29', '1234', '2007-05-05', 'Jeff Bridges', 'Male', 'https://i.pravatar.cc/150?img=29', 'Intermediate'),
('user30', '1234', '2008-06-06', 'Joan Jett', 'Female', 'https://i.pravatar.cc/150?img=30', 'Expert'),
('user31', '1234', '2009-07-07', 'John Mayer', 'Male', 'https://i.pravatar.cc/150?img=31', 'Beginner'),
('user32', '1234', '2010-08-08', 'Jessica Simpson', 'Female', 'https://i.pravatar.cc/150?img=32', 'Intermediate'),
('user33', '1234', '1991-09-09', 'Justin Timberlake', 'Male', 'https://i.pravatar.cc/150?img=33', 'Expert'),
('user34', '1234', '1992-10-10', 'Jared Leto', 'Male', 'https://i.pravatar.cc/150?img=34', 'Beginner'),
('user35', '1234', '1993-11-11', 'Jason Statham', 'Male', 'https://i.pravatar.cc/150?img=35', 'Intermediate'),
('user36', '1234', '1994-12-12', 'Julia Stiles', 'Female', 'https://i.pravatar.cc/150?img=36', 'Expert'),
('user37', '1234', '1995-01-01', 'Jeff Goldblum', 'Male', 'https://i.pravatar.cc/150?img=37', 'Beginner'),
('user38', '1234', '1996-02-02', 'Judy Dench', 'Female', 'https://i.pravatar.cc/150?img=38', 'Intermediate'),
('user39', '1234', '1997-03-03', 'James Franco', 'Male', 'https://i.pravatar.cc/150?img=39', 'Expert'),
('user40', '1234', '1998-04-04', 'Joseph Gordon-Levitt', 'Male', 'https://i.pravatar.cc/150?img=40', 'Beginner'),
('user41', '1234', '1999-05-05', 'Jane Krakowski', 'Female', 'https://i.pravatar.cc/150?img=41', 'Intermediate'),
('user42', '1234', '2000-06-06', 'Jack McBrayer', 'Male', 'https://i.pravatar.cc/150?img=42', 'Expert'),
('user43', '1234', '2001-07-07', 'Jake Gyllenhaal', 'Male', 'https://i.pravatar.cc/150?img=43', 'Beginner'),
('user44', '1234', '2002-08-08', 'Joaquin Phoenix', 'Male', 'https://i.pravatar.cc/150?img=44', 'Intermediate'),
('user45', '1234', '2003-09-09', 'Jennifer Lawrence', 'Female', 'https://i.pravatar.cc/150?img=45', 'Expert'),
('user46', '1234', '2004-10-10', 'Josh Brolin', 'Male', 'https://i.pravatar.cc/150?img=46', 'Beginner'),
('user47', '1234', '2005-11-11', 'Jessica Chastain', 'Female', 'https://i.pravatar.cc/150?img=47', 'Intermediate'),
('user48', '1234', '2006-12-12', 'Jesse Eisenberg', 'Male', 'https://i.pravatar.cc/150?img=48', 'Expert'),
('user49', '1234', '2007-01-01', 'Jude Law', 'Male', 'https://i.pravatar.cc/150?img=49', 'Beginner'),
('user50', '1234', '2008-02-02', 'Jackie Chan', 'Male', 'https://i.pravatar.cc/150?img=50', 'Intermediate');

CREATE INDEX idx_user_id ON user(user_id);

CREATE TABLE goal (
   goal_id INT NOT NULL AUTO_INCREMENT,
    goal_content VARCHAR(300),
   goal_userid VARCHAR(200) NOT NULL,
    goal_regdate DATE NOT NULL,
    PRIMARY KEY(goal_id),
     FOREIGN KEY (goal_userid) REFERENCES user(user_id)
);
INSERT INTO goal (goal_content, goal_userid, goal_regdate)
VALUES ('이두', 'KHJ7752', '2024-05-20'),
       ('하체', 'KHJ7752', '2024-05-20'),
       ('클라이밍', 'KHJ7752', '2024-05-21'),
       ('어깨', 'KHJ7752', '2024-05-21'),
       ('농구', 'SSH9023', '2024-05-21'),
       ('스킨스쿠버', 'SSH9023', '2024-05-22'),
       ('헬스장', 'SSH9023', '2024-05-23'),
       ('달리기', 'SSH9023', '2024-05-23'),
       ('맨몸운동', 'KHJ7752', '2024-05-23'),
       ('테니스', 'KHJ7752', '2024-05-24'),
       ('골프', 'SSH9023', '2024-05-24'),
       ('코딩', 'SSH9023', '2024-05-25'),
       ('채점', 'HALEY9503', '2024-05-25');

CREATE TABLE todo (
   todo_id INT NOT NULL AUTO_INCREMENT,
   todo_goalid INT NOT NULL,
    todo_content VARCHAR(300) NOT NULL,
   todo_state BOOLEAN NOT NULL DEFAULT false,
    PRIMARY KEY(todo_id),
    FOREIGN KEY (todo_goalid) REFERENCES goal(goal_id)
);
INSERT INTO todo (todo_goalid, todo_content)
VALUES (1, '바벨 컬'),
       (1, '인클라인 덤벨 컬'),
       (2, '레그 프레스'),
       (2, '스쿼트'),
       (3, '악력기 30회'),
       (3, '클라이밍장 코스 A 성공'),
       (3, '클라이밍 장비 쿠팡 주문하기'),
       (4, '덤벨 레터럴 레이즈'),
       (4, '케이블 로프 페이스 풀'),
       (5, '자유투 연습 30분'),
       (5, '레이업 연습 30번'),
       (6, '배영 연습 10분'),
       (6, '접영 연습 10분'),
       (7, '암컬 4세트'),
       (7, '덤밸 레이즈 3세트'),
       (8, '세계 챔피언 상대로 스파링 1분 버티기'),
       (8, '다음 헤비급 챔피언 달기'),
       (9, '런지 15회 3세트'),
       (9, '팔굽혀펴기 20회 4세트'),
       (10, '플랫 연습하기'),
       (10, '톱 스핀 연습하기'),
       (10, '롱 서브 연습하기'),
       (10, '스파이크 30회'),
	   (11, '라운딩 가기'),
       (11, '홀인원 하기'),
       (12, '백준 골드 1문제'),
       (12, 'SWEA D4 1문제');

CREATE TABLE post (
   post_id INT NOT NULL AUTO_INCREMENT,
    post_userid VARCHAR(200) NOT NULL,
    post_goalid INT NOT NULL,
    post_content VARCHAR(300) NOT NULL,
    post_like INT NOT NULL,
    post_img VARCHAR(300) NOT NULL,
    post_regdate DATE NOT NULL,
    PRIMARY KEY(post_id),
   FOREIGN KEY (post_userid) REFERENCES user(user_id),
    FOREIGN KEY (post_goalid) REFERENCES goal(goal_id)
);
INSERT INTO post (post_userid, post_goalid, post_content, post_like, post_img, post_regdate)
VALUES ('KGC8923', 1, '홈트중', 19, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진1+요가.jpg', '2024-05-21'),
       ('KJH8846', 2, '다음 대회까지 맹연습', 26, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진2+수영.jpg', '2024-05-21'),
       ('KHJ7752', 3, '클라이밍 초보입니당', 4, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진3+클라이밍.jpg', '2024-05-21'),
		('HALEY9503', 8, '내주먹 맛좀 볼래?', 10, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진복싱.jpg', '2024-05-21'),
		('HDH9917', 3, '오늘도 멋진 내자신 내 근육', 55, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진헬스장2.JPG', '2024-05-21'),
       ('PHY9601', 4, '마라톤 15km 완주 처음해봄 ㅎ', 50, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/오운완마라톤.JPG', '2024-05-21'),
       ('SSH9023', 5, '고딩 친구들이랑 오랜만에 농구함', 12, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진4+농구.jpg', '2024-05-21'),
       ('CJW8529', 6, '아침 러닝 5키로', 23, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진6+조깅.jpg', '2024-05-21'),
		('YYJ9378', 6, '배드민턴은 언제나 재미있어 짜릿해', 29, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진진짜배드민턴.JPG', '2024-05-21'),
       ('SSH9023', 5, '스킨 스쿠버 초보 3일차!', 12, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진스킨스쿠버.jpg', '2024-05-22'),
		('LGH9265', 5, '내허리가 장난감이냐?', 99, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진7필라테스.jpg', '2024-05-22'),
       ('KHJ7752', 9, '친구랑 맨몸운동 PT 조진날', 23, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/오운완9sh.jpg', '2024-05-23'),
		('SCY8134', 3, '크로스핏강의 처음나간날 두근두근', 10, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진크로스핏3.jpg', '2024-05-23'),
       ('SSH9023', 5, '2시간 헬스한 날', 0, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진5+헬스장.jpg', '2024-05-23'),
       ('KHJ7752', 10, '테니스 3개월차', 6, 'https://fit-do.s3.ap-southeast-2.amazonaws.com/운동게시물사진테니스.jpg', '2024-05-24'); 

CREATE TABLE comment (
    comment_id INT NOT NULL AUTO_INCREMENT,
    comment_content VARCHAR(200) NOT NULL,
    comment_regdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    comment_userid VARCHAR(200) NOT NULL,
    comment_postid INT NOT NULL,
    PRIMARY KEY(comment_id),
    FOREIGN KEY (comment_userid) REFERENCES user(user_id),
    FOREIGN KEY (comment_postid) REFERENCES post(post_id)
);
INSERT INTO comment (comment_content, comment_regdate, comment_userid, comment_postid)
VALUES ('테니스 좀 치시나요?', '2024-05-23' ,'CJW8529', 15),
	   ('저도 배우는 중입니다!', '2024-05-23' ,'SCY8134', 15),
	   ('라켓 얼마짜린가요?', '2024-05-23' ,'HDH9917', 15),
       ('톱 스핀은 초보자가 하기 어려우니 다른거부터 연습하는걸 추천!', '2024-05-23' ,'YYJ9378', 15),
       ('현재야 담에 한 겜 치자 ㅋㅋ', '2024-05-23' ,'HALEY9503', 15);
INSERT INTO comment (comment_content, comment_regdate, comment_userid, comment_postid)
VALUES ('댓글2', '2024-05-23' ,'KHJ7752', 1);
INSERT INTO comment (comment_content, comment_regdate, comment_userid, comment_postid)
VALUES ('댓글3', '2024-05-23' ,'PHY9601', 1);
INSERT INTO comment (comment_content, comment_regdate, comment_userid, comment_postid)
VALUES ('댓글5', '2024-05-23' ,'CJW8529', 1);

CREATE TABLE follow (
	follow_id INT NOT NULL AUTO_INCREMENT,
	from_userid VARCHAR(200) NOT NULL,
	to_userid VARCHAR(200) NOT NULL,
	PRIMARY KEY(follow_id),
	FOREIGN KEY (from_userid) REFERENCES user(user_id),
    FOREIGN KEY (to_userid) REFERENCES user(user_id)
);
INSERT INTO follow (from_userid, to_userid)
VALUES ('KGC8923', 'KJH8846'),
       ('KHJ7752', 'KGC8923'),
       ('KHJ7752', 'PHY9601'),
       ('KHJ7752', 'CJW8529'),
       ('KHJ7752', 'LGH9265'),
       ('KGC8923', 'CJW8529'),
       ('PHY9601', 'KJH8846'),
       ('KJH8846', 'SSH9023'),
       ('KJH8846', 'KHJ7752'),
       ('SSH9023', 'PHY9601'),
       ('CJW8529', 'KHJ7752'),
       ('CJW8529', 'SSH9023'),
       ('SSH9023', 'KGC8923'),
       ('SSH9023', 'KJH8846'),
       ('SSH9023', 'KHJ7752'),
       ('SSH9023', 'HDH9917'),
       ('SSH9023', 'YYJ9378');
       
CREATE TABLE postlike (
	postlike_id INT NOT NULL AUTO_INCREMENT,
	postlike_userid VARCHAR(200) NOT NULL,
	postlike_postid INT NOT NULL,
	PRIMARY KEY(postlike_id),
	FOREIGN KEY (postlike_userid) REFERENCES user(user_id),
	FOREIGN KEY (postlike_postid) REFERENCES post(post_id)
);
INSERT INTO postlike (postlike_userid, postlike_postid)
VALUES ('KGC8923', 1),
       ('KHJ7752', 1),
       ('KHJ7752', 2),
       ('KGC8923', 2),
       ('PHY9601', 2),
       ('KJH8846', 3),
       ('KJH8846', 4),
       ('SSH9023', 4),
       ('CJW8529', 5),
       ('CJW8529', 6),
       ('SSH9023', 6),
       ('CJW8529', 15),
       ('HALEY9503', 15),
       ('LGH9265', 15),
       ('YYJ9378', 15),
       ('SCY8134', 15),
       ('KJH8846', 15);


DESC user;
DESC goal;
DESC todo;
DESC post;
DESC comment;
DESC follow;
DESC postlike;


SELECT * from user;
SELECT * from goal;
SELECT * from todo;
SELECT * from post;
SELECT * from comment;
SELECT * from follow;
SELECT * from postlike;
