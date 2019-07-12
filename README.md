# LCCN-Lookup
This program takes in a search query (a title or fragment of title) and searches through the Library of Congress catalog for all results matching the query. \
The returned format is as follows: \
``lccn:[ ... ], author:[ ... ], title:[ ... ]``

## Usage
To execute the program, open the command line, navigate to the directory where you put ``lccn.jar`` and enter: \
``java -jar lccn.jar "your search query here"`` \
\
Note: searches work best when *only* a title is given in the search query \
Note: The Library of Congress catalog requently reaches the maximum number of connections. Even if you are able to connect via a browser, the program may be unable to for a period. If this occurs, simply wait (can take upwards of half an hour during peak times) and run the program again.
