import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    // 데이터베이스 연결 정보
    private static final String URL = "jdbc:mariadb://localhost:3306/schooldb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    // ===== DB 연결 관리 =====

    /**
     * 데이터베이스 연결 객체 생성
     * @return Connection 객체
     * @throws SQLException DB 연결 실패 시
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ===== READ (조회) =====

    /**
     * 모든 학생 정보 조회
     * @return 학생 List
     */
    public static List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT name, id, dept FROM student";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String id = rs.getString("id");
                String dept = rs.getString("dept");
                students.add(new Student(id, name, dept));
            }
        } catch (SQLException e) {
            System.out.println("학생 조회 오류: " + e.getMessage());
        }

        return students;
    }

    // ===== CREATE (추가) =====

    /**
     * 새 학생 정보 추가
     * @param student 추가할 학생 객체
     * @return 성공 여부 (true: 성공, false: 실패)
     */
    public static boolean insertStudent(Student student) {
        String sql = "INSERT INTO student (name, id, dept) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getId());
            pstmt.setString(3, student.getDept());
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("학생 추가 오류: " + e.getMessage());
            return false;
        }
    }

    // ===== UPDATE (수정) =====

    /**
     * 학생 정보 수정 (학번 기준)
     * @param student 수정할 학생 객체 (id: WHERE 조건)
     * @return 성공 여부 (true: 성공, false: 실패)
     */
    public static boolean updateStudent(Student student) {
        String sql = "UPDATE student SET name = ?, dept = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getDept());
            pstmt.setString(3, student.getId());
            int result = pstmt.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("학생 수정 오류: " + e.getMessage());
            return false;
        }
    }

    // ===== DELETE (삭제) =====

    /**
     * 학생 정보 삭제 (학번 기준)
     * @param id 삭제할 학생의 학번
     * @return 성공 여부 (true: 성공, false: 실패)
     */
    public static boolean deleteStudent(String id) {
        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);

            int result = pstmt.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("학생 삭제 오류: " + e.getMessage());
            return false;
        }
    }

    // ===== 유틸리티 메서드 =====

    /**
     * DB 연결 테스트
     * @return 연결 성공 여부
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null;
        } catch (SQLException e) {
            System.out.println("DB 연결 테스트 실패: " + e.getMessage());
            return false;
        }
    }

    /**
     * JDBC 드라이버 로드
     */
    public static void loadDriver() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패: " + e.getMessage());
        }
    }
}
