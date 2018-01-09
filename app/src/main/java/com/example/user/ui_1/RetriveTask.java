package com.example.user.ui_1;

import android.os.AsyncTask;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by User on 08.01.2018.
 */
public class RetriveTask extends AsyncTask<String, Integer, TreeMap<String, ArrayList<String> > >{
    private Exception e;
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
            e.printStackTrace();
        }
        return responce;
    }
}
