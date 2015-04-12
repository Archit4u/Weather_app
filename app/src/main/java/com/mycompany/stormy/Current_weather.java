package com.mycompany.stormy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by vaio on 03-04-2015.
 */
public class Current_weather {
    private String mIcon;
    private long mTime;
    private double mTemp;
    private double mHumidity;
    private double mPercipchance;
    private String mSummary;
    private String mTimezone;

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public String getIcon() {
        return mIcon;
    }
    public int getIconid()
    {
        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        int icon=R.mipmap.clear_day;

        if(mIcon.equals("clear-day"))
        {
            icon=R.mipmap.clear_day;
        }
        else if(mIcon.equals("clear-night"))
        {
            icon=R.mipmap.clear_night;
        }
        else if(mIcon.equals("rain"))
        {
            icon=R.mipmap.rain;
        }
        else if(mIcon.equals("snow"))
        {
            icon=R.mipmap.snow;
        }
        else if(mIcon.equals("sleet"))
        {
            icon=R.mipmap.sleet;
        }
        else if(mIcon.equals("wind"))
        {
            icon=R.mipmap.wind;
        }
        else if(mIcon.equals("fog"))
        {
            icon=R.mipmap.fog;
        }
        else if(mIcon.equals("cloudy"))
        {
            icon=R.mipmap.cloudy;
        }
        else if(mIcon.equals("partly-cloudy-day"))
        {
            icon=R.mipmap.partly_cloudy;
        }
        else if(mIcon.equals("partly-cloudy-night"))
        {
            icon=R.mipmap.cloudy_night;
        }
return icon;

    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }
    public String getFormattedTime()
    {
        SimpleDateFormat formatter=new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        Date date=new Date(getTime()*1000);
        String timestring=formatter.format(date);
        return timestring;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemp() {
        return (int)Math.round(mTemp);
    }

    public void setTemp(double temp) {
        mTemp = temp;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPercipchance() {
        return (int)Math.round(mPercipchance*100);
    }

    public void setPercipchance(double percipchance) {
        mPercipchance = percipchance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
