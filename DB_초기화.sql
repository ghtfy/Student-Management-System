CREATE DATABASE IF NOT EXISTS schooldb
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- 데이터베이스 선택
USE schooldb;

--  기존 테이블 삭제 
DROP TABLE IF EXISTS student;

--  테이블 생성
CREATE TABLE student (
    name VARCHAR(50) NOT NULL COMMENT '학생 이름',
    id VARCHAR(20) PRIMARY KEY NOT NULL COMMENT '학번 (기본키)',
    dept VARCHAR(50) NOT NULL COMMENT '학과'
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='학생 정보 관리 테이블';


INSERT INTO student (name, id, dept) VALUES
('손흥민', '2400001', '컴퓨터공학과'),
('김민재', '2400002', '정보보호학과'),
('홀란드', '2400003', '정보통신학과'),
('음바페', '2400004', '산업경영학과'),
('제라드', '2400005', '서양조각학과'),
('호날두', '2400006', '문화예술학과');

--데이터 확인
SELECT COUNT(*) as '전체 학생 수' FROM student;
SELECT * FROM student;