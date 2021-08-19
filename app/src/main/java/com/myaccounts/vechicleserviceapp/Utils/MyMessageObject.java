package com.myaccounts.vechicleserviceapp.Utils;

/**
 * Created by myaccounts on 2/14/2017.
 */

public class MyMessageObject {
    private static String MyTitle = "";
    private static String MyMessage = "";
    private static Enums.MyMesageType MessageType = Enums.MyMesageType.YesNo;

    public static String getMyTitle() {
        String strTitle = MyTitle;
        MyTitle = "";
        return strTitle;
    }

    public static void setMyTitle(String myTitle) {
        MyTitle = myTitle;
    }

    public static String getMyMessage() {
        String strMessage = MyMessage;
        MyMessage = "";
        return strMessage;
    }

    public static void setMyMessage(String myMessage) {
        MyMessage = myMessage;
    }

    public static Enums.MyMesageType getMessageType() {
        return MessageType;
    }

    public static void setMessageType(Enums.MyMesageType messageType) {
        MessageType = messageType;
    }
}

