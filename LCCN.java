import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class LCCN {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://catalog.loc.gov/vwebv/search?searchCode=TALL&searchArg=hop+on+pop&searchType=1");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }
    }
}
