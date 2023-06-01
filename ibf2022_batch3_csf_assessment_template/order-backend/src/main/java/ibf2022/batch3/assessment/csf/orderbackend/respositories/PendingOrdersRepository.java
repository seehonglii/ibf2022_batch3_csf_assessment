package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

public class PendingOrdersRepository {

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	public void add(PizzaOrder order) {
	// Create a JSON string with the required fields
	String jsonOrder = String.format("{\"orderId\": \"%s\", \"date\": \"%s\", \"total\": %f, \"name\": \"%s\", \"email\": \"%s\"}",
	order.getOrderId(), order.getDate(), order.getTotal(), order.getName(), order.getEmail());

	// Store the JSON string in Redis with orderId as the key
	redisTemplate.opsForValue().set(order.getOrderId(), jsonOrder);
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	public boolean delete(String orderId) {
		return false;
	}

}
