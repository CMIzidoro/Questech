package com.fatec.questTech.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fatec.questTech.enumeration.EnviromentEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;

import com.fatec.questTech.dto.SessionDto;
import com.fatec.questTech.enumeration.ParametrosEnum;
import com.fatec.questTech.util.EmailUtil;
import com.fatec.questTech.util.ServiceUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final String BR_BR = "<br></br>";

	@Autowired
	EmailUtil emailService;

	@Value("${environment}")
	private String environment;

	@ExceptionHandler(value = Exception.class)
	public String defaultErrorHandler(HttpServletRequest req, HttpSession session, Exception e) {

		try {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			
			String url = (String) req.getAttribute(
			        HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

			StringBuilder sb = new StringBuilder();
			sb.append("<b>URL:</b> " + url);
			sb.append(BR_BR);
			sb.append(BR_BR);
			sb.append("<b>USER:</b> " + getUser(session));
			sb.append(BR_BR);
			sb.append(BR_BR);
			sb.append("<b>MESSAGE:</b> " + e.getMessage());
			sb.append(BR_BR);
			sb.append(BR_BR);
			sb.append("<b>STACKTRACE:</b> " + sw.toString());
			
			if(!EnviromentEnum.DEVELOPMENT.toString().equals(environment)) {
				String email = ServiceUtil.getParameter(ParametrosEnum.ERROR_EMAIL);
				emailService.htmlEmail(email, "ERROR [" + environment + "]["+url.toUpperCase()+"] ", sb.toString());
			}

			log.error("error", e);
			
		} catch (Exception ex) {
			log.error("Falha ao enviar o email de erro:", ex);
		}

		return "redirect:error";

	}

	private String getUser(HttpSession session) {
		try {
			SessionDto dto = (SessionDto) session.getAttribute("sessionDto");
			return dto.getNomeUser();
		} catch (Exception e) {
			return "";
		}

	}

}