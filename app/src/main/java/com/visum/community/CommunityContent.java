package com.visum.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CommunityContent {

    /**
     * An array of sample (dummy) items.
     */
    private static final List<CommunityItem> ITEMS = new ArrayList<CommunityItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, CommunityItem> ITEM_MAP = new HashMap<String, CommunityItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createCommunityItem(i));
        }
    }

    private static void addItem(CommunityItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static CommunityItem createCommunityItem(int position) {
        return new CommunityItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static List<CommunityItem> getItems() {
        return ITEMS;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class CommunityItem {
        public final String id;
        public final String name;
        public final String description;

        public CommunityItem(String id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
