package com.jds.Traveler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private LinearLayout pb;
    private Button btnOrder, btnShuffle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar();
        lstTickets = (ListView) findViewById(R.id.lstTickets);
        pb = (LinearLayout) findViewById(R.id.pb);
        btnOrder = (Button) findViewById(R.id.btn_order);
        btnShuffle = (Button) findViewById(R.id.btn_shuffle);

        tAdapter = new TicketAdapter(this);
        lstTickets.setAdapter(tAdapter);

        loadTickets();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               orderTickets();
            }
        });
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shuffleTickets();
            }
        });
    }


    private void loadTickets(){
        //Cancel task if is running
        setSupportProgressBarIndeterminateVisibility(true);
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
                setSupportProgressBarIndeterminateVisibility(false);
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
                orderTickets();
                return true;
            case R.id.mi_shuffle:
                shuffleTickets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shuffleTickets() {
        tAdapter.shuffleTickets();
        Toast.makeText(this, getString(R.string.msg_shuffling), Toast.LENGTH_SHORT).show();
    }

    private void orderTickets() {
        pb.setVisibility(View.VISIBLE);
        tAdapter.orderTickets();
        Toast.makeText(this, getString(R.string.msg_ordering), Toast.LENGTH_SHORT).show();
        pb.setVisibility(View.GONE);
    }
}
