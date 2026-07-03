
package Models;

public class ClassRoom {
    private int id;
    private String classCode;
    private String className;
    private int majorId;
    private String majorName; 

    public ClassRoom() {}

    public ClassRoom(int id, String classCode, String className, int majorId) {
        this.id = id;
        this.classCode = classCode;
        this.className = className;
        this.majorId = majorId;
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public int getMajorId() { return majorId; }
    public void setMajorId(int majorId) { this.majorId = majorId; }

    public String getMajorName() { return majorName; }
    public void setMajorName(String majorName) { this.majorName = majorName; }
}