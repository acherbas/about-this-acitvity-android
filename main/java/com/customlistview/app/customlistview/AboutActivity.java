package com.customlistview.app.customlistview;
/**
 * AboutThis Template, plantilla base para generar ventana/layout/activity de información de la aplicación
 *
 */
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutActivity extends AppCompatActivity {

    private static String tag = "AboutActivity";

    //Variables de configuración
    private static String emailSendBug = "webserveis@gmail.com";
    private static String urlFacebook = null;
    private static String urlGooglePlus = "https://plus.google.com/106878853408561937382";
    private static String urlTwitter = "https://twitter.com/webserveis";
    private static String urlLinkedin = null;
    private String appName;

    private List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //this.getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        /*ActionBar actionBar =getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        //Añade el botón hacia atras a la toolbar del header
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvAppName = (TextView) findViewById(R.id.name_app);
        TextView tvAppVersion = (TextView) findViewById(R.id.app_version);

        appName = getApplicationName(this); //obtener el nombre de la aplicación

        tvAppName.setText(appName); //nombre app
        tvAppVersion.setText(BuildConfig.VERSION_NAME); //version x.x.x

        Log.v(tag, "name device" + android.os.Build.MODEL);

        //Obtener el UID (Unique Id Device)
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID).toUpperCase();

        /**
         * bloque de licéncia
         */
        TextView tvDescription = (TextView) findViewById(R.id.app_description);
        tvDescription.setText(Html.fromHtml(String.format(getString(R.string.about_license_content), android.os.Build.MODEL, android_id)));

        /**
         * bloque de votar y opinar
         */
        LinearLayout btnRate = (LinearLayout) findViewById(R.id.btnRate);
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clck event
                Log.v(tag, "rate  button goto http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                openWebURL("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            }
        });

        /**
         * Bloque datos informativos del desarrollador
         */
        ListView lv = (ListView) findViewById(R.id.listView);

        /*
        //eso sirve para prevenir que se haga scroll al padre mientras permite scroll en el hijo
        lv.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/

        // enter valid data, these 2 are the same as the remaining 9

        //ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(3);
        list = new ArrayList<>(3);
        HashMap<String, String> maplist;

        maplist = new HashMap<String, String>();
        maplist.put("key", getString(R.string.about_label_studio));
        maplist.put("value", "Webserveis");
        list.add(maplist);

        maplist = new HashMap<String, String>();
        maplist.put("key",  getString(R.string.about_label_developer));
        maplist.put("value", "Gerard Coll");
        list.add(maplist);

        maplist = new HashMap<String, String>();
        maplist.put("key", getString(R.string.about_label_webpage_support));
        maplist.put("value", "www.webserveis.com");
        maplist.put("uri", "http://www.webserveis.com");
        list.add(maplist);

        maplist = new HashMap<String, String>();
        maplist.put("key", getString(R.string.about_label_email_support));
        maplist.put("value", "webserveis@gmail.com");
        maplist.put("uri", "webserveis@gmail.com");
        list.add(maplist);


        String[] from = { "key", "value" };
        int[] to = { android.R.id.text1, android.R.id.text2 };

        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, from, to);


        lv.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lv);
        lv.setFocusable(false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
                String val =(String) arg0.getItemAtPosition(position).toString();

                if (list.get(position).containsKey("uri")) {
                    val = list.get(position).get("uri").toString();

                    if (isValidEmailAddress(val)) {
                        //abrir cliente de email
                        openEmailApp(val,appName,"cuerpo del mensaje");
                        Log.v(tag,"Abrir cliente email");
                    } else {
                        //Abrir navegador web
                        Log.v(tag,"Abrir navegador web");
                        openWebURL(val);
                    }
                }
                Log.v(tag,"item: " + val);
            }
        });

        //Bloque de iconos sociales
        ImageButton btnFacebook = (ImageButton) findViewById(R.id.btnGoFacebook);
        ImageButton btnGooglePlus = (ImageButton) findViewById(R.id.btnGoGooglePlus);
        ImageButton btnTwitter = (ImageButton) findViewById(R.id.btnGoTwitter);
        ImageButton btnLinkelin = (ImageButton) findViewById(R.id.btnGoLinkelin);

        if (urlFacebook == null) btnFacebook.setVisibility(View.GONE);
        if (urlGooglePlus == null) btnGooglePlus.setVisibility(View.GONE);
        if (urlTwitter == null) btnTwitter.setVisibility(View.GONE);
        if (urlLinkedin == null) btnLinkelin.setVisibility(View.GONE);

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clck event
                Log.v(tag,"click button facebook");

                openWebURL(urlFacebook);
            }
        });

        btnGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clck event
                Log.v(tag,"click button google plus");

                openWebURL(urlGooglePlus);
            }
        });

        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clck event
                Log.v(tag,"click button twitter");

                openWebURL(urlTwitter);
            }
        });

        btnLinkelin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clck event
                Log.v(tag,"click button linkelin");

                openWebURL(urlLinkedin);
            }
        });

        /**
         * Bloque para enviar reporte de error a medias
         * @todo falta como mostrar el botón cuando el scroll baje
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailSendBug});
                i.putExtra(Intent.EXTRA_SUBJECT, appName);
                i.putExtra(Intent.EXTRA_TEXT, "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Enviar mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Estoy usando la aplicación tal, la puedes obtener de , pruebala!";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                try {
                    startActivity(Intent.createChooser(sharingIntent, "Compartir con"));
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "No is posible to share content.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Funciones exclusivas para AboutThis-Template
     */

    /**
     * para obtener el nombre de la aplicación
     * @param context context contexto de la aplicación
     * @return string nombre de la aplicación
     */
    public static String getApplicationName(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }

    /**
     * reajusta la altura del objeto si se encuentra dentro de un control scrollview
     * @param listview listView  reajustar su altura (height)
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * para comprobar si es un correo electrónico válido
     * @param string email  dirección de correo electrónico abcx@abc.abc
     * @return boolean  si es verdadero o falso
     */
    public boolean isValidEmailAddress(String email) {
        String EMAIL_PATTERN =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        //String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(EMAIL_PATTERN);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Para lanzar el navegador web
     * @param string url  dirección web
     */
    public void openWebURL(String url ) {
        if (!URLUtil.isValidUrl(url)) {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;

        }
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }



    /**
     * Para enviar correo electrónico con alguna aplicación de correo
     * @param string email    dirección de correo electrónico
     * @param string subject  titulo del mensage
     * @param string body     cuerpo del mensage
     * @throws ActivityNotFoundException Si no se encuentra aplicación asociada para enviar un email
     */
    public void openEmailApp(String email, String subject, String body) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        try {
            startActivity(Intent.createChooser(i, "Enviar mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

