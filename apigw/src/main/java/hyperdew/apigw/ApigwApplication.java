package hyperdew.apigw;

import hyperdew.apigw.filters.Postfilter;
import hyperdew.apigw.filters.preFilter.Prefilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class ApigwApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigwApplication.class, args);
	}

	@Bean
	public Prefilter preFilter() {
		return new Prefilter();
	}
	@Bean
	public Postfilter postFilter() {
		return new Postfilter();
	}

}
