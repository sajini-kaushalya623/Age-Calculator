// GradeCalculatorGUI.java
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GradeCalculatorGUI extends JFrame {
    private GradeCalculator calculator;

    // GUI Components
    private JTextField nameField, idField;
    private JSpinner subjectCountSpinner;
    private JPanel marksPanel;
    private ArrayList<JTextField> markFields;
    private JTextArea resultArea;
    private JList<Student> studentList;
    private DefaultListModel<Student> listModel;

    // Buttons
    private JButton createFieldsBtn, calculateBtn, addStudentBtn,
            clearBtn, showStatsBtn, removeStudentBtn;

    public GradeCalculatorGUI() {
        calculator = new GradeCalculator();
        markFields = new ArrayList<>();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setFrameProperties();
    }

    private void initializeComponents() {
        // Input components
        nameField = new JTextField(15);
        idField = new JTextField(15);
        subjectCountSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 15, 1));

        // Marks panel
        marksPanel = new JPanel();
        marksPanel.setLayout(new GridLayout(0, 2, 5, 5));
        marksPanel.setBorder(new TitledBorder("Subject Marks (0-100)"));

        // Result area
        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        resultArea.setBorder(BorderFactory.createLoweredBevelBorder());

        // Student list
        listModel = new DefaultListModel<>();
        studentList = new JList<>(listModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.setBorder(BorderFactory.createTitledBorder("Students"));

        // Buttons
        createFieldsBtn = new JButton("Create Mark Fields");
        calculateBtn = new JButton("Calculate Grade");
        addStudentBtn = new JButton("Add Student");
        clearBtn = new JButton("Clear Fields");
        showStatsBtn = new JButton("Show Statistics");
        removeStudentBtn = new JButton("Remove Selected");

        // Initially disable some buttons
        calculateBtn.setEnabled(false);
        addStudentBtn.setEnabled(false);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel - Student info
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));

        // Student info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(new TitledBorder("Student Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        infoPanel.add(new JLabel("Student Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        infoPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        infoPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        infoPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        infoPanel.add(new JLabel("Number of Subjects:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        infoPanel.add(subjectCountSpinner, gbc);

        topPanel.add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel1 = new JPanel(new FlowLayout());
        buttonPanel1.add(createFieldsBtn);
        buttonPanel1.add(clearBtn);
        topPanel.add(buttonPanel1, BorderLayout.SOUTH);

        // Center panel - Marks and Results
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Marks scroll pane
        JScrollPane marksScroll = new JScrollPane(marksPanel);
        marksScroll.setPreferredSize(new Dimension(300, 200));
        marksScroll.setBorder(new TitledBorder("Enter Marks"));

        // Results scroll pane
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(new TitledBorder("Results"));

        centerPanel.add(marksScroll, BorderLayout.WEST);
        centerPanel.add(resultScroll, BorderLayout.CENTER);

        // Right panel - Student list and actions
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));

        JScrollPane listScroll = new JScrollPane(studentList);
        listScroll.setPreferredSize(new Dimension(200, 200));
        rightPanel.add(listScroll, BorderLayout.CENTER);

        JPanel buttonPanel2 = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel2.add(calculateBtn);
        buttonPanel2.add(addStudentBtn);
        buttonPanel2.add(removeStudentBtn);
        buttonPanel2.add(showStatsBtn);
        rightPanel.add(buttonPanel2, BorderLayout.SOUTH);

        centerPanel.add(rightPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void setupEventHandlers() {
        createFieldsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMarkFields();
            }
        });

        calculateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateGrade();
            }
        });

        addStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudentToList();
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        showStatsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStatistics();
            }
        });

        removeStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedStudent();
            }
        });

        studentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedStudent();
            }
        });
    }

    private void createMarkFields() {
        int subjectCount = (Integer) subjectCountSpinner.getValue();

        marksPanel.removeAll();
        markFields.clear();

        marksPanel.setLayout(new GridLayout(subjectCount, 2, 5, 5));

        for (int i = 1; i <= subjectCount; i++) {
            JLabel label = new JLabel("Subject " + i + ":");
            JTextField markField = new JTextField(8);

            marksPanel.add(label);
            marksPanel.add(markField);
            markFields.add(markField);
        }

        calculateBtn.setEnabled(true);

        marksPanel.revalidate();
        marksPanel.repaint();

        JOptionPane.showMessageDialog(this,
                "Mark fields created! Enter marks (0-100) for each subject.",
                "Fields Created", JOptionPane.INFORMATION_MESSAGE);
    }

    private void calculateGrade() {
        try {
            // Validate input
            if (!validateInput()) {
                return;
            }

            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            int subjectCount = markFields.size();

            Student student = new Student(name, id, subjectCount);

            // Set marks
            for (int i = 0; i < markFields.size(); i++) {
                double mark = Double.parseDouble(markFields.get(i).getText().trim());
                student.setMark(i, mark);
            }

            // Display results
            displayResults(student);
            addStudentBtn.setEnabled(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for all marks!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayResults(Student student) {
        StringBuilder result = new StringBuilder();
        result.append("GRADE CALCULATION RESULTS\n");
        result.append("========================\n\n");
        result.append("Student Name: ").append(student.getName()).append("\n");
        result.append("Student ID: ").append(student.getStudentId()).append("\n\n");

        result.append("SUBJECT MARKS:\n");
        double[] marks = student.getMarks();
        for (int i = 0; i < marks.length; i++) {
            result.append("Subject ").append(i + 1).append(": ")
                    .append(String.format("%.1f", marks[i])).append("\n");
        }

        result.append("\n");
        result.append("Average: ").append(String.format("%.2f", student.calculateAverage())).append("\n");
        result.append("Grade: ").append(student.calculateGrade()).append("\n");
        result.append("Performance: ").append(student.getGradeDescription()).append("\n");

        resultArea.setText(result.toString());
    }

    private boolean validateInput() {
        // Check name
        if (!calculator.isValidName(nameField.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid student name!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }

        // Check ID
        if (!calculator.isValidStudentId(idField.getText())) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid student ID!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            idField.requestFocus();
            return false;
        }

        // Check if fields are created
        if (markFields.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please create mark fields first!",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check marks
        for (int i = 0; i < markFields.size(); i++) {
            try {
                String markText = markFields.get(i).getText().trim();
                if (markText.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please fill in all mark fields!",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    markFields.get(i).requestFocus();
                    return false;
                }

                double mark = Double.parseDouble(markText);
                if (!calculator.isValidMark(mark)) {
                    JOptionPane.showMessageDialog(this,
                            "Mark for Subject " + (i + 1) + " must be between 0 and 100!",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    markFields.get(i).requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for Subject " + (i + 1) + "!",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                markFields.get(i).requestFocus();
                return false;
            }
        }

        return true;
    }

    private void addStudentToList() {
        try {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            int subjectCount = markFields.size();

            // Check if student ID already exists
            if (calculator.findStudentById(id) != null) {
                JOptionPane.showMessageDialog(this,
                        "A student with ID '" + id + "' already exists!",
                        "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Student student = new Student(name, id, subjectCount);

            for (int i = 0; i < markFields.size(); i++) {
                double mark = Double.parseDouble(markFields.get(i).getText().trim());
                student.setMark(i, mark);
            }

            calculator.addStudent(student);
            listModel.addElement(student);

            JOptionPane.showMessageDialog(this,
                    "Student added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error adding student: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        idField.setText("");
        subjectCountSpinner.setValue(5);

        for (JTextField field : markFields) {
            field.setText("");
        }

        resultArea.setText("");
        addStudentBtn.setEnabled(false);

        nameField.requestFocus();
    }

    private void showStatistics() {
        if (calculator.getStudents().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No students data available for statistics!",
                    "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder stats = new StringBuilder();
        stats.append("CLASS STATISTICS\n");
        stats.append("================\n\n");
        stats.append("Total Students: ").append(calculator.getStudents().size()).append("\n");
        stats.append("Class Average: ").append(String.format("%.2f", calculator.getClassAverage())).append("\n\n");

        Student highest = calculator.getHighestPerformingStudent();
        Student lowest = calculator.getLowestPerformingStudent();

        if (highest != null) {
            stats.append("Highest Performer:\n");
            stats.append("  Name: ").append(highest.getName()).append("\n");
            stats.append("  Average: ").append(String.format("%.2f", highest.calculateAverage())).append("\n");
            stats.append("  Grade: ").append(highest.calculateGrade()).append("\n\n");
        }

        if (lowest != null) {
            stats.append("Lowest Performer:\n");
            stats.append("  Name: ").append(lowest.getName()).append("\n");
            stats.append("  Average: ").append(String.format("%.2f", lowest.calculateAverage())).append("\n");
            stats.append("  Grade: ").append(lowest.calculateGrade()).append("\n\n");
        }

        stats.append(calculator.getGradeDistribution());

        // Display in a new dialog
        JTextArea statsArea = new JTextArea(stats.toString());
        statsArea.setEditable(false);
        statsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(statsArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(this, scrollPane,
                "Class Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeSelectedStudent() {
        Student selected = studentList.getSelectedValue();
        if (selected != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to remove " + selected.getName() + "?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                calculator.removeStudent(selected);
                listModel.removeElement(selected);
                clearFields();
                JOptionPane.showMessageDialog(this,
                        "Student removed successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please select a student to remove!",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void loadSelectedStudent() {
        Student selected = studentList.getSelectedValue();
        if (selected != null) {
            nameField.setText(selected.getName());
            idField.setText(selected.getStudentId());
            subjectCountSpinner.setValue(selected.getSubjectCount());

            // Create fields and populate marks
            createMarkFields();

            double[] marks = selected.getMarks();
            for (int i = 0; i < marks.length && i < markFields.size(); i++) {
                markFields.get(i).setText(String.valueOf(marks[i]));
            }

            displayResults(selected);
        }
    }

    private void setFrameProperties() {
        setTitle("Student Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        pack();
        setLocationRelativeTo(null);

        // Set minimum size
        Dimension minSize = new Dimension(900, 600);
        setMinimumSize(minSize);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeCalculatorGUI().setVisible(true);
            }
        });
    }
}