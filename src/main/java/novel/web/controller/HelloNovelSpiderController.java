package novel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloNovelSpiderController {
    @RequestMapping(value="hello.do",method=RequestMethod.GET)
    @ResponseBody //能够将对象转换成String输出，包括中文也可以，注意是对象
    public String sayHello(){
        return "Hello,Novel Spider";
    }
}
