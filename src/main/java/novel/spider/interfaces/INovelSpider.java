package novel.spider.interfaces;

import novel.spider.entitys.Novel;

import java.util.Iterator;
import java.util.List;

/**
 * 爬取某个站点的小说列表
 */
public interface INovelSpider {
    public static final int MAX_TRY_TIMES = 3;
    /**
     * 给我一个url，我就给你一个小说实体的集合
     * @param url
     * @param maxTryTimes 网页下载的最大次数，允许失败重试的次数
     * @return
     */
    public List<Novel> getNovel(String url, Integer maxTryTimes);

    /**
     * 判断是否还有下一页可以扫描
     * @return
     */
    public boolean hasNext();

    public String next();

    /**
     *
     * @param firstPage
     * @param maxTryTimes 网页下载的最大次数，允许失败重试的次数
     * @return
     */
    public Iterator<List<Novel>> iterator(String firstPage,Integer maxTryTimes);

}
