package com.watcher.cripto.trader.model;

import java.math.BigDecimal;
import java.util.Date;

public class Alert {
        String name;
        BigDecimal lastPrice;
        String symbol;
        String type;

        public Alert(String name, BigDecimal lastPrice, String symbol, String type) {
                this.name = name;
                this.lastPrice = lastPrice;
                this.symbol = symbol;
                this.type = type;
        }

        public Alert(BigDecimal last){
                lastPrice = last;
        }
        public static Alert buidDefault(BigDecimal price){
                return new Alert(price);
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public BigDecimal getLastPrice() {
                return lastPrice;
        }

        public void setLastPrice(BigDecimal lastPrice) {
                this.lastPrice = lastPrice;
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
}