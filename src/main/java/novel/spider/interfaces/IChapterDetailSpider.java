package novel.spider.interfaces;

import novel.spider.entitys.Chapter;
import novel.spider.entitys.ChapterDetail;

import java.util.List;

public interface IChapterDetailSpider {

    //给我一个url，就给你一个对应网站的小说内容的章节实体
    public ChapterDetail getChapterDetail(String url);
}
