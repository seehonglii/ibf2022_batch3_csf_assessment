package ibf2022.batch3.assessment.csf.orderbackend.respositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.UpdateOptions;

import ibf2022.batch3.assessment.csf.orderbackend.models.PizzaOrder;

@Repository
public class OrdersRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	// TODO: Task 3
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	/*
	 * db.orders.update({
	 * 	'_id': 'someid',
	 * 	'date': 'somedate',
	 *  'total': 'sometotal',
	 * 	'name': 'somename',
	 *  'email': 'someemail',
	 * 	'sauce': 'somesauce',
	 * 	'base' : 'somebase',
	 * 	'size': 'somesize',
	 * 	'comments': 'somecomments',
	 * },
	 * 	{$push : {'toppings': {$each: [<topping0>, ...]}}},
	 * 	{upsert: true}
	 * )
	 * 
	 */
	// Native MongoDB query here for add()
	public void add(PizzaOrder order) {
		mongoTemplate.getCollection("orders").updateOne(
            new Document("_id", order.getOrderId())
                .append("date", order.getDate())
                .append("total", order.getTotal())
                .append("name", order.getName())
                .append("email", order.getEmail())
                .append("sauce", order.getSauce())
                .append("base", order.getThickCrust())
                .append("size", order.getSize())
                .append("comments", order.getComments()),
            new Document("$push", new Document("toppings", order.getToppings())),
            new UpdateOptions().upsert(true)
        );
	}
	
	// TODO: Task 6
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	/*
	 * db.orders.find(
	 * 	{'email': 'someemail'}
	 * )
	 */
	//   Native MongoDB query here for getPendingOrdersByEmail()
	public List<PizzaOrder> getPendingOrdersByEmail(String email) {

		return null;
	}

	// TODO: Task 7
	// WARNING: Do not change the method's signature.
	// Write the native MongoDB query in the comment below
	//   Native MongoDB query here for markOrderDelivered()
	public boolean markOrderDelivered(String orderId) {
		
		return false;
	}

	public boolean existsById(String orderId) {
		return false;
	}

    public void deleteById(String orderId) {
    }


}
