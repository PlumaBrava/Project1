package com.example.perezjuanjose.movip1;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetalleFragment extends Fragment {

    public MovieDetalleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_movi_detalle, container, false);
        Intent intent = getActivity().getIntent();
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT) ){
            String forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            String imagen = intent.getStringExtra("IMAGEN");

            ((TextView) rootView.findViewById(R.id.detail_title))
                    .setText(forecastStr);

            ImageView movieImagen =(ImageView) rootView.findViewById(R.id.detail_image);
            Picasso.with(rootView.getContext())
                    .load("http://image.tmdb.org/t/p/w500"+imagen)
                    .into(movieImagen);
        }

        return rootView;


    }
}
