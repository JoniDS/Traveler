package com.jds.Traveler.utils;

/**
 * Interface to allow getting the results of AsyncTasks
 */
public interface ASyncTaskListener {
    public void onThreadFinished(Object result);
}
