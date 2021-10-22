package com.example.login;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.*;
public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
    EditText email,password;
    CheckBox checkBox;
    ImageButton signin;
    Button signup;
    ProgressBar load;
    static String URL_LOGIN="https://findmykids.000webhostapp.com/models/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        checkBox = (CheckBox)findViewById(R.id.checkbox);
        load =(ProgressBar) findViewById(R.id.progressBar);

        signin =(ImageButton)findViewById(R.id.signin);



        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String memail=email.getText().toString().trim();
                String mpassword=password.getText().toString().trim();
                if(!memail.isEmpty() && !mpassword.isEmpty()){
                    Login(memail,mpassword);
                }else{
                    email.setError("s'il vous plaît entrer votre username");
                    password.setError("s'il vous plaît entrer votre password");
                }

            }
        });


    }
    private void Login(final String email,final String password){
        load.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.getString("success");
                            JSONArray jsonArray=jsonObject.getJSONArray("login");
                            if(success.equals("1")){
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String name=object.getString("name").trim();
                                    String  email=object.getString("email").trim();
                                    String age=object.getString("age").trim();
                                    String  idenfant=object.getString("id").trim();
                                    String nopere=object.getString("nompere").trim();
                                    String  pre=object.getString("prenompere").trim();
                                    Intent intent=new Intent(MainActivity.this,Profiel.class);
                                    intent.putExtra("nom",name);
                                    intent.putExtra("prenom",email);
                                    intent.putExtra("age",age);
                                    intent.putExtra("id",idenfant);
                                    intent.putExtra("nompere",nopere);
                                    intent.putExtra("prenompere",pre);
                                    startActivity(intent);
                                    load.setVisibility(View.GONE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "ERREUR DE LOGIN"+e.toString()+"!!!!" ,Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "IERREUR DE REPONSE" ,Toast.LENGTH_SHORT).show();


            }

        })  {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("email", email);
                params.put("password",password);
                return params;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this)   ;
        requestQueue.add(stringRequest);

    }


}