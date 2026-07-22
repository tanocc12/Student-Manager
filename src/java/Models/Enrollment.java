package Models;

import java.sql.Date;

public class Enrollment {

    // Dữ liệu chính trong bảng Enrollments
    private int id;
    private int studentId;
    private int classId;
    private Date enrollmentDate;
    private String status;

    // Dữ liệu dùng để hiển thị sau khi JOIN
    private String studentCode;
    private String studentName;

    private String classCode;
    private String className;

    private int courseId;
    private String courseCode;
    private String courseName;

    private int majorId;
    private String majorCode;
    private String majorName;

    public Enrollment() {
    }

    public Enrollment(
            int studentId,
            int classId,
            Date enrollmentDate,
            String status) {

        this.studentId = studentId;
        this.classId = classId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public Enrollment(
            int id,
            int studentId,
            int classId,
            Date enrollmentDate,
            String status) {

        this.id = id;
        this.studentId = studentId;
        this.classId = classId;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }


    public Enrollment(
            int id,
            int studentId,
            String studentCode,
            String studentName,
            int classId,
            String classCode,
            String className,
            int courseId,
            String courseCode,
            String courseName,
            int majorId,
            String majorCode,
            String majorName,
            Date enrollmentDate,
            String status) {

        this.id = id;

        this.studentId = studentId;
        this.studentCode = studentCode;
        this.studentName = studentName;

        this.classId = classId;
        this.classCode = classCode;
        this.className = className;

        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;

        this.majorId = majorId;
        this.majorCode = majorCode;
        this.majorName = majorName;

        this.enrollmentDate = enrollmentDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    @Override
    public String toString() {
        return "Enrollment{"
                + "id=" + id
                + ", studentId=" + studentId
                + ", studentCode='" + studentCode + '\''
                + ", studentName='" + studentName + '\''
                + ", classId=" + classId
                + ", classCode='" + classCode + '\''
                + ", className='" + className + '\''
                + ", courseId=" + courseId
                + ", courseCode='" + courseCode + '\''
                + ", courseName='" + courseName + '\''
                + ", majorId=" + majorId
                + ", majorCode='" + majorCode + '\''
                + ", majorName='" + majorName + '\''
                + ", enrollmentDate=" + enrollmentDate
                + ", status='" + status + '\''
                + '}';
    }
}