package LibraryManagementSystem;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@org.springframework.boot.autoconfigure.SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@EnableJpaRepositories
public class SpringBootApplication implements CommandLineRunner {
	@Autowired
private CacheManager cacheManager;
	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringBootApplication.class, args);


	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Using cache manager: " + cacheManager.getClass().getName());
	}
}

