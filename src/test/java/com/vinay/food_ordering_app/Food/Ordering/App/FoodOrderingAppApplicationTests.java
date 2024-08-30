package com.vinay.food_ordering_app.Food.Ordering.App;

import com.vinay.food_ordering_app.Food.Ordering.App.dto.RestaurantDto;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.MenuItemEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.OrderEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.RestaurantEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.entities.realWorldEntites.DeliveryPartnerEntity;
import com.vinay.food_ordering_app.Food.Ordering.App.repositories.RestaurantRepository;
import com.vinay.food_ordering_app.Food.Ordering.App.services.MenuItemService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.NotificationService;
import com.vinay.food_ordering_app.Food.Ordering.App.services.RestaurantService;
import com.vinay.food_ordering_app.Food.Ordering.App.strategies.DeliveryPartnerFindingStrategy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FoodOrderingAppApplicationTests {

//	@Autowired
//	private NotificationService notificationService;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private DeliveryPartnerFindingStrategy strategy;;

	@Test
	void testFindingPartnerFunctionality() {
		RestaurantEntity restaurant = restaurantRepository.findById(1L).orElse(null);

		DeliveryPartnerEntity deliveryPartner = strategy.findNearestDeliveryPartner(restaurant);

		System.out.println(deliveryPartner);
	}


//	@Test
//	void sendNotificationToRestaurantTest() {
//		notificationService.sendNotificationToRestaurantId(new RestaurantEntity(), new OrderEntity());
//	}

}
