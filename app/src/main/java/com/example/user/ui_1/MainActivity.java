package com.example.user.ui_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    final static public String address = "10.23.44.218";
    public int last = 0;
    public MyPage curPage;
    public final int add = 5;
    public int downloading = 0;
    public int ggbet = 1;
    private void showProblems(){
        Toast toast = Toast.makeText(getApplicationContext(),
                "Проблемы с соединением к серверу. Проверьте настройки Интернета", Toast.LENGTH_SHORT);
        toast.show();
    }

    public class RetriveTask extends AsyncTask<String, Integer, TreeMap<String, ArrayList<String> > >{
        private Exception e = null;
        private int port = 6666;
        protected TreeMap<String, ArrayList<String> > doInBackground(String... urls){
            TreeMap<String, ArrayList<String>>responce = new TreeMap<>();
            try {
                InetAddress ipAddress = InetAddress.getByName(urls[0]);
                Socket socket = new Socket(ipAddress, port);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                TreeMap<String, String> req = new TreeMap<>();
                req.put("type", "get_list");
                req.put("count", "5");
                req.put("last",urls[1]);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                out.writeUTF(gson.toJson(req));
                out.flush();
                String res = in.readUTF();
                responce = (TreeMap<String, ArrayList<String> >) new Gson().fromJson(res, responce.getClass());
                req.clear();
                req.put("type", "close");
                out.writeUTF(gson.toJson(req));
                out.flush();
                socket.close();
                return responce;
            } catch (Exception e) {
                this.e = e;
                Log.e("Errr", e.toString());
            }
            return responce;
        }
        protected void onPostExecute(TreeMap<String,ArrayList<String>> result){
            if (e != null){
                showProblems();
            }
        }
    }

    public void reDownload(){

        RetriveTask retriveTask = new RetriveTask();
        retriveTask.execute(address, String.valueOf(last));
        TreeMap<String, ArrayList<String>> res = new TreeMap<>();
        ArrayList<String>links = new ArrayList<>();
        try {
            res = retriveTask.get();
            if (res != null)
                links = res.get("links");
            else
                links = null;
        } catch (InterruptedException e) {
            showProblems();
        } catch (ExecutionException e) {
            showProblems();
        }
        if (links != null) {
            for (int i = 0; i < links.size(); i++) {
                downloading++;
                DownloadTask task = new DownloadTask();
                task.execute(links.get(i), Integer.toString(i + last));
            }
            last += add;
        }
    }
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        Log.e("scrollY", String.valueOf(scrollY));
        if (getCurrentFocus() != null) {
            Log.e("bot", String.valueOf(last));
            Log.e("height", String.valueOf(curPage.getView().findViewById(R.id.list).getHeight() ));
            if (scrollY > 5000 * ggbet && downloading == 0){
                // GG Я УМЕР Я НЕ ЗНАЮ ЧТО ЭТО
                ggbet++;
                reDownload();
            }
        }
    }
    public class DownloadTask extends AsyncTask<String, Integer, Bitmap> {
        public Exception e;
        public int number;
        protected Bitmap doInBackground(String... connections){
            number = Integer.parseInt(connections[1]);
            Bitmap result;
            InputStream inputStream = null;
            try {
                HttpURLConnection connection =(HttpURLConnection) new URL(connections[0]).openConnection();
                connection.setDoInput(true);
                connection.connect();
                inputStream = connection.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = BitmapFactory.decodeStream(inputStream);
            return result;
        }
        @Override
        protected void onPostExecute(Bitmap result){
            super.onPostExecute(result);
            ImageView temp = new ImageView(curPage.getContext());
            temp.setImageBitmap(result);
            curPage.addMem(new myMem("Meme#" + number, number, temp));
            downloading--;
        }
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState sc) {
        ActionBar ab = getSupportActionBar();
        if (sc == ScrollState.UP && ab.isShowing()) {
            ab.hide();
        } else {
            if (sc == ScrollState.DOWN && !ab.isShowing())
                ab.show();
        }
    }
    @Override
    public void onDownMotionEvent() {
    }
    private ViewPager viewPager;
    private LayoutInflater inflater;
    public  MyPageAdapter pageAdapter;
    private void actionBarSetup(ActionBar actionBar){

        View customView = inflater.inflate(R.layout.custom_actionbar, null);
        TextView customTitle = (TextView)customView.findViewById(R.id.actionbarTitle);
        customTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RecyclerView listView = viewPager.getFocusedChild().findViewById(R.id.list);
                listView.smoothScrollToPosition(0);
            }
        });

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(customView);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        inflater = LayoutInflater.from(this);

        pageAdapter = new MyPageAdapter(this,new ArrayList<MyPage>(), getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(0);
        ActionBar actionBar = getSupportActionBar();
        actionBarSetup(actionBar);
        curPage = (MyPage) pageAdapter.getItem(0);
        reDownload();
    }
}