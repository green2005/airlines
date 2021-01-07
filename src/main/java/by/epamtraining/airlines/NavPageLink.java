package by.epamtraining.airlines;


import java.util.ArrayList;
import java.util.List;

public class NavPageLink {
    private String name;
    private int pageNo;
    private boolean active;
    private boolean selected;

    public static final int RECORDS_PER_PAGE = 7;

    public static int getPageCount(long recordCount) {
        return (int) Math.ceil((double) recordCount / RECORDS_PER_PAGE);
    }

    public static int getCurrentPageNo(Integer pageNo, long recordCount) {
        if (pageNo == null) {
            pageNo = 1;
        }
        int pageCount = NavPageLink.getPageCount(recordCount);
        if (pageNo >= pageCount) {
            pageNo = pageCount - 1;
        }
        if (pageNo < 0) {
            pageNo = 0;
        }
        return pageNo;
    }

    public static List<NavPageLink> getPageLinks(long recordCount, int currentPage, String urlPart) {
        List<NavPageLink> pages = new ArrayList<>();
        int totalPageCount = getPageCount(recordCount);
        if (totalPageCount <= 1) {
            return pages;
        }
        for (int i = 0; i < totalPageCount; i++) {
            NavPageLink lnk = new NavPageLink();
            lnk.active = true;
            lnk.selected = i == currentPage;
            lnk.name = Integer.toString(i + 1); //page counting starts from 1 for users
            lnk.pageNo = i;
            pages.add(lnk);
        }
        return pages;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
