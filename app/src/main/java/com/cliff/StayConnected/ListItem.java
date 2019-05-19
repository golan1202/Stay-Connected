package com.cliff.StayConnected;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListItem extends Fragment {

    private String mHeading;
    private String mDescription;
    private String mImageURL;
    private String mDetailURL;


    public String getmHeading() {
        return mHeading;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public String getmDetailURL() {
        return mDetailURL;
    }

    private RecyclerView recyclerView;
    private List<ListItem> listItems;
    private MyAdapter myAdapter;
    private static final String REQUEST_URL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=3d5998d023614120acefd255e7017c2a";
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        listItems = new ArrayList<>();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MyAdapter myAdapter = new MyAdapter(listItems, getActivity());
        recyclerView.setAdapter(myAdapter);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject baseobject = new JSONObject(response);
                            JSONArray articles = baseobject.getJSONArray("articles");

                            for (int i = 0; i < articles.length(); i++) {
                                JSONObject jsonObject = articles.getJSONObject(i);
                                ListItem listItem = new ListItem();
                                String title = jsonObject.getString("title");
                                String description = jsonObject.getString("description");
                                String urlToImage = jsonObject.getString("urlToImage");
                                String url = jsonObject.getString("url");
                                listItem.mHeading = title;
                                listItem.mDescription = description;
                                listItem.mImageURL = urlToImage;
                                listItem.mDetailURL = url;

                                progressDialog.dismiss();
                                listItems.add(listItem);
                            }


                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Error Fetching Data", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        myAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(stringRequest);

    }
}
