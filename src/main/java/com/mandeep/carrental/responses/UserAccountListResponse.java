package com.mandeep.carrental.responses;

import java.util.List;

import com.mandeep.carrental.models.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountListResponse  extends BaseResponse{

	List<User> users;
	
}
