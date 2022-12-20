package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service 
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 

	// find all user
	public List<User> listAll() {
		return (List<User>) userRepository.findAll();
	}

	//
	public List<Role> listRoles() {
		return (List<Role>) roleRepository.findAll();
	}

	public void save(User user) { 
		boolean isUpdateUser  = (user.getId() != null); // xem id có null k
		if (isUpdateUser == true) { // có id user
			User exitUser = userRepository.findById(user.getId()).get(); // get id user
			if (user.getPassword().isEmpty()) {  // nếu password null thì set vào
				user.setPassword(exitUser.getPassword());
			}  else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);
		}
		userRepository.save(user);
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepository.getUserByEmail(email);
		
		if(userByEmail == null) {
			return true;
		}
		boolean isCreatingNew = (id==null);
		if (isCreatingNew) {
			if (userByEmail != null) { // not email
				 return false;
			}
		} else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try { 
			return userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new UserNotFoundException("Could not find any user with ID : " + id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long idDelete  =  userRepository.countById(id);
		if (idDelete == null || idDelete==0) {
			throw new UserNotFoundException("Could not find any user with ID : " + id);
		}
		userRepository.deleteById(id);
		
	}

}
