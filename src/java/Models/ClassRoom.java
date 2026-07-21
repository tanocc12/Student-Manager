package Models;

public class ClassRoom {

    private int id;
    private String classCode;
    private String className;

    private int courseId;
    private String courseCode;
    private String courseName;

    private int majorId;
    private String majorCode;
    private String majorName;

    public ClassRoom() {
    }

    public ClassRoom(
            int id,
            String classCode,
            String className,
            int courseId,
            int majorId) {

        this.id = id;
        this.classCode = classCode;
        this.className = className;
        this.courseId = courseId;
        this.majorId = majorId;
    }

    public ClassRoom(
            int id,
            String classCode,
            String className,
            int courseId,
            String courseCode,
            String courseName,
            int majorId,
            String majorCode,
            String majorName) {

        this.id = id;
        this.classCode = classCode;
        this.className = className;
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.majorId = majorId;
        this.majorCode = majorCode;
        this.majorName = majorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}