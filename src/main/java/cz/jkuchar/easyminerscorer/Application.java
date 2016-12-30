package cz.jkuchar.easyminerscorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.WebApplicationInitializer;

/**
 * Main Application 
 * @author Jaroslav Kuchar <https://github.com/jaroslav-kuchar>
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableCaching
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer{
	
	/**
	 * Spring application configuration
	 */
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
	
	/**
	 * Cache configuration
	 * @return cache manager object
	 */
//	@Bean
//    public CacheManager cacheManager() {
//        // configure and return an implementation of Spring's CacheManager SPI
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("scorers")));
//        return cacheManager;
//    }
	
	@Bean
	public CacheManager cacheManager() {
		// http://www.mkyong.com/spring/spring-caching-and-ehcache-example/
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}
 
	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
	}

}
