package by.epamtraining.airlines.helpers;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.epamtraining.airlines.domain.News;
import by.epamtraining.airlines.exceptions.IncorrectJSONException;
import org.json.*;

public class NewsDataProcessor {
    private static final String INCORRECT_JSON_MSG = "Incorrect JSON";

    public List<News> getVKData(String jsonStr) throws IncorrectJSONException {
        List<News> resultList = new ArrayList();
        JSONObject mainJO;
        try {
            mainJO = new JSONObject(jsonStr);
        } catch (JSONException e) {
            throw new IncorrectJSONException(INCORRECT_JSON_MSG, e);
        }
        JSONArray array = mainJO.optJSONObject("response").optJSONArray("items");
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.optJSONObject(i);
                if (item == null) {
                    throw new IncorrectJSONException(INCORRECT_JSON_MSG);
                }
                News post = new News();
                post.setId(item.optInt("id") + "");
                post.setTitle(item.optString("text"));
                post.setUrl(API.getPostUrl(post.getId()));
                fillAttachmentsUrl(post, item);
                post.setDate(new SimpleDateFormat("YYYY-MM-dd-HH:mm:ss").format(new Date(Integer.parseInt(item.optString("date")) * 1000L)));
                resultList.add(post);
            }
        } else {
            throw new IncorrectJSONException(INCORRECT_JSON_MSG);
        }
        return resultList;
    }

    private void fillAttachmentsUrl(News post, JSONObject item) {
        JSONArray attachments = item.optJSONArray("attachments");
        if (attachments != null) {
            for (int i = 0; i < attachments.length(); i++) {
                JSONObject jo = attachments.optJSONObject(i);
                JSONObject jVideo = jo.optJSONObject("video");
                JSONObject jPhoto = jo.optJSONObject("photo");
                if (jVideo != null) {
                    post.setImgUrl(jVideo.optString("photo_320"));
                    post.setVideoUrl(API.getVideoUrl(jVideo.optString("id")));
                }
                if (jPhoto != null) {
                    //jPhoto.optJSONArray("sizes").optJSONObject(3).optString("width")
                    //jPhoto.optJSONArray("sizes").optJSONObject(3).optString("url")
                    JSONArray photos = jPhoto.optJSONArray("sizes");
                    for (int j = 0; j < photos.length() - 1; j++) {
                        JSONObject jItem = photos.optJSONObject(j);
                        if ("320".equalsIgnoreCase(jItem.optString("width"))) {
                            post.setImgUrl(jItem.optString("url"));
                            break;
                        }
                    }
                }
            }
        }

        // item.optJSONArray("attachments").optJSONObject(0).optJSONObject("video").optString("photo_320")

    }
}