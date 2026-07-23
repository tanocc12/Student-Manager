package Models;

public class Semester {

    private int id;
    private String semesterName;
    private String schoolYear;

    public Semester() {
    }

    public Semester(
            int id,
            String semesterName,
            String schoolYear) {

        this.id = id;
        this.semesterName = semesterName;
        this.schoolYear = schoolYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return semesterName + " - " + schoolYear;
    }
}