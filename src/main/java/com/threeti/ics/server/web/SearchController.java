package com.threeti.ics.server.web;

import com.threeti.ics.server.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 26/09/12
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
@Controller(value = "searchController")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search/{userName}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseResult search(@PathVariable String userName, @WebParam String queryString) throws UnsupportedEncodingException {
        final String q = new String(queryString.getBytes("ISO-8859-1"), "UTF-8");
        return new ResponseResult(ResponseResult.SEARCH_RESULT_CODE, searchService.search(userName, q));
    }

}
