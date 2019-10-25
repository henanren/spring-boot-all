package com.myzh.sharding.entities;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
@Builder
public class Order {
    private BigInteger orderId;
    private String orderName;
    private Date createTime;
}
