package com.an_ant_on_the_sun.weather.ui;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;

public class XmlMapParser {
    private static final String TAG = XmlMapParser.class.getSimpleName();

    public static HashMap<String, Integer> getHashMapFromXmlResource(Context context,
                                                                 int xmlResourceId){
        HashMap<String, Integer> map = new HashMap<>();
        XmlResourceParser parser = context.getResources().getXml(xmlResourceId);
        String key = null;
        Integer value = null;
        try{
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){

                if(eventType == XmlPullParser.START_TAG){
                    if(parser.getName().equals("entry")){
                        key = parser.getAttributeValue(null, "key");
                        if(key == null){
                            parser.close();
                            return null;
                        }
                    }
                } else if(eventType == XmlPullParser.END_TAG){
                    if(parser.getName().equals("entry")){
                        map.put(key, value);
                        key = null;
                        value = null;
                    }
                } else if(eventType == XmlPullParser.TEXT){
                    if(key != null){
                        value = Integer.parseInt(parser.getText());
                    }
                }

                eventType = parser.next();
            }
        } catch (Exception e){
            Log.e(TAG, "Got exception trying to parse xml to HashMap. Exception: ", e);
            return null;
        }
        return map;
    }

}
