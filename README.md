[![Build Status](https://travis-ci.org/ronsmits/omdbapi4j.png?branch=develop)](https://travis-ci.org/ronsmits/omdbapi4j)
[![Stories in Ready](https://badge.waffle.io/ronsmits/omdbapi4j.png?label=ready&title=Ready)](http://waffle.io/ronsmits/omdbapi4j)

This is a very thin layer around the http://omdbapi.com rest services. Everything is build around the Omdb object:

    List<SearchResults> results = new Omdb().search("Star Wars");

Will return at most 10 results of that have star wars in the title.

    List<SearchResuls> results = new Omdb().year(1982).type(MovieType.movie).search("blade runner");
    
Will search for all entries from the year `1982` with type `MovieType.movie` and title `blade runner`

See the test cases for more examples.


