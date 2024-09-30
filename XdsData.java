package com.example.xdsapp.model;

public class XdsData {
    private String location;
    private String marketTime;
    private String ccy;
    private String rateFixingIndex;
    private String indexTerm;
    private String name; // curveId
    private String term;
    private String midRate;
    private String spread;

    // Getters and setters

    // toString method to display data
    @Override
    public String toString() {
        return "XdsData{" +
                "location='" + location + '\'' +
                ", marketTime='" + marketTime + '\'' +
                ", ccy='" + ccy + '\'' +
                ", rateFixingIndex='" + rateFixingIndex + '\'' +
                ", indexTerm='" + indexTerm + '\'' +
                ", name='" + name + '\'' +
                ", term='" + term + '\'' +
                ", midRate='" + midRate + '\'' +
                ", spread='" + spread + '\'' +
                '}';
    }
}
