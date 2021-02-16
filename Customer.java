import java.util.*;

public class Customer {
    int id;
    // <movieID: rating >
    Map<Integer, Rating> movieList;

    // create customer
    public Customer(int id) {
        this.id = id;
        movieList = new HashMap<>();
    }
    // add movie review
    public void rateMovie(int movieID, int rating, String date) {
        Rating r = new Rating(rating, date);
        movieList.put(movieID,r);
    }
}
