package ru.integrotech.su.common;

import java.util.Objects;

public class Region {

    public static Region of(String regionCode) {
        return new Region(regionCode);
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

