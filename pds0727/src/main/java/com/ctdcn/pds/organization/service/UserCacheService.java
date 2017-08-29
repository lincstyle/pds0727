package com.ctdcn.pds.organization.service;

import com.ctdcn.pds.organization.dao.UserDAO;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.utils.PinYinGenerator;
import com.ctdcn.utils.SpringUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张靖
 *         2015-08-30 11:44.
 */
@Service
public class UserCacheService {

    //缓存管理
    private CacheManager cacheManager;

    private static final String URL= "classpath:ehcache/ehcache-user.xml";

    @Autowired
    private UserDAO userDAO;

    private static final String ALL_USER = "allUser";

    public UserCacheService() throws FileNotFoundException, BadHanyuPinyinOutputFormatCombination {
        cacheManager = new CacheManager(ResourceUtils.getURL(URL));
        putUser();
    }

    /**
     * 放入缓存数据
     */
    @Transactional
    public void putUser() throws BadHanyuPinyinOutputFormatCombination {
        Cache allUserCache = cacheManager.getCache(ALL_USER);
        if(userDAO == null){
            userDAO = SpringUtils.getBean(UserDAO.class);
        }

        List<User> allUser = userDAO.queryAllUser(new User());
        for (User user : allUser){
            user.setQuanpin(PinYinGenerator.formatToPinYin(user.getName()));
            user.setJianpin(PinYinGenerator.formatAbbrToPinYin(user.getName()));
            allUserCache.put(new net.sf.ehcache.Element(user.getId(),user));
        }
    }

    @Transactional
    public void putUser(User user) throws BadHanyuPinyinOutputFormatCombination {
        Cache allUserCache = cacheManager.getCache(ALL_USER);

        user.setQuanpin(PinYinGenerator.formatToPinYin(user.getName()));
        user.setJianpin(PinYinGenerator.formatAbbrToPinYin(user.getName()));

        allUserCache.put(new net.sf.ehcache.Element(user.getId(),user));
    }

    @Transactional
    public void delUser(User user) throws BadHanyuPinyinOutputFormatCombination {
        Cache allUserCache = cacheManager.getCache(ALL_USER);
        allUserCache.remove(user.getId());
    }

    @Transactional
    public void updateUser(User user) throws BadHanyuPinyinOutputFormatCombination {
        Cache allUserCache = cacheManager.getCache(ALL_USER);
        
        user.setQuanpin(PinYinGenerator.formatToPinYin(user.getName()));
        user.setJianpin(PinYinGenerator.formatAbbrToPinYin(user.getName()));
        
        allUserCache.replace(new net.sf.ehcache.Element(user.getId(),user));
    }

    /**
     * 存储
     * @param keywords
     * @return
     */
    public List<Map> autoCompleteName(String keywords){

        Ehcache allUserCache = cacheManager.getCache(ALL_USER);


        Attribute<String> quanpin = allUserCache.getSearchAttribute("quanpin");
        //Attribute<String> jianpin = allUserCache.getSearchAttribute("jianpin");
        Query query = allUserCache.createQuery().addCriteria(quanpin.ilike(keywords+"*")).maxResults(10);
        Results results = query.includeValues().end().execute();
        List<Result> resultList = results.all();
        List<Map> re = new ArrayList<Map>();
        for (int i = 0; i < resultList.size() ; i++) {
            User result = (User)resultList.get(i).getValue();
            Map map = new HashMap();
            map.put("id",result.getId());
            map.put("name",result.getName());
            map.put("quanpin",result.getQuanpin());
            re.add(map);
        }
        return  re;
    }
}
