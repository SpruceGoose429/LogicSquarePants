package com.example.logicSquarePants.data;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by GiyeopHwang on 2/28/14.
 */

public class XMLParser {

    public static final String ROW_COUNT = "rowCount";
    public static final String COL_COUNT = "colCount";
    public static final String ROW = "row";
    public static final String NODE = "node";

    public void parseDataFromXML(Resources res, int xml) {

        DataModel model = DataModel.getDataModel();
        boolean tempArr[][] = null;
        int rowIndex = -1;

        try {

            XmlResourceParser parser = res.getXml(xml);
            parser.next();

            int eventType = parser.getEventType();
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if(eventType == XmlResourceParser.START_TAG) {
                    if(parser.getName().equals(ROW_COUNT)) {
                        parser.next();
                        model.setRowCount(Integer.parseInt(parser.getText()));
                    } else if(parser.getName().equals(COL_COUNT)) {
                        parser.next();
                        model.setColumnCount(Integer.parseInt(parser.getText()));
                        tempArr = new boolean[model.getRowCount()][model.getColCount()];
                    } else if(parser.getName().equals(ROW)) {
                        rowIndex++;
                    } else if(parser.getName().equals(NODE)) {
                        parser.next();
                        tempArr[rowIndex][Integer.parseInt(parser.getText())] = true;
                    }
                }
                eventType = parser.next();
            }
        } catch(IOException e) {

        } catch (XmlPullParserException e) {

        }

        model.setNodes(tempArr);
    }
}
