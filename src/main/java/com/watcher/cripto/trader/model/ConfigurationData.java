package com.watcher.cripto.trader.model;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "configurations")
@Entity
public class ConfigurationData {

    String name;
    String value;
    String status;
    Date date;
    Date udate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }
}
