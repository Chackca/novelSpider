package novel.spider.impl.novel;

import novel.spider.NovelSiteEnum;
import novel.spider.entitys.Novel;
import novel.spider.impl.AbstractSpider;
import novel.spider.interfaces.INovelSpider;
import novel.spider.util.NovelSpiderUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 一个抽象的小说列表抓取，已经实现了解析tr元素的方法
 */
public abstract class AbstractNovelSpider extends AbstractSpider implements INovelSpider{
    protected Element nextPageElement;
    //下一页的url
    protected String nextPage;
    /**
     * 默认的抓取方法，最多会尝试{@link INovelSpider#MAX_TRY_TIMES}次下载
     * @param url
     * @return
     * @throws Exception
     */
    protected Elements getsTr(String url) throws Exception{
        return getsTr(url,INovelSpider.MAX_TRY_TIMES);
    }

    /**
     * 以指定次数的形式去解析对应网页，如果为null，默认为{@link INovelSpider#MAX_TRY_TIMES}次
     * @param url
     * @param maxTryTimes
     * @return
     * @throws Exception
     */
    protected Elements getsTr(String url,Integer maxTryTimes) throws Exception{
        maxTryTimes = maxTryTimes == null ? INovelSpider.MAX_TRY_TIMES : maxTryTimes;
        Elements trs = null;
        for (int i = 0 ; i < maxTryTimes; i++){
            try{
                String result = super.crawl(url);
                Map<String, String> context = NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl(url));
                String novelSelector = context.get("novel-selector");
                if (novelSelector == null)  throw new RuntimeException("url="+url+"目前不支持抓取小说列表");
                Document doc = Jsoup.parse(result);
                doc.setBaseUri(url);
                trs = doc.select(novelSelector);

                String nextPageSelector = NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl(url)).get("novel-next-page-selector");
                //如果配置文件中有设置这一项
                if (nextPageSelector!=null){
                    Elements nextPageElements = doc.select(nextPageSelector);
                    nextPageElement = nextPageElements == null ? null : nextPageElements.first();

                    if (nextPageElement != null){
                        nextPage = nextPageElement.absUrl("href");
                    }else {
                        nextPage = "";
                    }
                }

                return trs;
            }catch (Exception e){

            }
        }throw new RuntimeException(url+"尝试了"+maxTryTimes+"依然下载失败");
    }
    /*/@Override
    public List<Novel> getNovel(String url) {
        return null;
    }*/

    @Override
    public boolean hasNext() {
        return !nextPage.isEmpty();
    }

    @Override
    public String next() {
        return nextPage;
    }

    @Override
    public Iterator<List<Novel>> iterator(String firstPage,Integer maxTryTimes) {
        nextPage = firstPage;
        return new NovelIterator(maxTryTimes);
    }

    /**
     * 专门用于对小说网站书籍列表抓取
     */
    private class NovelIterator implements Iterator<List<Novel>>{
        private Integer maxTryTimes;
        public NovelIterator(Integer maxTryTimes){
            this.maxTryTimes = maxTryTimes;
        }
        @Override
        public boolean hasNext() {
            return AbstractNovelSpider.this.hasNext();
        }

        @Override
        /**
        调用此next，返回下一页的novelList，也就是下一页的目录所有书的信息
         */
        public List<Novel> next() {
            //此处的getNovel()方法其具体逻辑会再调用回到本类中的getsTr，其内部就会把本类对象的nextPage设置为下一页
            List<Novel> novelList = getNovel(nextPage,maxTryTimes);
            return novelList;
        }
    }
}
