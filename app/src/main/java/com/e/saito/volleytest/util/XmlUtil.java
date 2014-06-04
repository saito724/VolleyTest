package com.e.saito.volleytest.util;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.xpath.XPathException;

/**
 * Created by e.saito on 2014/06/04.
 */
public class XmlUtil {



  /*
        method for debug  */
    public static void  parseTest (String xmlContents) {
        XmlPullParser xmlPullParser = Xml.newPullParser();

        try{
            xmlPullParser.setInput(new StringReader(xmlContents));
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }

        try {
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("XmlPullParser", "START_DOCUMENT");
                        break;
                    case  XmlPullParser.END_DOCUMENT:
                        Log.d("XmlPullParser","END_DOCUMENT");

                        break;
                    case  XmlPullParser.START_TAG:
                        Log.d("XmlPullParser","START_TAG: " + xmlPullParser.getName());
                        break;
                    case  XmlPullParser.END_TAG:
                        Log.d("XmlPullParser","END_TAG: "+ xmlPullParser.getName());
                        break;
                    case  XmlPullParser.TEXT:
                        Log.d("XmlPullParser","TEXT: <"+ xmlPullParser.getName() + ">" + xmlPullParser.getText());
                        break;
                    default:
                        Log.d("XmlPullParser","no mach  Event type:"+ Integer.toString(eventType));
                }
                eventType = xmlPullParser.next();
            }
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }




    }


}
