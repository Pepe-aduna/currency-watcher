package com.watcher.cripto.trader.model;

import jakarta.persistence.*;
import org.json.JSONObject;


import java.util.Date;

@Table(name = "price_track")
@Entity
public class TrackData {
    private Double price;
    private Long time;
    private Date date;
    private String symbol;
    private String type;
    private String kind;
    private Integer variation;
    private String direction;
    private Long millis_diff;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long t_id;

    public TrackData(){
    }

    public TrackData(JSONObject t){
        price = Double.valueOf(t.getString("price"));
        time = t.getLong("closeTime");

    }

    public TrackData(String symbol){
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public Long getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getType() {
        return type;
    }

    public String getKind() {
        return kind;
    }

    public Integer getVariation() {
        return variation;
    }

    public String getDirection() {
        return direction;
    }

    public Long getMillis_diff() {
        return millis_diff;
    }

    public Long getT_id() {
        return t_id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setVariation(Integer variation) {
        this.variation = variation;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setMillis_diff(Long millis_diff) {
        this.millis_diff = millis_diff;
    }

    public void setT_id(Long t_id) {
        this.t_id = t_id;
    }
}
