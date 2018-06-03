package novel.spider.entitys;

import java.io.Serializable;
import java.util.Date;

/**
 * 小说实体
 */
public class Novel implements Serializable{
    /** 小说书名 */
    private String name;
    /** 作者名字 */
    private String author;
    /** 小说链接 */
    private String url;
    /** 小说类别：如：武侠修真、都市言情 */
    private String type;
    /** 小说最新章节/最后一章名字 */
    private String lastUpdateChapterName;
    /** 小说最新章节/最后一章的地址 */
    private String lastUpdateChapterUrl;
    /** 小说最后更新的时间 */
    private Date lastUpdateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastUpdateChapterName() {
        return lastUpdateChapterName;
    }

    public void setLastUpdateChapterName(String lastUpdateChapterName) {
        this.lastUpdateChapterName = lastUpdateChapterName;
    }

    public String getLastUpdateChapterUrl() {
        return lastUpdateChapterUrl;
    }

    public void setLastUpdateChapterUrl(String lastUpdateChapterUrl) {
        this.lastUpdateChapterUrl = lastUpdateChapterUrl;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /** 小说更新状态：1、连载  2、完结 */
    private int status;
    /** 书名的首字母 */
    private char firstLetter;
    /** 小说平台ID */
    private int platformId;
    /** 小说存储到数据库的时间 */
    private Date addTime;

    public Novel(){

    }

    @Override
    public String toString() {
        return "Novel{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", lastUpdateChapterName='" + lastUpdateChapterName + '\'' +
                ", lastUpdateChapterUrl='" + lastUpdateChapterUrl + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", status=" + status +
                ", firstLetter=" + firstLetter +
                ", platformId=" + platformId +
                ", addTime=" + addTime +
                '}';
    }
}
