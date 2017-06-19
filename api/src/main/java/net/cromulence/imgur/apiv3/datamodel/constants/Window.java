package net.cromulence.imgur.apiv3.datamodel.constants;

public enum Window {
    DAY,
    WEEK,
    MONTH,
    YEAR,
    ALL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();

    }
}	
