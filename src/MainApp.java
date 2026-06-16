import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainApp extends JFrame {
    // UI 컴포넌트 변수
    private JTextField txtName, txtId, txtDept;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnCreate, btnUpdate, btnDelete, btnClear;


    public MainApp() {
        // DB 드라이버 로드
        DBManager.loadDriver();

        // 1. 창 기본 설정
        setTitle("대학생 학적 관리 시스템 (CRUD)");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙 배치
        setLayout(new BorderLayout(10, 10));

        // 2. 상단 입력 폼 레이아웃
        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBorder(BorderFactory.createTitledBorder("학생 정보 입력"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; pnlInput.add(new JLabel("이름:"), gbc);
        gbc.gridx = 1; txtName = new JTextField(10); pnlInput.add(txtName, gbc);

        gbc.gridx = 2; pnlInput.add(new JLabel("학번(PK):"), gbc);
        gbc.gridx = 3; txtId = new JTextField(10); pnlInput.add(txtId, gbc);

        gbc.gridx = 4; pnlInput.add(new JLabel("학과:"), gbc);
        gbc.gridx = 5; txtDept = new JTextField(12); pnlInput.add(txtDept, gbc);

        add(pnlInput, BorderLayout.NORTH);

        // 3. 중앙 테이블 레이아웃
        String[] columnNames = {"이름", "학번", "학과"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("학생 명단 목록 [Read]"));
        add(scrollPane, BorderLayout.CENTER);

        // 4. 하단 버튼 레이아웃 (CRUD 기능)
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnCreate = new JButton("등록 (Create)");
        btnUpdate = new JButton("수정 (Update)");
        btnDelete = new JButton("삭제 (Delete)");
        btnClear  = new JButton("비우기");

        pnlButtons.add(btnCreate);
        pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnClear);
        add(pnlButtons, BorderLayout.SOUTH);

        // 5. 이벤트 리스너 연결
        btnCreate.addActionListener(e -> createStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());

        // 행 클릭 시 입력창에 자동 완성
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow != -1) {
                    txtName.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtId.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtDept.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtId.setEditable(false); // 학번은 수정 불가
                }
            }
        });

        // 켜지자마자 데이터 읽어오기
        refreshTable();
    }

    // ===== 테이블 새로고침 =====

    private void refreshTable() {
        tableModel.setRowCount(0); // 기존 행 제거
        List<Student> students = DBManager.getStudents();

        // 각 학생을 테이블 행으로 추가
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                student.getName(),
                student.getId(),
                student.getDept()
            });
        }
    }

    private void createStudent() {
        String name = txtName.getText().trim();
        String id = txtId.getText().trim();
        String dept = txtDept.getText().trim();

        // 유효성 검사
        if (name.isEmpty() || id.isEmpty() || dept.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.");
            return;
        }

        // Student 객체 생성
        Student student = new Student(id, name, dept);

        // DBManager를 통해 DB에 추가
        if (DBManager.insertStudent(student)) {
            JOptionPane.showMessageDialog(this, "성공적으로 등록되었습니다.");
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "등록 실패: 이미 존재하는 학번이거나 DB 오류입니다.");
        }
    }

    // ===== [R] READ - 학생 데이터 조회 =====
    // refreshTable() 메서드 참고

    // ===== [U] UPDATE - 학생 데이터 수정 =====

    /**
     * 선택한 학생의 정보를 수정
     */
    private void updateStudent() {
        String name = txtName.getText().trim();
        String id = txtId.getText().trim();
        String dept = txtDept.getText().trim();

        // Student 객체 생성
        Student student = new Student(id, name, dept);

        // DBManager를 통해 DB에서 수정
        if (DBManager.updateStudent(student)) {
            JOptionPane.showMessageDialog(this, "정보가 성공적으로 수정되었습니다.");
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "수정할 대상을 찾지 못했습니다.");
        }
    }

    // ===== DELETE - 학생 데이터 삭제 =====

    /**
     * 선택한 학생 정보 삭제
     */
    private void deleteStudent() {
        String id = txtId.getText().trim();
        if(id.isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(this, "정말로 이 학생을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // DBManager를 통해 DB에서 삭제
        if (DBManager.deleteStudent(id)) {
            JOptionPane.showMessageDialog(this, "삭제 완료되었습니다.");
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "삭제 실패: DB 오류입니다.");
        }
    }

    // ===== 입력 필드 초기화 =====

    /**
     * 모든 입력 필드를 초기화
     */
    private void clearFields() {
        txtName.setText("");
        txtId.setText("");
        txtDept.setText("");
        txtId.setEditable(true); // 학번 필드 수정 가능하게 복구
        table.clearSelection();
    }

    // ===== main 메서드 =====

    /**
     * 프로그램 진입점
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}