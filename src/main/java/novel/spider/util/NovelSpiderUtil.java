package novel.spider.util;

import novel.spider.NovelSiteEnum;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class NovelSpiderUtil {

    public static final Map<NovelSiteEnum,Map<String,String>> CONTEXT_MAP = new HashMap<>();
    static{
        init();
    }
    public NovelSpiderUtil(){}

    private static void init(){
        SAXReader reader = new SAXReader();
        try {
            URL url = NovelSpiderUtil.class.getClassLoader().getResource("Spider-Rule.xml");
            Document doc = reader.read(new File(url.getFile()));
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

    /** 多个文件合并为一个文件，合并规则，按文件名分割排序
     * @param path  基础目录，该项目录下的所有文本文件都会被合并到mergeToFile
     * @param mergeToFile   被合并的文本文件，这个参数可以为null，如果为null那么合并后的文件保存在path/merge.txt
     */
    public static void multiFileMerge(String path, String mergeToFile, boolean deleteThisFile) {
        mergeToFile = mergeToFile == null ? path + "/merge.txt" : path+"/"+mergeToFile;
        File[] files = new File(path).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                int o1Index = Integer.parseInt(o1.getName().split("\\-")[0]);
                int o2Index = Integer.parseInt(o2.getName().split("\\-")[0]);
                if (o1Index > o2Index) {
                    return 1;
                } else if (o1Index == o2Index){
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        PrintWriter out = null;
        try {
            out = new PrintWriter(new File(mergeToFile), "UTF-8");
            for (File file : files) {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                String line = null;
                while ((line = buffer.readLine()) != null) {
                    out.println(line);
                }
                buffer.close();
                if (deleteThisFile) {
                    file.delete();
                }
            }
        } catch (IOException e1) {
            System.err.println("文件合并出现异常");
            throw new RuntimeException(e1);
        } finally {
            out.close();
        }
    }

    /**
     * 获取书籍的状态
     * @param status
     * @return
     */
    public static int getNovelStatus(String status){
        if (status.contains("连载")){
            return 1;
        }else if (status.contains("完结")||status.contains("完成")||status.contains("完本")){
            return 2;
        }else {
            throw new RuntimeException("不支持的书籍状态"+status);
        }
    }

    /**
     * 将日期字符串解析成日期类型
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date getDate(String dateStr,String pattern) throws ParseException {
        if ("mm-dd".equals(pattern)){
            pattern = "yyyy-mm-dd";
            dateStr = new GregorianCalendar().get(Calendar.YEAR) +"-"+ dateStr;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * 获取本时刻的字符量
     * @param field
     * @return
     */
    /*public static String getDateField(int field){
        Calendar calendar = new GregorianCalendar();
        return calendar.get(field)+"";
    }*/
}
