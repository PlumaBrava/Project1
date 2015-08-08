package com.example.perezjuanjose.movip1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment {


    static private String API_KEY  = "79424eca98daa0b906a464bf7d8f9f0f";
    private FilmsAdapter mMoviesAdapter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menumoviesfragmet, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchMoviesTask moviesTask = new FetchMoviesTask();
            moviesTask.execute("94043");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public MovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);

        // Create some dummy data for the ListView.  Here's a sample weekly forecast
        Film[] data = {
                new  Film("titlea","Polulrity","adress"),
                new  Film("titlea","Polulrity","adress"),
                new  Film("titlea","Polulrity","adress"),
                new  Film("titlea","Polulrity","adress"),
                new  Film("titlea","Polulrity","adress"),
                new  Film("titlea","Polulrity","adress"),
                new  Film("titlea","Polulrity","adress"),
                new  Film("titlea","Polulrity","adress"),
                new  Film("titlea","Polulrity","adress"),
                new  Film("titlea","Polulrity","adress"),
                };

        List<Film> listMovies = new ArrayList<Film>(Arrays.asList(data));

        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mMoviesAdapter =
                new FilmsAdapter(
                        getActivity(),
                        listMovies);
        View v= inflater.inflate(R.layout.moviefragment, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) v.findViewById(R.id.movies_list);
        listView.setAdapter(mMoviesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getActivity(), "data :" + mMoviesAdapter.getItem(position).toString(), Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(getActivity(), MovieDetalle.class);

                intent.putExtra(Intent.EXTRA_TEXT, mMoviesAdapter.getItem(position).title);
                intent.putExtra("IMAGEN", mMoviesAdapter.getItem(position).backdrop_path);

                startActivity(intent);


            }
        });

        return v;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Film[]> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        /* The date/time conversion code is going to be moved outside the asynctask later,
         * so for convenience we're breaking it out into its own method now.
         */
        private String getReadableDateString(long time){
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }

        /**
         * Prepare the weather high/lows for presentation.
         */
        private String formatHighLows(double high, double low) {
            // For presentation, assume the user doesn't care about tenths of a degree.
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private Film[] getFilmDataFromJson(String movieJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_PAGE = "page";
            final String OWM_RESULTS = "results";
            final String OWM_TOTAL_PAGES = "total_pages";
            final String OWM_TOTAL_RESULTS = "total_results";
            final String OWM_ADULTS = "adults";
            final String OWM_BACKDROP_PATH = "backdrop_path";
            final String OWM_ID = "id";
            final String OWM_ORIGINAL_LANGUAJE = "original_languag";
            final String OWM_ORIGINAL_TITLE = "original_title";
            final String OWM_OVERVIEW = "overview";
            final String OWM_RELEASE_DATE = "release_date";
            final String OWM_POSTER_PATH = "poster_path";
            final String OWM_POPULARITY = "popularity";
            final String OWM_TITLE = "title";
            final String OWM_VIDEO = "video";
            final String OWM_VOTE_AVERAGE = "vote_average";
            final String OWM_VOTE_COUNT = "vote_count";





            JSONObject pageMoviesJson = new JSONObject(movieJsonStr);
            JSONArray resultsArray = pageMoviesJson.getJSONArray(OWM_RESULTS);

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.



            Film[] resulMovies = new Film[resultsArray.length()];
            for(int i = 0; i < resultsArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String title;
                String popularity; //Its a double but i don't need to process the information. So use the string
                String backdrop_path;

                // Get the JSON object representing the day
                JSONObject movie = resultsArray.getJSONObject(i);



                title = movie.getString(OWM_TITLE);
                popularity = movie.getString(OWM_POPULARITY);
                backdrop_path = movie.getString(OWM_BACKDROP_PATH);


                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                ////JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                ////  double high = temperatureObject.getDouble(OWM_MAX);
                ////  double low = temperatureObject.getDouble(OWM_MIN);

                //// highAndLow = formatHighLows(high, low);

                resulMovies[i]= new Film(title,popularity,backdrop_path);
             //   resulMovies[i].popularity=popularity;
              //  resulMovies[i].backdrop_path=backdrop_path;
            //                resulMovies[i].film(title, popularity , backdrop_path);

            }

            for (Film s : resulMovies) {
                Log.v(LOG_TAG, "FMovies entry: " + s.getTitle()+" -"+s.getPopularity()+" -"+s.backdrop_path);
            }
            return resulMovies;

        }
        @Override
        protected Film[] doInBackground(String... params) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String MOVIE_BASE_URL =
                        "https://api.themoviedb.org/3/discover/movie?";
                final String QUERY_API_KEY = "api_key";


                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_API_KEY,API_KEY)
                     //   .appendQueryParameter(FORMAT_PARAM, format)
                      //  .appendQueryParameter(UNITS_PARAM, units)
                      //  .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Forecast string: " + movieJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getFilmDataFromJson(movieJsonStr, numDays);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(Film[] result) {
            if (result != null) {
                mMoviesAdapter.clear();
                for(Film movi : result) {
                    mMoviesAdapter.add(movi);
                }
                // New data is back from the server.  Hooray!
            }
        }
    }
    
}
