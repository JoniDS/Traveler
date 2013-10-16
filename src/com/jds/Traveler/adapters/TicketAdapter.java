package com.jds.Traveler.adapters;

/**
 * Ticket adapter for the listview
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jds.Traveler.R;
import com.jds.Traveler.objects.Ticket;

import java.lang.ref.WeakReference;
import java.util.*;

//Internal item for listview

public class TicketAdapter extends BaseAdapter {


    private ArrayList<Ticket> tickets;

    public OnClickListener removeListener;
    Context ctx;

    public TicketAdapter(Context ctx) {
        this.ctx = ctx;
        tickets = new ArrayList<Ticket>();

    }

    public void setRemoveListener(OnClickListener listener) {
        this.removeListener = listener;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> items) {
        this.tickets = items;
        notifyDataSetChanged();
    }

    public void clear() {
        tickets.clear();
    }


    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public Ticket getItem(int position) {
        return tickets.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) ctx.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.partial_item_layout, null);
            holder = new ViewHolder();

            holder.lblDesc = (TextView) convertView.findViewById(R.id.lblDesc);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Ticket o = tickets.get(position);

        if (o != null) {

            String text="";
            switch(o.getType())
            {   case 1:
                text = ctx.getString(R.string.lbl_from)+" "+o.getFrom() +" "+ctx.getString(R.string.lbl_take)+" "+ ctx.getString(R.string.lbl_flight)+" "+
                        (o.isHasTransportName() ? o.getTransportName() : "")+" "+ctx.getString(R.string.lbl_to)+" "+ o.getTo()+". "
                        + String.format(ctx.getString(R.string.lbl_gate), o.getGateNumber())+", "+
                        (o.isHasAssignedSeats() ? String.format(ctx.getString(R.string.lbl_seat), o.getSeatAssignment())+"." : ".") +
                        (o.isHasBaggageDrop() ? String.format(ctx.getString(R.string.lbl_counter), o.getBaggageCounter())+".": ".")+
                        (o.getBaggageTransferred() ? ctx.getString(R.string.lbl_baggage) : "");
                break;

                case 2: text = ctx.getString(R.string.lbl_take)+" "+ ctx.getString(R.string.lbl_train)+" "+o.getTransportName()+" "+
                        ctx.getString(R.string.lbl_from)+" "+ o.getFrom()+" " + ctx.getString(R.string.lbl_to)+" "+o.getTo()+"."+
                        (o.isHasAssignedSeats() ? String.format(ctx.getString(R.string.lbl_seat), o.getSeatAssignment())+".": ".");
                    break;
                case 3:
                    text = ctx.getString(R.string.lbl_take)+" "+ ctx.getString(R.string.lbl_bus)+" "+
                            ctx.getString(R.string.lbl_from)+" "+ o.getFrom()+" " + ctx.getString(R.string.lbl_to)+" "+o.getTo()+"."+
                            (o.isHasAssignedSeats() ? String.format(ctx.getString(R.string.lbl_seat), o.getSeatAssignment())+".": ctx.getString(R.string.lbl_noseat)+".");
                    break;

            }

            holder.lblDesc.setText(text);

        }

        return convertView;
    }

    /**
     * This function will sort the tickets     *
     */
    public void orderTickets() {
        ArrayList<Ticket> sortedList = new ArrayList<Ticket>();

        Ticket last = null;
        //Run all tickets to find the departure location
        for(Ticket e: tickets)
        {

              if (e.itsTheFirstDestination(tickets) == true)
              {
                  sortedList.add(e);
                  last = e;
                  break;
              }
        }

        //Complete the list
        for(int i = 0;i<tickets.size()-1;i++)
        {
           last = last.getNextRoute(tickets);
           sortedList.add(last);
        }

        setTickets(sortedList);

        notifyDataSetChanged();
    }

    /**
     * Will shuffle the tickets order
     */
    public void shuffleTickets() {
        long seed = System.nanoTime();
        Collections.shuffle(tickets, new Random(seed));
        notifyDataSetChanged();
    }

    /**
     * To reuse views instead of always regenerating
     */
    class ViewHolder {
        TextView lblDesc;
    }

}