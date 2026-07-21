package Models;

import java.sql.Date;

public class Student {

    // Thuộc bảng Students
    private int id;
    private String studentCode;
    private int userId;
    private int classId;
    private int majorId;
    private String address;
    private String status;

    // Lấy từ bảng Users
    private String username;
    private String fullName;
    private String email;
    private String gender;
    private Date dob;
    private String phone;

    // Lấy từ bảng Classes
    private String classCode;
    private String className;

    // Lấy từ bảng Majors
    private String majorCode;
    private String majorName;

    // Lấy gián tiếp từ Classes → Courses
    private int courseId;
    private String courseCode;
    private String courseName;

    public Student() {
    }

    public Student(int id, String studentCode, int userId,
            int classId, int majorId, String address, String status) {
        this.id = id;
        this.studentCode = studentCode;
        this.userId = userId;
        this.classId = classId;
        this.majorId = majorId;
        this.address = address;
        this.status = status;
    }

    public Student(int id, String studentCode, int userId,
            int classId, int majorId, String address, String status,
            String username, String fullName, String email,
            String gender, Date dob, String phone,
            String classCode, String className,
            String majorCode, String majorName,
            int courseId, String courseCode, String courseName) {

        this.id = id;
        this.studentCode = studentCode;
        this.userId = userId;
        this.classId = classId;
        this.majorId = majorId;
        this.address = address;
        this.status = status;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.classCode = classCode;
        this.className = className;
        this.majorCode = majorCode;
        this.majorName = majorName;
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}