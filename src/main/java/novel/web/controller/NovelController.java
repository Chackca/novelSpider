package novel.web.controller;

import novel.spider.entitys.Chapter;
import novel.spider.entitys.ChapterDetail;
import novel.spider.entitys.Novel;
import novel.spider.interfaces.IChapterDetailSpider;
import novel.spider.interfaces.IChapterSpider;
import novel.spider.util.ChapterDetailSpiderFactory;
import novel.spider.util.ChapterSpiderFactory;
import novel.web.entitys.JSONResponse;
import novel.web.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class NovelController {

    @Resource
    private NovelService service;

    //http://localhost:8080/Novel/chapters.do?url=https://www.bxwx9.org/b/176/176884/index.html
    @RequestMapping(value="/chapters.do",method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse getsChapter(String url){
        IChapterSpider spider = ChapterSpiderFactory.getChapterSpider(url);
        List<Chapter> chapterList = spider.getChapter(url);
        return JSONResponse.success(chapterList);
    }

    //http://localhost:8080/Novel/chapterDetail.do?url=https://www.bxwx9.org/b/176/176884/29540096.html
    /*@RequestMapping(value="/chapterDetail.do",method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse getChapterDetail(String url){
        IChapterDetailSpider spider = ChapterDetailSpiderFactory.getChapterDetailSpider(url);
        ChapterDetail detail = spider.getChapterDetail(url);
        return JSONResponse.success(detail);
    }*/


    //http://localhost:8080/Novel/novelSearch.do?keyword=%22%E4%B8%89%22
    @RequestMapping(value="/novelSearch.do",method = RequestMethod.GET)
    @ResponseBody
    public JSONResponse getNovelByKeyword(String keyword) throws UnsupportedEncodingException {
        keyword = new String(keyword.getBytes("ISO-8859-1"),"UTF-8");
        List<Novel> novelList = service.getsNovelByKeyword(keyword);
        return JSONResponse.success(novelList);
    }

    @RequestMapping(value = "/novelSearch2.do", method = RequestMethod.POST)
    @ResponseBody
    public JSONResponse getsNovelByKeyword(String keyword, int platformId) throws UnsupportedEncodingException {
        return JSONResponse.success(service.getsNovelByKeyword(keyword, platformId));
    }

    @RequestMapping(value = "/chapterList.do", method = RequestMethod.GET)
    public ModelAndView showChapterList(String url) {
        ModelAndView view = new ModelAndView();
        view.setViewName("chapterList");
        view.getModel().put("chapters", ChapterSpiderFactory.getChapterSpider(url).getChapter(url));
        view.getModel().put("baseUrl", url);
        return view;
    }

    @RequestMapping(value = "/chapterDetail.do", method = RequestMethod.GET)
    public ModelAndView showChapterDetail(String url, String baseUrl) {
        ModelAndView view = new ModelAndView();
        view.setViewName("chapterDetail");
        try {
            ChapterDetail detail = ChapterDetailSpiderFactory.getChapterDetailSpider(url).getChapterDetail(url);
            detail.setContent(detail.getContent().replaceAll("\n", "<br>"));
            view.getModel().put("chapterDetail", detail);
            view.getModel().put("isSuccess", true);
        } catch (Exception e) {
            e.printStackTrace();
            view.getModel().put("isSuccess", false);
        }
        view.getModel().put("baseUrl", baseUrl);
        return view;
    }
}
