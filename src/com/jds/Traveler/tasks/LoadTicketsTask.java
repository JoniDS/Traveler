package com.jds.Traveler.tasks;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.jds.Traveler.objects.Ticket;
import com.jds.Traveler.utils.ASyncTaskListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Will load the tickets asyncrounously
 */
public class LoadTicketsTask extends AsyncTask<Integer, Integer, Integer> {


    ASyncTaskListener listener;
    //To prevent generating a copy of the context object (save memory)
    WeakReference<Context> ctx;
    Ticket[] tickets;

    public LoadTicketsTask(Context ctx) {
        this.ctx = new WeakReference<Context>(ctx);
    }


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        Context c = ctx.get();
        if (c != null) {
            try {
                AssetManager assets = c.getAssets();
                InputStream is = assets.open("tickets.json");

                //Convert to string
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String content = "";

                while ((content = r.readLine()) != null) {
                    result.append(content);
                }
                Log.i("content", result.toString());
                //Parse JSON
                Gson gson = new Gson();
                tickets = gson.fromJson(result.toString(), Ticket[].class);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;

    }

    protected void onPostExecute(Integer result) {
        if (listener != null)
            listener.onThreadFinished(tickets);

    }

    public void setOnThreadFinishedListener(ASyncTaskListener listener) {
        this.listener = listener;
    }

}
