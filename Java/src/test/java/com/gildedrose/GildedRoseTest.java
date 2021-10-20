package com.gildedrose;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    private Item[] items = null;
    private GildedRose gildedRose = null;

    @AfterEach
    void tearDown() {
        items = null;
    }

    @Test
    void testAddOneItem() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals("foo", gildedRose.items[0].getName());
    }

    @Test
    void testUpdateQualityWithEmptyArray() {
        items = new Item[0];
        gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(0, items.length);
    }

    @Test
    void testCheckQualityOfSulfurasAfterOneDay() {
        items = DummyListCreator.addItemToItemsArray(new Item[0], DummyListCreator.createSulfuras());
        gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(GildedRose.SULFURAS_NAME, items[0].getName());
        assertEquals(GildedRose.SULFURAS_QUALITY, items[0].getQuality());
    }

    @Test
    void testCheckQualityOfSulfurasSeveralDays() {
        items = DummyListCreator.addItemToItemsArray(new Item[0], DummyListCreator.createSulfuras());
        gildedRose = new GildedRose(items);
        for (int i = 0; i < 65; i++) {
            gildedRose.updateQuality();
        }
        assertEquals(GildedRose.SULFURAS_NAME, items[0].getName());
        assertEquals(GildedRose.SULFURAS_QUALITY, items[0].getQuality());
    }

    @Test
    void testCheckQualityOfAgedBrieAfterOneDay() {
        items = DummyListCreator.addItemToItemsArray(new Item[0], DummyListCreator.createAgedBrie());
        gildedRose = new GildedRose(items);
        int brieInitialQuality = items[0].getQuality();
        gildedRose.updateQuality();
        assertEquals(GildedRose.AGED_BRIE_NAME, items[0].getName());
        assertEquals(brieInitialQuality + 1, items[0].getQuality());
    }

    @Test
    void testCheckQualityOfAgedBrieSeveralDays() {
        items = DummyListCreator.addItemToItemsArray(new Item[0], DummyListCreator.createAgedBrie());
        gildedRose = new GildedRose(items);
        int brieInitialQuality = items[0].getQuality();
        int brieInitialSellIn = items[0].getSellIn();

        int maxIterations = 20;
        for (int i = 0; i < maxIterations; i++) {
            gildedRose.updateQuality();
        }

        assertEquals(GildedRose.AGED_BRIE_NAME, items[0].getName());

        // items degrade twice as fast after due date
        int brieFinalQuality = brieInitialQuality + maxIterations + ( - Math.min(brieInitialSellIn - maxIterations, 0));
        assertEquals(Math.min(brieFinalQuality, GildedRose.MAX_QUALITY), items[0].getQuality());
    }

    @ParameterizedTest
    @CsvSource({
        "20, 15, 16",
        "8, 15, 17",
        "2, 15, 18",
        "0, 15, 0"
    })
    void testCheckQualityOfBackstagePassInSeveralOccasions(int initialSellIn, int initialQuality, int expectedQuality) {
        items = DummyListCreator.addItemToItemsArray(new Item[0], DummyListCreator.createBackstagePass(initialSellIn, initialQuality));
        gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertEquals(GildedRose.BACKSTAGE_PASS_NAME, items[0].getName());
        assertEquals(expectedQuality, items[0].getQuality());
    }

    @Test
    void testQualityDegradationOfGenericObjectAfterOneDay() {
        items = DummyListCreator.createItemsList(1, 0, 0);
        int initialQuality = 1;
        items[0].setQuality(initialQuality);

        gildedRose = new GildedRose(items);
        gildedRose.updateQuality();

        assertEquals(initialQuality - 1, items[0].getQuality());
    }

    @Test
    void testQualityDegradationOfGenericObjectAfterSeveralDays() {
        items = DummyListCreator.createItemsList(1, 0, 0);
        items[0].setSellIn(1);
        items[0].setQuality(1);

        gildedRose = new GildedRose(items);
        int maxIterations = 20;
        for (int i = 0; i < maxIterations; i++) {
            gildedRose.updateQuality();
        }

        assertEquals(0, items[0].getQuality());
    }

    @Test
    void testQualityDegradationOfConjuredObjectAfterOneDay() {
        items = DummyListCreator.addItemToItemsArray(new Item[0], DummyListCreator.createConjuredObject());
        int initialQuality = 7;
        items[0].setQuality(initialQuality);

        gildedRose = new GildedRose(items);
        gildedRose.updateQuality();

        assertEquals(initialQuality - 2, items[0].getQuality());
    }

    @Test
    void testToString() {
        items = DummyListCreator.createItemsList(3, 5, 2);
        for (Item item: items) {
            assertTrue(item.toString().contains(item.getName()));
        }
    }
}
