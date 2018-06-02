package novel.spider.entitys;

import java.io.Serializable;

/**
 * 章节详细内容实体
 */
public class ChapterDetail implements Serializable{

    //标题
    private String title;
    //链接
    private String content;
    //前一章地址
    private String prev;
    //后一章地址
    private String next;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChapterDetail)) return false;

        ChapterDetail that = (ChapterDetail) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (prev != null ? !prev.equals(that.prev) : that.prev != null) return false;
        return next != null ? next.equals(that.next) : that.next == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (prev != null ? prev.hashCode() : 0);
        result = 31 * result + (next != null ? next.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChapterDetail{" +
                "title='" + title + '\'' +
                ", content='" + content.substring(0,30) + '\'' +
                ", prev='" + prev + '\'' +
                ", next='" + next + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }


    public static void main(String[] args) {
        ChapterDetail chapterDetail = new ChapterDetail();
        chapterDetail.setContent("内容");
        System.out.println(chapterDetail);
    }

}
