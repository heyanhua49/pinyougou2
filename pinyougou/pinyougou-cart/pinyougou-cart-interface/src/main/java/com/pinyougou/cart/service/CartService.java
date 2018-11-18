package com.pinyougou.cart.service;

import com.pinyougou.vo.Cart;

import java.util.List;

public interface CartService {
    /**
     * 将商品加入到购物车列表
     * @param cartList 购物车列表
     * @param itemId 商品sku id
     * @param num 购买数量
     * @return 购物车列表
     */
    List<Cart> addItemToCartList(List<Cart> cartList, Long itemId, Integer num);

    /**
     * 根据用户名查询其对应的购物车列表
     * @param username 用户名
     * @return 购物车列表
     */
    List<Cart> findCartListByUsername(String username);

    /**
     * 将用户的购物车列表保存到redis
     * @param username 用户名
     * @param cartList 购物车列表
     */
    void saveCartListByUsername(String username, List<Cart> cartList);
}
