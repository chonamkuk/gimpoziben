import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Test {

    public static void main(String[] args) {
        System.out.println(DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now()));
    }
}
