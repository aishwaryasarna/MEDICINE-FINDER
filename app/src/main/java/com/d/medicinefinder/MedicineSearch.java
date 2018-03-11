package com.d.medicinefinder;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MedicineSearch extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> medicines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_search);
//        medicines.add("Crocin");
//        medicines.add("ciplox");
//        medicines.add("folic acid");
//        medicines.add("paracetomol");
//        medicines.add("norflox");
//        medicines.add("entraquinol");
//        medicines.add("ciphrazone");

        searchView = (SearchView) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.list);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchDb(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    void  searchDb(final String medname){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait...");
        dialog.show();
        String url ="http://medicinefinder.000webhostapp.com/upload/search.php";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("", "Login Response: " + response.toString());


                try {
                    JSONArray jObj = new JSONArray(response);
                    medicines.clear();

                    for(int i=0;i<jObj.length();i++)
                    {
                        medicines.add(jObj.getString(i));
                    }

                    dialog.dismiss();

                    adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,medicines);

                    listView.setAdapter(adapter);

                 Toast.makeText(getApplicationContext(),"Available",Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                    dialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

                Toast.makeText(getApplicationContext(),"Error: " + error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();


                params.put("medicine", medname);



                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(strReq, "");
    }
}
