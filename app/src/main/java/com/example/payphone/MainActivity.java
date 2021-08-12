package com.example.payphone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView txtIdentidad, txtCelular,txtCosto,txtIva,txtPagoT;
    Button btnEnviar;
    RequestQueue requestQueue;
    JSONObject jsonObject;
    private static final String url= "https://pay.payphonetodoesposible.com/api/Sale";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIdentidad=findViewById(R.id.identidad);
        txtCelular=findViewById(R.id.celular);
        txtCosto=findViewById(R.id.cobro);
        txtIva=findViewById(R.id.iva);
        txtPagoT=findViewById(R.id.pago);
        btnEnviar=findViewById(R.id.btnEnviar);



        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperaPyPhone();
            }
        });
    }
    private void iniTUI(){
       // textView= findViewById(R.id.textView);
    }
    void OperaPyPhone(){
        requestQueue=Volley.newRequestQueue(this);
        jsonObject= new JSONObject();
        try{
            jsonObject.put("phoneNumber", txtCelular.getText().toString());
            jsonObject.put("countryCode", "593");
            jsonObject.put("clientUserId", txtIdentidad.getText().toString());
            jsonObject.put("reference", "none");
            jsonObject.put("responseUrl", "http://paystoreCZ.com/confirm.php");
            jsonObject.put("amount",txtCosto.getText().toString());
            jsonObject.put("amountWithTax",txtPagoT.getText().toString());
            jsonObject.put("amountWithoutTax","0");
            jsonObject.put("tax",txtIva.getText().toString());
            jsonObject.put("clientTransactionId", "666");

        }catch (JSONException e){
            e.printStackTrace();
        }

            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(
                    Request.Method.POST, url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                    error.printStackTrace();
                }
            }

            ){
             public Map<String,String> getHeaders() throws AuthFailureError{
                 Map<String,String> map=new HashMap<>();
                 map.put("Content-Type","application/json");
                 map.put("Accept","application/json");
                 map.put("Authorization","Bearer vpaBjW0l8CyqU3h78fQs60zcpC1AjWeZeY6AejOmmwSU4w4gc4Pi8cjPnzyv79rH-fPaPGBiRvRXEy9iWRoaeXl6U-8SGhrWDTXt8JtV7zSG5uWxfQKl66PueOmEibPYHQ_Q6ZRF738ERVwxc9uzcoh9Iwba68CjONVexzTPRHUfWAlrJtOHiTBpM98yWVzPiq1WziGzcsOHynvyYmzoiGbgUPq2-_fd5_uBC1QKtYyOtPGLmgaeeIudz0bQIiuIKEb5JPpyzjGRW9Hd2ZOZ2PGBvna2Fm4xvNxBtNsWB3CGZRJpnzZgNk-S8AVtuG8ZVtg81w");
                 return map;
             }
            };
        requestQueue.add(jsonObjectRequest);
    }





}