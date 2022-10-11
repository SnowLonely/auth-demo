package cn.lonelysnow.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LonelySnow
 * @classname MessagesController
 * @description 控制器
 * @date 2022/10/11 10:42
 */
@RestController
public class MessagesController {

	@GetMapping("/messages")
	public String[] getMessages() {
		return new String[] {"Message 1", "Message 2", "Message 3"};
	}
}