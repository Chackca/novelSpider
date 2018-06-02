package novel.spider.util;

import novel.spider.NovelSiteEnum;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NovelSpiderUtil {

    public static final Map<NovelSiteEnum,Map<String,String>> CONTEXT_MAP = new HashMap<>();
    static{
        init();
    }
    public NovelSpiderUtil(){}

    private static void init(){
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new File("conf/Spider-Rule.xml"));
            Element rootElement = doc.getRootElement();//sites节点
            List<Element> sites = rootElement.elements("site");
            for (Element site : sites){
                List<Element> innerSites = site.elements();
                Map<String,String> innerMap = new HashMap<>();
                for (Element innerSite : innerSites){
                    String name = innerSite.getName(); //<title>
                    String text = innerSite.getTextTrim();  //title 对应的值
                    innerMap.put(name,text);
                }
                CONTEXT_MAP.put(NovelSiteEnum.getEnumByUrl(innerMap.get("url")),innerMap);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    /**
        拿到对应网站的解析规则
     */
    public static Map<String,String> getContext(NovelSiteEnum novelSiteEnum){
        return CONTEXT_MAP.get(novelSiteEnum);
    }

}
