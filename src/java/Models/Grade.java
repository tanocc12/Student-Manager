package Models;

public class Grade {

    private int id;
    private int studentCourseId;
    private int studentId;

    private String studentCode;
    private String studentName;

    private Double assignment;
    private Double practicalExam;
    private Double finalExam;
    private Double average;

    public Grade() {
    }

    public Grade(
            int id,
            int studentCourseId,
            int studentId,
            String studentCode,
            String studentName,
            Double assignment,
            Double practicalExam,
            Double finalExam,
            Double average) {

        this.id = id;
        this.studentCourseId = studentCourseId;
        this.studentId = studentId;
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.assignment = assignment;
        this.practicalExam = practicalExam;
        this.finalExam = finalExam;
        this.average = average;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentCourseId() {
        return studentCourseId;
    }

    public void setStudentCourseId(int studentCourseId) {
        this.studentCourseId = studentCourseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Double getAssignment() {
        return assignment;
    }

    public void setAssignment(Double assignment) {
        this.assignment = assignment;
    }

    public Double getPracticalExam() {
        return practicalExam;
    }

    public void setPracticalExam(Double practicalExam) {
        this.practicalExam = practicalExam;
    }

    public Double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(Double finalExam) {
        this.finalExam = finalExam;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }
}