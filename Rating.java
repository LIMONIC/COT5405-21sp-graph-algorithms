import java.io.IOException;

public class Rating {
    int val;
    int year;
    int month;
    int day;

    public Rating(int val, String date){
        this.val = val;
        String[] d = date.split("-");
        try {
            if (d.length != 3){
                throw new Exception();
            }
            if (d[0].length() != 4 || d[1].length() != 2 || d[2].length() != 2) {
                throw new Exception();
            }

            int year = Integer.parseInt(d[0]);
            int month = Integer.parseInt(d[1]);
            int day = Integer.parseInt(d[2]);

            if (month < 1 && month > 12) {
                throw new Exception();
            }
            if (day < 1 && day > 31) {
                throw new Exception();
            }

            this.year = year;
            this.month = month;
            this.day = day;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
