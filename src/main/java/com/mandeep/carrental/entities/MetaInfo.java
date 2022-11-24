package com.mandeep.carrental.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetaInfo {
	
	@Column
	LocalDateTime createdOn;
	
	@Column
	LocalDateTime deletedOn;

	@Column
	LocalDateTime updatedOn;
	
	
	


}
