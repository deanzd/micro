package com.momp.mongo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePO {
	@Id
	private String id;

	@Field("sys_init")
	private Boolean sysInit;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@Field("create_time")
	private LocalDateTime createTime;

	@Field("create_user")
	private String createUser;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@Field("update_time")
	private LocalDateTime updateTime;

	@Field("update_user")
	private String updateUser;

	@Field("deleted")
	private boolean deleted;
}
