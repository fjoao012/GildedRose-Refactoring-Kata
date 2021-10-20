package com.gildedrose;

public class Item {

    private String name;
    private int sellIn;
    private int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return sellIn;
    }

    void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    void setQuality(int quality) {
        this.quality = quality;
    }

    void decrementQuality() {
        this.quality -= 1;
    }

    void incrementQuality() {
        this.quality += 1;
    }

    public void decrementSellIn() {
        this.sellIn -= 1;
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
