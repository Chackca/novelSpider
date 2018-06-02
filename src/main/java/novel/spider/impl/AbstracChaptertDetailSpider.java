package novel.spider.impl;

import novel.spider.NovelSiteEnum;
import novel.spider.entitys.ChapterDetail;
import novel.spider.interfaces.IChapterDetailSpider;
import novel.spider.util.NovelSpiderUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

public abstract class AbstracChaptertDetailSpider extends AbstractSpider implements IChapterDetailSpider{

    @Override
    public ChapterDetail getChapterDetail(String url) {
        try {
            String result = super.crawl(url);
            //jsoup不支持解析&nbsp这种字符
            result = result.replace("&nbsp;","  ")
                    .replace("<br />","\n")
                    .replace("<br/>","\n");
            Document doc = Jsoup.parse(result);
            doc.setBaseUri(url);
            Map<String, String> contexts = NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl(url));

            //拿标题内容
            String titleSelector = contexts.get("chapter-detail-title-selector");
            String[] splits = titleSelector.split("\\,");
            splits = parseSelector(splits);
            ChapterDetail detail = new ChapterDetail();
            Elements docSelectCheck = doc.select(splits[0]);
            if (docSelectCheck.size() == 0) {
                System.out.println("您配置的章节的CSS查询器可能无法正确匹配到内容，请检查后继续");
                return null;
            }
            detail.setTitle(docSelectCheck.get(Integer.parseInt(splits[1])).text());

            //拿章节内容
            String contentSelector = contexts.get("chapter-detail-content-selector");
            docSelectCheck = doc.select(contentSelector);
            if (docSelectCheck.size() == 0) {
                System.out.println("您配置的内容的CSS查询器可能无法正确匹配到内容，请检查后继续");
                return null;
            }
            detail.setContent(docSelectCheck.first().text());

            //拿前一章的内容
            String prevSelector = contexts.get("chapter-detail-prev-selector");
            splits = prevSelector.split("\\,");
            splits = parseSelector(splits);
            docSelectCheck = doc.select(splits[0]);
            if (docSelectCheck.size() == 0) {
                System.out.println("您配置的前一章的CSS查询器可能无法正确匹配到内容，请检查后继续");
                return null;
            }
            detail.setPrev(docSelectCheck.get(Integer.parseInt(splits[1])).absUrl("href"));

            //拿后一章的内容
            String nextSelector = contexts.get("chapter-detail-next-selector");
            splits = nextSelector.split("\\,");
            splits = parseSelector(splits);
            docSelectCheck = doc.select(splits[0]);
            if (docSelectCheck.size() == 0) {
                System.out.println("您配置的后一章的CSS查询器可能无法正确匹配到内容，请检查后继续");
                return null;
            }
            detail.setNext(docSelectCheck.get(Integer.parseInt(splits[1])).absUrl("href"));

            return detail;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 处理具体元素的下标索引
     */
    private String[] parseSelector(String[] splits) {
        //这个方法可以使得Spider-Rule.xml中的值无需手动默认添加，0
        String[] newSplits = new String[2];
        if (splits.length == 1) {
            newSplits[0] = splits[0];
            newSplits[1] = "0";
            return newSplits;
        } else {
            return splits;
        }
    }
}
