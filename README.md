# popular-movies-stage2
An Android app showcasing popular movies and includes the following features:

Presents the user with a grid arrangement of movie posters upon launch.
Allows user to change sort order by most popular or by highest-rated
Allow the user to tap on a movie poster and transition to a details screen with additional information such as:

- original title
- movie poster image thumbnail
- option to Add to Favourites db
- plot synopsis (called overview in the api)
- user rating (called voteAverage in the api)
- release date
- reviews
- links to youtube to view trailers and featurettes

## Technologies used 

Gridview
RecyclerView with multiple layouts
Retrofit
Gson
SQLlite
Content Providers 

## Usage

In order to use the app, please generate an API key at https://www.themoviedb.org/ and replace the MyMovieDatabase.API_KEY string parameter with the String key in  /app/src/main/java/com/jman/popularmovies/MainActivity.java. Also delete the import statement for MyMovieDatabase in MainActivity.java

NOTE: the generate a key you must make a profile and request an API key on the settings page of your account at settings->API.
