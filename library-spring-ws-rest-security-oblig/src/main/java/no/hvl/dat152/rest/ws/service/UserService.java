/**
 * 
 */
package no.hvl.dat152.rest.ws.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.hvl.dat152.rest.ws.exceptions.UserNotFoundException;
import no.hvl.dat152.rest.ws.model.Order;
import no.hvl.dat152.rest.ws.model.User;
import no.hvl.dat152.rest.ws.repository.UserRepository;

/**
 * @author tdoy
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user) {
		
		user = userRepository.save(user);
		
		return user;
	}
	
	public void deleteUser(Long id) throws UserNotFoundException {
		User user = findUser(id);
		userRepository.delete(user);
	}
	
	public User updateUser(User user, Long id) throws UserNotFoundException {
		
		User returnUser = findUser(id);
		
		returnUser.setFirstname(user.getFirstname());
		returnUser.setLastname(user.getLastname());
		returnUser.setOrders(user.getOrders());
		
		try {
			returnUser = userRepository.save(returnUser);
		} catch (Exception e) {
			System.out.println("error in updating user with id: " + id );
		}
		
		return returnUser;
		
	}
	
	public List<User> findAllUsers(){
		
		List<User> allUsers = (List<User>) userRepository.findAll();
		
		return allUsers;
	}
	
	public User findUser(Long id) throws UserNotFoundException {
		
		User user = userRepository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User with id: "+id+" not found"));
		
		return user;
	}
	
	public Set<Order> findOrdersForUser(Long id) throws UserNotFoundException{
		
		User user = findUser(id);
		
		return user.getOrders();
	}
	
	public User createOrdersForUser(Long id, Order order) throws UserNotFoundException{
		
		User user = findUser(id);
		user.addOrder(order);
		updateUser(user, id);
		
		
		return user;
	}
}
