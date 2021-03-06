package com.tourneynizer.tourneynizer.services;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.tourneynizer.tourneynizer.model.User;
import com.tourneynizer.tourneynizer.util.CookieRequestFactory;
import com.tourneynizer.tourneynizer.util.JSONConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by ryanwiener on 2/16/18.
 */

public class UserService {

    public UserService() {}

    public interface OnUserLoadedListener {
        public void onUserLoaded(User user);
    }

    public void getUserFromEmail(String email, final OnUserLoadedListener listener) {
        String url = HTTPService.DOMAIN + "user/find?email=" + email;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // parse response
                listener.onUserLoaded(JSONConverter.getInstance().convertJSONToUser(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //awww shucks
                listener.onUserLoaded(null);
            }
        });
        HTTPService.getInstance().getRequestQueue().add(request);
    }

    public void getUserFromID(long id, final OnUserLoadedListener listener) {
        String url = HTTPService.DOMAIN + "user/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // parse response
                listener.onUserLoaded(JSONConverter.getInstance().convertJSONToUser(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //awww shucks
                listener.onUserLoaded(null);
            }
        });
        HTTPService.getInstance().getRequestQueue().add(request);
    }

    public void getUsers(int pageNum, int pageSize, final OnUserLoadedListener listener) {
        String url = HTTPService.DOMAIN + "user/getAll";
        JSONArray pageination = new JSONArray();
        pageination.put(pageNum);
        pageination.put(pageSize);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // parse response
                Log.d("Response", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        listener.onUserLoaded(JSONConverter.getInstance().convertJSONToUser(response.getJSONObject(i)));
                    } catch (JSONException e) {
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //awww shucks
                Log.e("Response", error.toString());
                listener.onUserLoaded(null);
            }
        });
        HTTPService.getInstance().getRequestQueue().add(request);
    }

    public void getUserFromEmailAndPassword(String email, String password, final OnUserLoadedListener listener) {
        String url = HTTPService.DOMAIN + "auth/login";
        JSONObject loginJSON = new JSONObject();
        try {
            loginJSON.put("email", email);
            loginJSON.put("password", password);
        } catch (JSONException e) {
            loginJSON = null;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, loginJSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // parse response and make sure the user was valid
                Log.d("Response", response.toString());
                listener.onUserLoaded(JSONConverter.getInstance().convertJSONToUser(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //awww shucks
                Log.e("Error", "" + error.toString());
                listener.onUserLoaded(null);
            }
        });
        HTTPService.getInstance().getRequestQueue().add(request);
    }

    public void createUser(String name, String email, String password, final OnUserLoadedListener listener) {
        String url = HTTPService.DOMAIN + "user/create";
        JSONObject userJSON = new JSONObject();
        try {
            userJSON.put("name", name);
            userJSON.put("email", email);
            userJSON.put("password", password);
        } catch (JSONException e) {
            userJSON = null;
        }
        Log.d("JSON", userJSON.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, userJSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                User u = JSONConverter.getInstance().convertJSONToUser(response);
                listener.onUserLoaded(u);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //awww shucks
                Log.e("Error", error.toString());
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        Log.d("Error", new String(error.networkResponse.data, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {

                    }
                }
                listener.onUserLoaded(null);
            }
        });
        HTTPService.getInstance().getRequestQueue().add(request);
    }

    public void logOut() {
        String url = HTTPService.DOMAIN + "auth/logout";
        StringRequest request = new CookieRequestFactory().makeStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", "Logged out");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });
        HTTPService.getInstance().getRequestQueue().add(request);
    }
}
