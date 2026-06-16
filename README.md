# Student Management System

Java Swing 기반 학생 관리 시스템

Java Desktop 환경에서 학생 정보를 관리할 수 있는 CRUD 기반 프로그램입니다.

사용자 입력을 통해 학생 정보를 등록, 조회, 수정, 삭제할 수 있으며  
JDBC를 활용하여 MariaDB 데이터베이스와 연동해 데이터를 관리합니다.

---

## 주요 기능

### 학생 정보 관리

- 학생 정보 조회
- 학생 정보 등록
- 학생 정보 수정
- 학생 정보 삭제

### Database 연동

- MariaDB 연동
- JDBC 기반 데이터 처리
- SQL Query를 활용한 CRUD 구현

### 객체 지향 구조

- 데이터 모델(Student)
- 데이터베이스 처리(DBManager)
- 프로그램 실행(MainApp)

각 기능별 역할을 분리하여 구조화했습니다.

---

## Architecture

프로그램 실행, 데이터 모델, 데이터베이스 처리를 분리한 구조입니다.

```text
                MainApp
                   │
                   │ GUI Event 처리
                   ▼
               DBManager
                   │
          ┌────────┴────────┐
          │                 │
          ▼                 ▼
       JDBC          Student Model
          │
          ▼
       MariaDB
```

---

## 실행 환경

| 구분 | 환경 |
| --- | --- |
| Language | Java |
| GUI Framework | Java Swing |
| Database | MariaDB |
| Connection | JDBC |
| JDK | Java 17+ |

---

## 실행 방법

### 1. Database 설정

MariaDB 실행 후

```sql
DB_초기화.sql
```

파일을 실행하여 데이터베이스 및 테이블을 생성합니다.

---

### 2. JDBC Driver 설정

MariaDB JDBC Driver

```text
mariadb-java-client-3.5.8.jar
```

파일을 프로젝트 Build Path에 추가합니다.

---

### 3. Application 실행

```text
src/MainApp.java 실행
```

---

## Learning

- Java Swing 기반 GUI Application 개발
- JDBC Database 연동 구현
- SQL 기반 CRUD 기능 구현
- 객체 지향 기반 클래스 설계
- Desktop Application 구조 설계

---

## Directory Structure

```text
Student-Management-System

.
├── src
│   ├── MainApp.java
│   │   └── 프로그램 실행 및 GUI 구성
│   │
│   ├── DBManager.java
│   │   └── MariaDB 연결 및 SQL 처리
│   │
│   └── Student.java
│       └── 학생 데이터 Model 클래스
│
├── lib
│   └── mariadb-java-client-3.5.8.jar
│       └── JDBC Driver
│
├── img
│   └── 프로젝트 이미지 및 설계 자료
│
├── DB_초기화.sql
│   └── Database Table 생성 Script
│
└── README.md
```

---

## System Flow

```text
User Input
    │
    ▼
Java Swing UI
    │
    ▼
DBManager
    │
    ▼
JDBC
    │
    ▼
MariaDB
```

---

## Class Responsibility

| Class | Responsibility |
| --- | --- |
| MainApp | GUI 화면 구성 및 사용자 이벤트 처리 |
| DBManager | Database Connection 및 SQL Query 처리 |
| Student | 학생 데이터 관리 Model |

---

## Project Information

| 항목 | 내용 |
| --- | --- |
| Project | Student Management System |
| Type | Desktop Application |
| Version | 1.0.0 |
| Developed | 2026 |

---

## Database Schema

```text
Student Table

+-------------+
| Student     |
+-------------+
| name        |
| id (PK)     |
| dept        |
+-------------+
```

---

## Notes

Java Swing 기반 Desktop Application 구조를 설계하고  
JDBC를 활용한 Database 연동 및 CRUD 기능을 구현했습니다.