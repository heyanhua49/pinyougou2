package com.pinyougou.cart.controller;

import com.alibaba.fastjson.JSONArray;
import com.pinyougou.common.util.CookieUtils;
import com.pinyougou.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/cart")
@RestController
public class CartController {

    //购物车数据保存在浏览器中cookie的名称
    private static final String COOKIE_CART_LIST = "PYG_CART_LIST";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 在登录或者未登录情况下获取用户购物车列表
     * @return 购物车列表
     */
    @GetMapping("/findCartList")
    public List<Cart> findCartList(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if ("anonymousUser".equals(username)) {
                //没有登录；从cookie中获取购物车数据
                List<Cart> cookieCartList = new ArrayList<>();
                //1、获取cookie中购物车json格式字符串
                String cartListJsonStr = CookieUtils.getCookieValue(request, COOKIE_CART_LIST, true);
                //2、转换为列表对象
                if (!StringUtils.isEmpty(cartListJsonStr)) {
                    cookieCartList = JSONArray.parseArray(cartListJsonStr, Cart.class);
                }

                return cookieCartList;
            } else {
                //已登录；从redis中获取购物车数据
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前登录用户名
     * @return 用户信息
     */
    @GetMapping("/getUsername")
    public Map<String, Object> getUsername(){
        Map<String, Object> map = new HashMap<String, Object>();
        /**
         * 在security中配置了<intercept-url pattern="/cart/*.do" access="IS_AUTHENTICATED_ANONYMOUSLY"/>
         * 所以当用户没有登录的时候，获得到的用户名为 anonymousUser
         */
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("username", username);

        return map;
    }
}
