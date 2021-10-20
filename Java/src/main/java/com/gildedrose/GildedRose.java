package com.gildedrose;

import java.util.Arrays;
import java.util.List;

class GildedRose {

    Item[] items;
    public static final int MAX_QUALITY = 50;
    public static final int SULFURAS_QUALITY = 80;
    public static final String AGED_BRIE_NAME = "Aged Brie";
    public static final String BACKSTAGE_PASS_NAME = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_NAME = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED_BASE_NAME = "Conjured";
    protected static final List<String> SPECIAL_ITEMS_LIST = Arrays.asList(
        GildedRose.AGED_BRIE_NAME,
        GildedRose.BACKSTAGE_PASS_NAME,
        GildedRose.SULFURAS_NAME);

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            items[i] = quality(items[i]);
            items[i] = sellIn(items[i]);
        }
    }

    private Item quality(Item item) {
        if (!GildedRose.SPECIAL_ITEMS_LIST.contains(item.getName())) {
            decrementRegularItem(item);
        } else if (!item.getName().equals(GildedRose.SULFURAS_NAME)) {
            incrementQuality(item);
            if (item.getName().equals(GildedRose.BACKSTAGE_PASS_NAME)) {
                if (item.getSellIn() < 11) {
                    incrementQuality(item);
                }

                if (item.getSellIn() < 6) {
                    incrementQuality(item);
                }
            }
        }

        return item;
    }

    private void decrementQuality(Item item) {
        if (item.getQuality() > 0) {
            item.decrementQuality();
        }
    }

    private void incrementQuality(Item item) {
        if (item.getQuality() < GildedRose.MAX_QUALITY) {
            item.incrementQuality();
        }
    }

    private Item sellIn(Item item) {
        if (!item.getName().equals(GildedRose.SULFURAS_NAME)) {
            item.decrementSellIn();
        }

        // quality changes based on sellIn
        if (item.getSellIn() < 0) {
            if (!GildedRose.SPECIAL_ITEMS_LIST.contains(item.getName())) {
                decrementRegularItem(item);
            } else if (item.getName().equals(GildedRose.AGED_BRIE_NAME)) {
                item.incrementQuality();
            } else if (item.getName().equals(GildedRose.BACKSTAGE_PASS_NAME)) {
                item.setQuality(0);
            }
        } else if (item.getSellIn() == 0 && item.getName().equals(GildedRose.BACKSTAGE_PASS_NAME)) {
            item.setQuality(0);
        }
        return item;
    }

    private void decrementRegularItem(Item item) {
        decrementQuality(item);
        if (item.getName().contains(GildedRose.CONJURED_BASE_NAME)) {
            decrementQuality(item);
        }
    }
}
