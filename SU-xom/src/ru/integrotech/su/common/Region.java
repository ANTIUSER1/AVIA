package ru.integrotech.su.common;

import java.util.Objects;

public class Region {

    public static Region of(String regionCode) {
        Region result = new Region();
        result.setRegionCode(regionCode);
        return result;
    }

    private String regionCode;

    private Region(String regionCode) {
        this.regionCode = regionCode;
    }

    private Region() {
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {

        if (regionCode != null) {
            regionCode = regionCode.toUpperCase();
        }

        this.regionCode = regionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(regionCode, region.regionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionCode);
    }
}

