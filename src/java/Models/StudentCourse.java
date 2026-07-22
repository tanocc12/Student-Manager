package Models;

public class StudentCourse {

    // StudentCourses table
    private int id;
    private int studentId;
    private int classId;
    private int teachingAssignmentId;

    // Student information
    private String studentCode;
    private String studentName;

    // Class information
    private String classCode;
    private String className;

    // Course information
    private String courseCode;
    private String courseName;

    // Teacher information
    private String teacherCode;
    private String teacherName;

    // Semester information
    private String semesterName;
    private String schoolYear;

    public StudentCourse() {
    }

    // Used when inserting a new enrollment
    public StudentCourse(int studentId,
            int classId,
            int teachingAssignmentId) {

        this.studentId = studentId;
        this.classId = classId;
        this.teachingAssignmentId = teachingAssignmentId;
    }

    // Used for basic StudentCourses data
    public StudentCourse(int id,
            int studentId,
            int classId,
            int teachingAssignmentId) {

        this.id = id;
        this.studentId = studentId;
        this.classId = classId;
        this.teachingAssignmentId = teachingAssignmentId;
    }

    // Used when selecting joined enrollment information
    public StudentCourse(int id,
            int studentId,
            int classId,
            int teachingAssignmentId,
            String studentCode,
            String studentName,
            String classCode,
            String className,
            String courseCode,
            String courseName,
            String teacherCode,
            String teacherName,
            String semesterName,
            String schoolYear) {

        this.id = id;
        this.studentId = studentId;
        this.classId = classId;
        this.teachingAssignmentId = teachingAssignmentId;
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.classCode = classCode;
        this.className = className;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.teacherCode = teacherCode;
        this.teacherName = teacherName;
        this.semesterName = semesterName;
        this.schoolYear = schoolYear;
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

    public int getTeachingAssignmentId() {
        return teachingAssignmentId;
    }

    public void setTeachingAssignmentId(int teachingAssignmentId) {
        this.teachingAssignmentId = teachingAssignmentId;
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