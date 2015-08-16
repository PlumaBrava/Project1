package com.example.perezjuanjose.movip1;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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
import android.widget.GridView;
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
public class MovieFragment extends Fragment  {


    static private String API_KEY  = "79424eca98daa0b906a464bf7d8f9f0f";
    private FilmsAdapter mMoviesAdapter;
    private int moviePage;


    @Override
    public void onStart(){
        super.onStart();
        FetchMoviesTask moviesTask = new FetchMoviesTask();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String order_by=prefs.getString("order_by", "casa");


        //            weatherTask.execute(location);
        String asc_desc = prefs.getString("asc_desc","mesa");
        Log.v("Prererencias", "order_by:" + order_by + "asc_desc: " + asc_desc);
        moviesTask.execute(order_by, asc_desc);

       // FetchMoviesTask moviesTask = new FetchMoviesTask();
       // moviesTask.execute("94043","8");



    }

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

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String order_by=prefs.getString("order_by", "casa");


           //            weatherTask.execute(location);
            String asc_desc = prefs.getString("asc_desc","mesa");
            Log.v("Prererencias", "order_by:" + order_by + "asc_desc: " + asc_desc);
            moviesTask.execute(order_by,asc_desc );
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
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
                new  Film(false,"bckdrop","es","title","overview","Date","posterPath", 2.4,"title",false,3.3,4),
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
        GridView listView = (GridView) v.findViewById(R.id.movies_list);
        listView.setAdapter(mMoviesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getActivity(), "data :" + mMoviesAdapter.getItem(position).toString(), Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(getActivity(), MovieDetalle.class);

                intent.putExtra(Intent.EXTRA_TEXT, mMoviesAdapter.getItem(position).title);


                intent.putExtra("ADULTS", mMoviesAdapter.getItem(position).adults);
                intent.putExtra("BACKDROP_PATH", mMoviesAdapter.getItem(position).backdrop_path);
                intent.putExtra("ORIGINLANGUAJE", mMoviesAdapter.getItem(position).origianlLanguaje);
                intent.putExtra("ORIGINALTITLE", mMoviesAdapter.getItem(position).originalTitle);
                intent.putExtra("OVERVIEW", mMoviesAdapter.getItem(position).overview);
                intent.putExtra("RELEASEDATE", mMoviesAdapter.getItem(position).releaseDate);
                intent.putExtra("POSTERPATH", mMoviesAdapter.getItem(position).posterPath);
                intent.putExtra("POPULARITY", mMoviesAdapter.getItem(position).popularity);
                intent.putExtra("TITLE", mMoviesAdapter.getItem(position).title);
                intent.putExtra("VIDEO", mMoviesAdapter.getItem(position).video);
                intent.putExtra("VOTEAVERAGE", mMoviesAdapter.getItem(position).voteAverage);
                intent.putExtra("VOTECOUNT", mMoviesAdapter.getItem(position).vote_count);




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

            int total_pages;
            int total_results;


            // These are the names of the JSON objects that need to be extracted.

            final String OWM_PAGE = "page";
            final String OWM_RESULTS = "results";
            final String OWM_TOTAL_PAGES = "total_pages";
            final String OWM_TOTAL_RESULTS = "total_results";
            final String OWM_ADULT = "adult";
            final String OWM_BACKDROP_PATH = "backdrop_path";
            final String OWM_ID = "id";
            final String OWM_ORIGINAL_LANGUAJE = "original_language";
            final String OWM_ORIGINAL_TITLE = "original_title";
            final String OWM_OVERVIEW = "overview";
            final String OWM_RELEASE_DATE = "release_date";
            final String OWM_POSTER_PATH = "poster_path";
            final String OWM_POPULARITY = "popularity";
            final String OWM_TITLE = "title";
            final String OWM_VIDEO = "video";
            final String OWM_VOTE_AVERAGE = "vote_average";
            final String OWM_VOTE_COUNT = "vote_count";



            // OWM returns the data number of pages and results.

            JSONObject pageMoviesJson = new JSONObject(movieJsonStr);
            total_pages=pageMoviesJson.getInt(OWM_TOTAL_PAGES);
            total_results=pageMoviesJson.getInt(OWM_TOTAL_RESULTS);
            Log.v(LOG_TAG, "Total pages:" +total_pages+ "total results: "+total_results);
            JSONArray resultsArray = pageMoviesJson.getJSONArray(OWM_RESULTS);

            // OWM returns the data of the movies in an array


            Film[] resulMovies = new Film[resultsArray.length()];
            for(int i = 0; i < resultsArray.length(); i++) {
                // Thise are the variables of the class film
                Boolean adults;
                String backdrop_path;
                String origianlLanguaje;
                String originalTitle;
                String overview;
                String releaseDate;
                String posterPath;
                Double popularity;
                String title;
                Boolean video;
                Double voteAverage;
                int vote_count;

                // Get the JSON object representing list
                JSONObject movie = resultsArray.getJSONObject(i);
                    adults=movie.getBoolean(OWM_ADULT);
                    backdrop_path = movie.getString(OWM_BACKDROP_PATH);
                    origianlLanguaje=movie.getString(OWM_ORIGINAL_LANGUAJE);
                    originalTitle= movie.getString(OWM_ORIGINAL_TITLE);
                    overview=movie.getString(OWM_OVERVIEW);
                    releaseDate=movie.getString(OWM_RELEASE_DATE);
                    posterPath=movie.getString(OWM_POSTER_PATH);
                    popularity = movie.getDouble(OWM_POPULARITY);
                    title = movie.getString(OWM_TITLE);
                    video=movie.getBoolean(OWM_VIDEO);
                    voteAverage=movie.getDouble(OWM_VOTE_AVERAGE);
                    vote_count=movie.getInt(OWM_VOTE_COUNT);



                resulMovies[i]= new Film(adults,backdrop_path,origianlLanguaje,originalTitle,overview,releaseDate,posterPath,popularity,title,video,voteAverage,vote_count);


            }

            for (Film s : resulMovies) {
                Log.v(LOG_TAG, "FMovies entry: " + s.getTitle()+" -"+s.getPopularity()+" -"+s.backdrop_path);
            }
            return resulMovies;

        }
        @Override
        protected Film[] doInBackground(String... params) {

            Log.v(LOG_TAG, "Params 0 " + params[0]);
            Log.v(LOG_TAG, "Params 1 " + params[1]);
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


                final String FORMAT_SORT_BY = "sort_by";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_API_KEY,API_KEY)
                       .appendQueryParameter(FORMAT_SORT_BY, params[0]+params[1])
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
