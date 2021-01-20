package by.epamtraining.airlines.domain;

import javax.persistence.*;

@Entity
public class NewsArticle {
    @Id
    private String vkId;

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;

    @Column(length = 2000)
    private String title;
    private String url;
    private String imgUrl;
    private String videoUrl;
    private String date;
    private int num;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

  //  public int getId() {
  //      return id;
  //  }

   // public void setId(int id) {
  //      this.id = id;
   // }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
