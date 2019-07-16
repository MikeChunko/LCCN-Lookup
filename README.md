# LCCN-Lookup
This program takes in a search query (a title or fragment of title) and searches through the Library of Congress catalog for all results matching the query. \
The returned format is as follows: \
``lccn:[ ... ], author:[ ... ], title:[ ... ]``

## Usage
To execute the program, open the command line, navigate to the directory where you put ``lccn.jar``, and enter: \
``java -jar lccn.jar "your search query here"`` \
\
Optionally, replace the search query with ``-input_file=input_file_name`` to read search queries from an input file. Each search query in the file is assumed to be on a new line. \
Optionally, add ``-out_file=output_file_name`` as the 2nd argument (must be 2nd argument) to specify a certain output file. \
\
Note: searches work best when *only* a title is given in the search query \
Note: The Library of Congress catalog frequently reaches the maximum number of connections. Even if you are able to connect via a browser, the program may be unable to for a period. If this occurs, simply wait (can take upwards of half an hour during peak times) and run the program again.
