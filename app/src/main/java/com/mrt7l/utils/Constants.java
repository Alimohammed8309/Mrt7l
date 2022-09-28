package com.mrt7l.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
    /*variable declaration*/
    public static final String NA = "N/A";
    public static String MeOnly= "MEONLY";
    public static String newOther= "newOther";
    public static String currentOther= "currentOther";
    public static final int AVATARREQUEST = 1;

    /*Date format*/
    public static class DateFormat {
        public static String dd_MM = "dd-MMM";
        public static String dd_MM_yyyy = "dd - MMM - yyyy";
        public static String datFormat = "yyyy-MM-dd";

        public static final SimpleDateFormat DAY_MONTH_FORMATTER = new SimpleDateFormat(dd_MM, Locale.ENGLISH);
        public static final SimpleDateFormat DAY_MONTH_YEAR_FORMATTER= new SimpleDateFormat(dd_MM_yyyy, Locale.ENGLISH);
        public static final SimpleDateFormat FilterFormat= new SimpleDateFormat(datFormat, Locale.ENGLISH);

    }
    /*intent data*/
    public static class intentdata
    {
        public static String CARDDETAIL="carddetail";
        public static String TRAVELLERNAME="TravellerName";
        public static String TYPECOACH="typecoach";
        public static String PRICE="price";
        public static String HOLD="hold";
        public static String PACKAGE="package";
        public static String OFFER="offer";
        public static String TRIP_KEY="trip_key";
        public static String SEARCH_BUS="search_bus";
        public static String PACKAGE_NAME="package_name";
        public static String CARDFLAG="cardflag";
    }

}
