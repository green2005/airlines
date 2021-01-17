package by.epamtraining.airlines.helpers;

import java.util.Locale;

public class API {
    private static final String VK_FEED_URL =
            "https://api.vk.com/method/wall.get?owner_id=%s&count=%s&filter=owner&extended=1&v=%s&access_token=%s&offset=%d";
    private static final String VK_WALL_ID = "-9868686";
    private static final int DATA_LIMIT = 10;
    private static final String VK_VERSION = "5.80";
    private static final String ACCESS_TOKEN = "4482471944824719448247196344c48d2544482448247191fceab0c1c9b6487ef990f56";
    private static final String VK_POST_URL = "https://vk.com/wall%s_%s";
    private static final String VK_VIDEO_URL = "https://vk.com/video%s_%s";


    private API() {

    }

    public static String getPostUrl(String postId) {
        return String.format(VK_POST_URL, VK_WALL_ID, postId);
    }

    public static String getVideoUrl(String videoId) {
        return String.format(VK_VIDEO_URL, VK_WALL_ID, videoId);
    }

    public static String getVKUrl(int pageNo) {
        return String.format(Locale.ROOT, VK_FEED_URL, VK_WALL_ID, DATA_LIMIT, VK_VERSION, ACCESS_TOKEN, pageNo * DATA_LIMIT);
    }

}
