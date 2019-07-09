import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Michael Chunko
 * @since 2019-07-09
 */
public class LCCN {
    public static void main(String[] args) throws IOException {
        // The URL to be searched
        URL url = new URL("https://catalog.loc.gov/vwebv/search?searchCode=TALL&searchArg=hop+on+pop&searchType=1");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            // Find the bibId of each search result and reconstruct its URL based on it
            if (inputLine.contains("bibId") && !inputLine.contains("Links available")) {
                String bibId = inputLine.substring(inputLine.indexOf("bibId=") + 6, inputLine.indexOf("\">"));
                URL bib_url = new URL("https://catalog.loc.gov/vwebv/holdingsInfo?bibId=" + bibId);
                BufferedReader bib_in = new BufferedReader(new InputStreamReader(bib_url.openStream()));
                String bib_inputLine;

                // Find the author, LCCN, and title of each search result
                while ((bib_inputLine = bib_in.readLine()) != null) {
                    if (bib_inputLine.contains("Personal name")) {
                        bib_in.readLine(); // Skip 3 lines from the identifying HTML element
                        bib_in.readLine();
                        bib_inputLine = bib_in.readLine();
                        System.out.println("Author:" + bib_inputLine.trim());
                    }
                    if (bib_inputLine.contains("CALL NUMBER</h3>")) {
                        bib_in.readLine(); // Skip 3 lines from the identifying HTML element
                        bib_in.readLine();
                        bib_inputLine = bib_in.readLine();
                        System.out.println("LCCN:" + bib_inputLine.trim());
                    }
                    if (bib_inputLine.contains("small class"))
                        System.out.println("Title:" + bib_inputLine.trim());
                }
            }
        }
    }
}
