package Models;

public class Teacher {

    private int id;
    private String teacherCode;
    private int userId;
    private String status;

    // Dữ liệu lấy từ bảng Users qua JOIN
    private String username;
    private String email;
    private String fullName;
    private String gender;
    private String dob;
    private String phone;

    public Teacher() {
    }

    // Dùng khi thêm Teacher
    public Teacher(
            String teacherCode,
            int userId,
            String status) {

        this.teacherCode = teacherCode;
        this.userId = userId;
        this.status = status;
    }

    // Dùng khi update hoặc dữ liệu cơ bản
    public Teacher(
            int id,
            String teacherCode,
            int userId,
            String status) {

        this.id = id;
        this.teacherCode = teacherCode;
        this.userId = userId;
        this.status = status;
    }

    // Dùng khi SELECT JOIN Users
    public Teacher(
            int id,
            String teacherCode,
            int userId,
            String status,
            String username,
            String email,
            String fullName,
            String gender,
            String dob,
            String phone) {

        this.id = id;
        this.teacherCode = teacherCode;
        this.userId = userId;
        this.status = status;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}