package com.helias.myapplication;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.Droidlogin.library.Httppostaux;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DisplayMessageActivity extends Activity {
	private Button button1;
	private EditText editText1;
	private EditText editText2;
	
	
	 
	    Httppostaux post = new Httppostaux();
	    // String URL_connect="http://www.scandroidtest.site90.com/acces.php";
	    String IP_Server="10.0.2.2:8888";//IP DE NUESTRO PC
	    String URL_connect="http://"+IP_Server+"/register";//ruta en donde estan nuestros archivos
	  
	    boolean result_back;
	    private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		Intent intent = getIntent();
		
		//String message = "hello world";
		
	    //TextView textView = new TextView(this);
	    //textView.setTextSize(40);
	   
		//textView.setText(message);
		
	    // Set the text view as the activity layout
	    //setContentView(textView);
		
		setContentView(R.layout.activity_display_message);
		initTextFields();
	    initButtons();
		
		
	}

	private void initButtons(){
		button1 = (Button)findViewById(R.id.button1);
		
		button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				//go to register activity
				//startActivity(new Intent("jhu.spring2013.networks.lhz.DBox.DisplayMessageActivity"));
				//sendMessage(view);
				postData();
			}
		});
	}
	public void postData() {

		
	    // Create a new HttpClient and Post Header
		/*
		String urlString = "http://10.0.2.2";
		URL url = new URL(urlString);
		try {
			url = new URL(urlString);
			URLConnection conn = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			HttpGet get = new HttpGet(urlString);
			
			System.out.println(httpConn.getPermission().getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		*/
		String username=editText1.getText().toString();
		String passw=editText2.getText().toString();
		
		new asynclogin().execute(username,passw);   
		
		/*
		 UploadFileUtil fileUpload = new UploadFileUtil () ;
	        File file = new File ("xm12.xml") ; 
	        String response = fileUpload.executeMultiPartRequest("http://10.0.2.2/", file, file.getName(), "File Upload test Hydrangeas.jpg description") ;
	        System.out.println("Response : "+response);
		
		*/

	} 
	
	 public boolean loginstatus(String username ,String password ) {
	    	
	    	
	    	/*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
	    	 * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/ 
	    	ArrayList<NameValuePair> postparameters2send= new ArrayList<NameValuePair>();

    		postparameters2send.add(new BasicNameValuePair("userName",username));
    		postparameters2send.add(new BasicNameValuePair("passWord",password));

    		   //realizamos una peticion y como respuesta obtenes un array JSON
    		JSONArray jdata=post.getserverdata(postparameters2send, URL_connect);
    		    
	      	return true;

	    	
	    }
	private void initTextFields(){
		editText1 = (EditText)findViewById(R.id.editText1);
		editText2 = (EditText)findViewById(R.id.editText2);
	}
	
    //vibra y muestra un Toast
    public void err_login(){
    	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"Error:Nombre de usuario o password incorrectos", Toast.LENGTH_SHORT);
 	    toast1.show();    	
    }
	
	public static InputStream getInputStreamFromUrl(String url) {
		  InputStream content = null;
		  try {
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpResponse response = httpclient.execute(new HttpGet(url));
		    content = response.getEntity().getContent();
		  } catch (Exception e) {
		     System.out.println("nonononon");
		  }
		    return content;
		}
	
	/*		CLASE ASYNCTASK
	 * 
	 * usaremos esta para poder mostrar el dialogo de progreso mientras enviamos y obtenemos los datos
	 * podria hacerse lo mismo sin usar esto pero si el tiempo de respuesta es demasiado lo que podria ocurrir    
	 * si la conexion es lenta o el servidor tarda en responder la aplicacion sera inestable.
	 * ademas observariamos el mensaje de que la app no responde.     
	 */
	    
	    class asynclogin extends AsyncTask< String, String, String > {
	    	
	    	String user,pass;

	    	 protected void onPreExecute() {
	         	//para el progress dialog
	             pDialog = new ProgressDialog(DisplayMessageActivity.this);
	             pDialog.setMessage("Autenticando....");
	             pDialog.setIndeterminate(false);
	             pDialog.setCancelable(false);
	             pDialog.show();
	         }
	    	 

	    	 
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//obtnemos usr y pass
			
			user=params[0];
			pass=params[1];
			//enviamos y recibimos y analizamos los datos en segundo plano.
			if (loginstatus(user,pass)==true){    		    	
    			return "ok"; //login valido
    		}else{    		
    			return "err"; //login invalido     	          	  
    		}
            
			
		
		
		}
		
        protected void onPostExecute(String result) {

            pDialog.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=",""+result);
            
            if (result.equals("ok")){

 				Intent i=new Intent(DisplayMessageActivity.this,MainActivity.class);
 				i.putExtra("user",user);
 				startActivity(i); 
 				
             }else{
             	err_login();
             }
             
          }
	    	 
	    
			
	        }

}
