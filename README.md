# Java final project - using Spotify API

Final project I made for my Advanced Programming class. The project uses a Java wrapper for Spotify's web API, available
here:
https://github.com/spotify-web-api-java/spotify-web-api-java

## Backend

The backend of this application is a Java Spring Boot application. Using the Spotify API, the application is capable to
deliver different types of information about the current user:

- Top artists (short term, medium term and long term) and the user's followed artists on Spotify
- Top tracks (short term, medium term and long term)
- Recently played tracks
- Top genres (short term, medium term and long term)
- The user's playlists

The application can also create playlists, containing the user's top tracks for the specified period of time, or
playlists containing artists that are related to the user's top artists - this way, the user can discover new music. The
playlist names are selected randomly from the project's database.
The user's top tracks are also compared to a list of 11400 most listened songs on Spotify (this tracks exist in the
project database and are imported using the DatabaseImporter). This way, the user is given a percentage of how
mainstream their music taste is.

## Frontend

The frontend of this application is a React application, that uses Tailwind CSS. It uses the backend to display the
user's information and to create playlists.
I used the Cattpuccin Mocha theme color palette, available here: https://github.com/catppuccin/catppuccin



