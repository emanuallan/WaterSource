package com.tristenallen.watersource.model;

import android.location.Location;

import java.util.Date;

/**
 * Created by Tristen on 2/25/2017.
 *
 * Holds information about a water source. Has an associated ID number and
 * holds the ID of the user who generated this report.
 */
public class SourceReport {
    private WaterType type;
    private WaterQuality quality;
    private int userID;
    private Location location;
    private Date timestamp;
    private int reportNumber;

    /**
     * Generates a new SourceReport at the given location
     * with the given userID as its author and the given
     * WaterType and WaterQuality.
     *
     * Report ID numbers are passed in, but should be autogenerated by
     * their holding data structure, by convention.
     *
     * All data in a SourceReport is considered immutable.
     */
    public SourceReport(int userID, Location location, WaterQuality quality,
                           WaterType type, int reportNumber) {
        this.userID = userID;
        this.location = location;
        this.quality = quality;
        this.type = type;
        this.reportNumber = reportNumber;
        this.timestamp = new Date();
    }

    /**
     * Returns the type of water considered in this report.
     * @return WaterType representing the type of water.
     */
    public WaterType getType() {
        return type;
    }

    /**
     * Returns the quality of water considered in this report.
     * @return WaterQuality representing the quality of water.
     */
    public WaterQuality getQuality() {
        return quality;
    }

    /**
     * Returns the given location of this report.
     * @return Location of the water source.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the time this report was generated.
     * @return Date representing the time this report was created.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the ID number of this report.
     * @return int representing the ID of this report.
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Returns the User ID who created this report.
     * @return int User ID who created this report.
     */
    public int getAuthorID() {
        return userID;
    }

    @Override
    public String toString() {
        return "Report Number: " + reportNumber + "\n" + "Location: "
                + location.getLatitude() + ", " + location.getLongitude() + "\n"
                + "Water type: " + type.toString() + "\n" + "Water quality: " + quality.toString()
                + "\n" + "Created: " + timestamp;
    }
}
