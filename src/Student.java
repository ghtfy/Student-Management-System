public class Student {
    // 멤버 변수
    private String id;      // 학번 (기본키)
    private String name;    // 이름
    private String dept;    // 학과

    // ===== 생성자 =====
    
    /**
     * 기본 생성자
     */
    public Student() {
        this.id = "";
        this.name = "";
        this.dept = "";
    }

    /**
     * 매개변수 생성자
     * @param id   학번
     * @param name 이름
     * @param dept 학과
     */
    public Student(String id, String name, String dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }

    // ===== Getter 메서드 =====

    /**
     * 학번 조회
     * @return 학번
     */
    public String getId() {
        return id;
    }

    /**
     * 이름 조회
     * @return 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 학과 조회
     * @return 학과
     */
    public String getDept() {
        return dept;
    }

    // ===== Setter 메서드 =====

    /**
     * 학번 설정
     * @param id 학번
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 이름 설정
     * @param name 이름
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 학과 설정
     * @param dept 학과
     */
    public void setDept(String dept) {
        this.dept = dept;
    }

    // ===== toString 메서드 =====

    /**
     * 학생 정보 문자열로 변환
     * @return 학생 정보 (이름|학번|학과)
     */
    @Override
    public String toString() {
        return name + " | " + id + " | " + dept;
    }

    /**
     * 디버깅용 상세 문자열 변환
     * @return Student{id='...', name='...', dept='...'}
     */
    public String toDetailString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                '}';
    }
}
