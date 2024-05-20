package LibraryManagementSystem.ManagingPackage.Caching;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig implements ApplicationListener<ContextRefreshedEvent> {

    private CompositeCacheManager compositeCacheManager;

    @Bean
    public CacheManager cacheManager() {
        List<CacheManager> cacheManagers = new ArrayList<>();
        cacheManagers.add(new ConcurrentMapCacheManager("BookCache"));
        cacheManagers.add(new ConcurrentMapCacheManager("PatronCache"));

        compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(cacheManagers);

        return compositeCacheManager;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (compositeCacheManager != null) {
            compositeCacheManager.getCacheNames().forEach(cacheName -> {
                compositeCacheManager.getCache(cacheName).clear();
            });
        }
    }

}
