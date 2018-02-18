package com.tourneynizer.tourneynizer.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.tourneynizer.tourneynizer.model.Tournament;
import com.tourneynizer.tourneynizer.model.TournamentType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.Locale;

/**
 * Created by ryanl on 2/17/2018.
 */

public class JSONConverter {

    public static Tournament convertJSONToTournament(final Context c, JSONObject tJSON) {
        Tournament t;
        try {
            Geocoder coder = new Geocoder(c);
            Address address;
            try {
                // May throw an IOException
                List<Address> addresses = coder.getFromLocationName(tJSON.getString("address"), 5);
                if (addresses == null || addresses.size() == 0) {
                    address = new Address(Locale.getDefault());
                    address.setLatitude(10);
                    address.setLongitude(100);
                } else {
                    address = addresses.get(0);
                }
            } catch (IOException ex) {
                address = new Address(Locale.getDefault());
                address.setLatitude(10);
                address.setLongitude(100);
            }
            t = new Tournament(tJSON.getLong("id"), tJSON.getString("name"), address, new Time(tJSON.getLong("startTime")), tJSON.getInt("maxTeams"), 0/*ioooootJSON.getInt("currentTeams")*/, new Time(tJSON.getLong("timeCreated")), TournamentType.VOLLEYBALL_BRACKET, tJSON.getInt("numCourts"), tJSON.getLong("creatorId"));
        } catch (JSONException e) {
            e.printStackTrace();
            t = null;
        }
        return t;
    }
}