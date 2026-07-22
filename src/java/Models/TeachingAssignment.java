package Models;

public class TeachingAssignment {

    private int id;
    private int teacherId;
    private int classId;
    private int courseId;
    private int semesterId;

    private String teacherCode;
    private String teacherName;

    private String classCode;
    private String className;

    private String courseCode;
    private String courseName;

    private String semesterName;
    private String schoolYear;

    public TeachingAssignment() {
    }

    public TeachingAssignment(int id,
            int teacherId,
            int classId,
            int courseId,
            int semesterId) {

        this.id = id;
        this.teacherId = teacherId;
        this.classId = classId;
        this.courseId = courseId;
        this.semesterId = semesterId;
    }

    public TeachingAssignment(int id,
            int teacherId,
            int classId,
            int courseId,
            int semesterId,
            String teacherCode,
            String teacherName,
            String classCode,
            String className,
            String courseCode,
            String courseName,
            String semesterName,
            String schoolYear) {

        this.id = id;
        this.teacherId = teacherId;
        this.classId = classId;
        this.courseId = courseId;
        this.semesterId = semesterId;
        this.teacherCode = teacherCode;
        this.teacherName = teacherName;
        this.classCode = classCode;
        this.className = className;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semesterName = semesterName;
        this.schoolYear = schoolYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
}