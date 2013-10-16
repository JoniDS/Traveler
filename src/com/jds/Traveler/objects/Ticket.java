package com.jds.Traveler.objects;


import android.util.Log;

import java.util.ArrayList;

public class Ticket {

    //Types of transport
    public static int TYPE_FLIGHT = 1;
    public static int TYPE_TRAIN = 2;
    public static int TYPE_AIRPORT_BUS = 3;

    //Trip origin
    private String  from;
    //Trip destination
    private String to;
    //Type of
    private String type;
    //If baggage will be dropped at the end of the trip
    private boolean hasBaggageDrop;
    private String baggageCounter;
    //If ticket has a specific seat
    private boolean hasAssignedSeats;
    private String seatAssignment;
    //If tick has a gate (Airport)
    private boolean hasGate;
    private String gateNumber;
    private boolean hasTransportName;
    private String transportName;
    private Boolean baggageTransferred;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHasBaggageDrop() {
        return hasBaggageDrop;
    }

    public void setHasBaggageDrop(boolean hasBaggageDrop) {
        this.hasBaggageDrop = hasBaggageDrop;
    }

    public String getBaggageCounter() {
        return baggageCounter;
    }

    public void setBaggageCounter(String baggageCounter) {
        this.baggageCounter = baggageCounter;
    }

    public String getSeatAssignment() {
        return seatAssignment;
    }

    public void setSeatAssignment(String seatAssignment) {
        this.seatAssignment = seatAssignment;
    }

    public boolean isHasGate() {
        return hasGate;
    }

    public void setHasGate(boolean hasGate) {
        this.hasGate = hasGate;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public boolean isHasAssignedSeats() {
        return hasAssignedSeats;
    }

    public void setHasAssignedSeats(boolean hasAssignedSeats) {
        this.hasAssignedSeats = hasAssignedSeats;
    }

    public boolean isHasTransportName() {
        return hasTransportName;
    }

    public void setHasTransportName(boolean hasTransportName) {
        this.hasTransportName = hasTransportName;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public Boolean getBaggageTransferred() {
        return baggageTransferred;
    }

    public void setBaggageTransferred(Boolean baggageTransferred) {
        this.baggageTransferred = baggageTransferred;
    }

    //Checks if this is the departure location
    public boolean itsTheFirstDestination(ArrayList<Ticket> tickets){
        boolean unique = true;
        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            if (t.getFrom().equals(this.getFrom()) && t.getTo().equals(this.getTo()))
              ;
            else
            if (this.getFrom().equals(t.getTo()) || this.getFrom().equals(t.getFrom()))
                unique = false;

        }
        return unique;
    }

    //Returns the route after this (this.to = next.from)
    public Ticket getNextRoute(ArrayList<Ticket> tickets){
        boolean unique = true;
        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            if (t.getFrom().equals(this.getFrom()) && t.getTo().equals(this.getTo()))
                ;
            else
            if (this.to.equals(t.from))
                return t;

        }
        return null;
    }
}
