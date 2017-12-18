package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {

        Map<String,Object> head = new HashMap<String, Object>();

        if(q.contains("max:")){
            String query = q.substring(0, q.indexOf("max"));
            head.put("CamelTwitterCount",q.substring(q.indexOf(":")+1,q.length()));
            q=query;
        }
        head.put("CamelTwitterKeywords",q);
        System.out.println(head);

        return producerTemplate.requestBodyAndHeaders("direct:search","",head);
   }
}