package com.eking.momp.org.service;

import java.util.List;

import com.eking.momp.org.dto.UserDTO;
import com.eking.momp.org.param.UserParam;
import com.eking.momp.org.po.UserPO;

public interface UserService {
	UserDTO getById(Integer id);
	UserDTO save(UserParam userParam);
	boolean delete(Integer id);
	boolean update(Integer id, UserParam userParam);
    List<UserDTO> list(String username);
}
