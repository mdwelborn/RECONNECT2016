package com.mwelborn.r16.configuration;

import com.mwelborn.r16.rest.UserRestController;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(UserRestController.class);
	}
}
