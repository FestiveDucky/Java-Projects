import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

class Test {

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://api.slothpixel.me/api/players/asjkdhfgaksjhdf");
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("User-Agent", "UCSB/1.0");
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        System.out.println(reader);
    }


}
