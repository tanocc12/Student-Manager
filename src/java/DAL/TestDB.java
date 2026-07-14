package DAL;

public class TestDB {

    public static void main(String[] args) {

        DBContext db = new DBContext();

        if (db.connection != null) {
            System.out.println("SUCCESS");
        } else {
            System.out.println("FAILED");
        }
    }
}