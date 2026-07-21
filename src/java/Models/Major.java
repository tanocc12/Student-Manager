package Models;

public class Major {

    private int id;
    private String majorCode;
    private String majorName;

    public Major() {
    }

    public Major(int id, String majorCode, String majorName) {
        this.id = id;
        this.majorCode = majorCode;
        this.majorName = majorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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