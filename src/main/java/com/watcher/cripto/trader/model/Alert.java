package com.watcher.cripto.trader.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "alerts")
@Entity
public class Alert {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id;
        String name;
        String symbol;
        String type;
        String message;
        String origin;
        String notification_channel;
        Double percent;
        BigDecimal openPrice;
        BigDecimal deltaAmount;
        @Type(JsonType.class)
        @Column(name = "prices", columnDefinition = "json")
        List<String> prices;
        Integer reminders;
        Date date;
        String status;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getSymbol() {
                return symbol;
        }

        public void setSymbol(String symbol) {
                this.symbol = symbol;
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public String getOrigin() {
                return origin;
        }

        public void setOrigin(String origin) {
                this.origin = origin;
        }

        public String getNotification_channel() {
                return notification_channel;
        }

        public void setNotification_channel(String notification_channel) {
                this.notification_channel = notification_channel;
        }

        public Double getPercent() {
                return percent;
        }

        public void setPercent(Double percent) {
                this.percent = percent;
        }

        public BigDecimal getOpenPrice() {
                return openPrice;
        }

        public void setOpenPrice(BigDecimal openPrice) {
                this.openPrice = openPrice;
        }

        public BigDecimal getDeltaAmount() {
                return deltaAmount;
        }

        public void setDeltaAmount(BigDecimal deltaAmount) {
                this.deltaAmount = deltaAmount;
        }

        public List<String> getPrices() {
                return prices;
        }

        public void setPrices(List<String> prices) {
                this.prices = prices;
        }

        public Integer getReminders() {
                return reminders;
        }

        public void setReminders(Integer reminders) {
                this.reminders = reminders;
        }

        public Date getDate() {
                return date;
        }

        public void setDate(Date date) {
                this.date = date;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }
}