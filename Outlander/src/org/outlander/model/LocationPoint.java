/*
 * Copyright 2011 Greg Milette and Adam Stroud
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.outlander.model;

import android.location.Location;

/**
 * Bean to hold point information.
 * 
 * @author Adam Stroud &#60;<a
 *         href="mailto:adam.stroud@gmail.com">adam.stroud@gmail.com</a>&#62;
 */
public class LocationPoint {
    private double latitude;
    private double longitude;
    private double altitude = 0;
    private float  accuracy = -1;
    private long   time     = System.currentTimeMillis();
    private String provider;

    public LocationPoint() {
    }

    public LocationPoint(final double lat, final double lon) {
        latitude = lat;
        longitude = lon;
    }

    public LocationPoint(final Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        if (location.hasAccuracy()) {
            accuracy = location.getAccuracy();
        }
        if (location.hasAltitude()) {
            altitude = location.getAltitude();
        }

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(final float accuracy) {
        this.accuracy = accuracy;
    }

    public long getTime() {
        return time;
    }

    public void setTime(final long time) {
        this.time = time;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(final double altitude) {
        this.altitude = altitude;
    }
}
