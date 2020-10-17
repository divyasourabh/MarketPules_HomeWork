package com.ds.assignment.utils;

import com.ds.assignment.data.model.MessageModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MessageComparator implements Comparator<MessageModel> {

    @Override
    public int compare(MessageModel message1, MessageModel message2) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
//        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date1 = null;
        try {
            date1 = format.parse(message1.getTimestamp());
        } catch (Exception e) {
            e.printStackTrace();
        }


        Date date2 = null;
        try {
            date2 = format.parse(message2.getTimestamp());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Date1 : " + format.format(date1));
        System.out.println("Date1 : " + format.format(date2));

        return date1.compareTo(date2);
    }
}