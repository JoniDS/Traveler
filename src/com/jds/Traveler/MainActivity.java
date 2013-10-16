package com.jds.Traveler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.jds.Traveler.adapters.TicketAdapter;
import com.jds.Traveler.objects.Ticket;
import com.jds.Traveler.tasks.LoadTicketsTask;
import com.jds.Traveler.utils.ASyncTaskListener;

public class MainActivity extends ActionBarActivity {

    private final String TAG = "MAIN";
    private LoadTicketsTask tTickets;
    private Ticket[] tickets;
    private TicketAdapter tAdapter;
    private ListView lstTickets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar();
        lstTickets = (ListView) findViewById(R.id.lstTickets);
        tAdapter = new TicketAdapter(this);
        lstTickets.setAdapter(tAdapter);

        loadTickets();
    }


    private void loadTickets(){
        //Cancel task if is running
        if(tTickets != null)
            tTickets.cancel(true);
        tTickets = new LoadTicketsTask(this);

        tTickets.setOnThreadFinishedListener(new ASyncTaskListener() {
            @Override
            public void onThreadFinished(Object result) {
                if(result != null && result.getClass() == Ticket[].class)
                {
                    Log.i("MAIN", "got tickets");
                    tickets = (Ticket[]) result;
                    tAdapter.clear();
                    for(Ticket t : tickets)
                    {
                        Log.i(TAG, t.getFrom());
                        tAdapter.addTicket(t);
                    }
                    tAdapter.notifyDataSetChanged();
                }
            }
        });
        tTickets.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.mi_order:
                tAdapter.orderTickets();
                Toast.makeText(this, getString(R.string.msg_ordering), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mi_shuffle:
                tAdapter.shuffleTickets();
                Toast.makeText(this, getString(R.string.msg_shuffling), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
