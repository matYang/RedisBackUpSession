package RedisBaseModule.log4j;

import org.apache.log4j.PropertyConfigurator;

import RedisBaseModule.configurations.ServerConfig;


public class Log4j {

	public static void configure(){				
		PropertyConfigurator.configure(ServerConfig.resourcePrefix + "log4j.properties");
	}
}
