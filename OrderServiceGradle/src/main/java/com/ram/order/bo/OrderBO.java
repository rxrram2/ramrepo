package com.ram.order.bo;

import com.ram.order.bo.exception.BOException;
import com.ram.order.dto.Order;

public interface OrderBO {
	boolean placOrder(Order order) throws BOException;
	boolean cancelOrder(int id) throws BOException;
	boolean deleteOrder(int id) throws BOException;
}
