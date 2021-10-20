package com.gildedrose;

import com.github.javafaker.Faker;
import java.util.Locale;

/**
 * @author Ferreira Joao Alexandre (TT-CH/ERS-Av)
 */
public class DummyListCreator {
    private static Faker faker = new Faker(new Locale("pt"));

    /**
     * Creates a list with random stuff, none of that shall be one of the "special" items
     * @param quantity
     */
    public static Item[] createItemsList(int quantity, int sellInBaseline, int qualityBaseline) {
        if (quantity <= 0 || sellInBaseline < 0 || qualityBaseline < 0) {
            throw new IllegalArgumentException("Quantities must be higher than 0");
        }

        Item[] items = new Item[quantity];
        for (int i = 0; i < quantity; i++) {
            Item current = new Item(faker.pokemon().name(),
                faker.number().numberBetween(1, 500) + sellInBaseline,
                Math.min(faker.number().numberBetween(1, GildedRose.MAX_QUALITY) + qualityBaseline, GildedRose.MAX_QUALITY));
            items[i] = current;
        }

        return items;
    }

    public static Item[] addItemToItemsArray(Item[] items, Item item) {
        if (items == null) {
            items = new Item[0];
        }

        Item[] newItems = new Item[items.length + 1];
        System.arraycopy(items, 0, newItems, 0, items.length);

        newItems[newItems.length - 1] = item;
        return newItems;
    }

    public static Item createSulfuras() {
        return new Item(GildedRose.SULFURAS_NAME, 1000, GildedRose.SULFURAS_QUALITY);
    }

    public static Item createAgedBrie() {
        return new Item(GildedRose.AGED_BRIE_NAME,
            faker.number().numberBetween(2, 30),
            faker.number().numberBetween(1, 6));
    }

    public static Item createBackstagePass() {
        return new Item(GildedRose.BACKSTAGE_PASS_NAME,
            faker.number().numberBetween(20, 40),
            faker.number().numberBetween(5, 8));
    }

    public static Item createBackstagePass(int sellIn, int quality) {
        return new Item(GildedRose.BACKSTAGE_PASS_NAME,
            sellIn,
            quality);
    }

    public static Item createConjuredObject() {
        return new Item("Conjured " + faker.superhero().name(),
            faker.number().numberBetween(35, 75),
            faker.number().numberBetween(16, 40));
    }

    public static Item createConjuredObject(int sellIn, int quality) {
        return new Item("Conjured " + faker.superhero().name(),
            sellIn,
            quality);
    }
}
