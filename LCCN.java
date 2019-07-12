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
        String search_query;

        // No arguments were given
        try {
            search_query = args[0];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Please provide the search query as an argument");
        }

        // The URL to be searched
        search_query = search_query.replaceAll(" ", "+");
        URL url = new URL("https://catalog.loc.gov/vwebv/search?searchCode=TALL&searchArg=" + search_query + "&searchType=1");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        boolean single_result = false;

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("in use")) {
                System.out.println("All Library of Congress connections are currently in use");
                break;
            }

            // The search yielded only one result
            if (inputLine.contains("Permalink")) {
                single_result = true;
                break;
            }

            // Find the bibId of each search result and reconstruct its URL based on it
            if (inputLine.contains("bibId") && !inputLine.contains("Links available")) {
                String bibId = inputLine.substring(inputLine.indexOf("bibId=") + 6, inputLine.indexOf("\">"));
                URL bib_url = new URL("https://catalog.loc.gov/vwebv/holdingsInfo?bibId=" + bibId);
                BufferedReader bib_in = new BufferedReader(new InputStreamReader(bib_url.openStream()));
                String bib_inputLine;

                String call_number = "", author = "", title = "";

                // Find the author, LCCN, and title of each search result
                while ((bib_inputLine = bib_in.readLine()) != null) {
                    if (bib_inputLine.contains("Personal name")) {
                        bib_in.readLine(); // Skip 3 lines from the identifying HTML element
                        bib_in.readLine();
                        bib_inputLine = bib_in.readLine();
                        author = bib_inputLine.trim();
                    }
                    if (bib_inputLine.contains("LC classification</h3>")) {
                        bib_in.readLine(); // Skip 3 lines from the identifying HTML element
                        bib_in.readLine();
                        bib_inputLine = bib_in.readLine();

                        call_number = bib_inputLine.trim();
                    }
                    if (bib_inputLine.contains("small class"))
                        title = bib_inputLine.trim();
                }

                // Trim call_number, author, and title to be only the relevant data

                // Trimming is not necessary in the case of no call number
                if (call_number.equals(""))
                    call_number = "Not Available";
                else
                    call_number = call_number.substring(call_number.indexOf("<span>") + 17, call_number.indexOf("</span>"));

                // Trimming is not necessary in the case of no author
                if (author.equals(""))
                    author = "None Listed";
                else
                    author = author.substring(author.indexOf("<span") + 16, author.indexOf("</span>"));

                title = title.substring(title.indexOf("</small>") + 8, title.indexOf("</h1>"));

                System.out.println("lccn:[" + call_number + "], author:[" + author + "], title:[" + title + "]");
            }
        }
        if (single_result) {
            String call_number = "", author = "", title = "";

            // Find the author, LCCN, and title of each search result
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("Personal name")) {
                    in.readLine(); // Skip 3 lines from the identifying HTML element
                    in.readLine();
                    inputLine = in.readLine();
                    author = inputLine.trim();
                }
                if (inputLine.contains("LC classification</h3>")) {
                    in.readLine(); // Skip 3 lines from the identifying HTML element
                    in.readLine();
                    inputLine = in.readLine();

                    call_number = inputLine.trim();
                }
                if (inputLine.contains("small class"))
                    title = inputLine.trim();
            }

            // Trim call_number, author, and title to be only the relevant data

            // Trimming is not necessary in the case of no call number
            if (call_number.equals(""))
                call_number = "Not Available";
            else
                call_number = call_number.substring(call_number.indexOf("<span>") + 17, call_number.indexOf("</span>"));

            // Trimming is not necessary in the case of no author
            if (author.equals(""))
                author = "None Listed";
            else
                author = author.substring(author.indexOf("<span") + 16, author.indexOf("</span>"));

            title = title.substring(title.indexOf("</small>") + 8, title.indexOf("</h1>"));

            System.out.println("lccn:[" + call_number + "], author:[" + author + "], title:[" + title + "]");
        }
        System.out.println("program end");
    }
}
