package com.ram.order.bo;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ram.order.bo.exception.BOException;
import com.ram.order.dao.OrderDAO;
import com.ram.order.dto.Order;

public class OrderBOImplTest {

	@Mock
	OrderDAO orderDAO; //dependencies
	private OrderBOImpl orderBOImpl;
	
	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		orderBOImpl = new OrderBOImpl();
		orderBOImpl.setOrderDao(orderDAO);
	}
	
	@Test
	public void placeOrderShouldCreateAnOrder() throws SQLException, BOException {
		Order order = new Order();
		when(orderDAO.create(order)).thenReturn(new Integer(1));
		//You can also use any(<class>) 
		//when(orderDAO.create(any(Order.class))).thenReturn(new Integer(1));
		boolean result = orderBOImpl.placOrder(order);
		assertTrue(result);
		verify(orderDAO).create(order);
	}

	@Test(expected=BOException.class)
	public void placeOrderShouldThrowBoException() throws SQLException, BOException {
		Order order = new Order();
		when(orderDAO.create(order)).thenThrow(SQLException.class);
		boolean result = orderBOImpl.placOrder(order);
	}

	@Test
	public void cancelOrderShouldCancelOrder() throws SQLException,BOException {
		
		Order order = new Order();
		//set the expectations
		when(orderDAO.read(anyInt())).thenReturn(order);
		when(orderDAO.update(order)).thenReturn(1);
		//verify cancel operation
		boolean result = orderBOImpl.cancelOrder(123);
		assertTrue(result);
		//verify method was invoked
		verify(orderDAO).read(anyInt());
		verify(orderDAO).update(order);
		
	}
	
	@Test
	public void deleteOrderShouldDeleteOrder() throws SQLException,BOException {
		when(orderDAO.delete(123)).thenReturn(1);
		boolean result = orderBOImpl.deleteOrder(123);
		assertTrue(result);
		
		//times(n) specifies how many times delete method was called when deleting the order
		verify(orderDAO,times(1)).delete(123);
	}
}
