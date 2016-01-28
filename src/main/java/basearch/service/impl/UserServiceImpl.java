package basearch.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basearch.dao.UserDao;
import basearch.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;

	@Inject
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

}
