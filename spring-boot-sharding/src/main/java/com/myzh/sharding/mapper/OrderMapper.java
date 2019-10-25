package com.myzh.sharding.mapper;

import java.util.List;

import com.myzh.sharding.entities.Order;

public interface OrderMapper {
    int save(Order info);

    /**
     * 批量保存
     * @param list
     * @return
     */
    int batchSave(List<Order> list);
}
