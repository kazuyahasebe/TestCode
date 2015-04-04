package demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.entity.Test;
import demo.repository.TestRepository;

@Controller
public class StandardArgumentsController {

	@Autowired
	public ApplicationContext applicationContext;

	@Autowired
	public TestRepository testRepository;

	// request related

	@RequestMapping(value = "/data/standard/request", method = RequestMethod.GET)
	public @ResponseBody String standardRequestArgs(HttpServletRequest request,
			Principal user, Locale locale) {
		Iterable<Test> list = testRepository.findAll();
		StringBuilder buffer = new StringBuilder();
		buffer.append("request = ").append(request).append(", ");
		buffer.append("userPrincipal = ").append(user).append(", ");
		buffer.append("requestLocale = ").append(locale);
		return buffer.toString();
	}

}
