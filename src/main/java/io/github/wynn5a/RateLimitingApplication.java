package io.github.wynn5a;

import io.github.wynn5a.algorithm.FixedWindowCounting;
import io.github.wynn5a.algorithm.LeakyBucket;
import io.github.wynn5a.algorithm.SlidingWindowCounting;
import io.github.wynn5a.algorithm.TokenBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fuwenming
 * @date 2022/3/1
 */
@SpringBootApplication
@RestController
public class RateLimitingApplication {

  @Autowired
  FixedWindowCounting fixedWindowCounting;
  @Autowired
  SlidingWindowCounting slidingWindowCounting;
  @Autowired
  LeakyBucket leakyBucket;
  @Autowired
  private TokenBucket tokenBucket;

  public static void main(String[] args) {
    SpringApplication.run(RateLimitingApplication.class);
  }

  @GetMapping("fixed")
  public String get(){
    String response = fixedWindowCounting.get();
    System.out.println("fixed --> " + response);
    return response;
  }
  @GetMapping("sliding")
  public String getBySliding(){
    String response = slidingWindowCounting.get();
    System.out.println("sliding --> " + response);
    return response;
  }

  @GetMapping("leaky")
  public String getByLeakyBucket(){
    String response = leakyBucket.get();
    System.out.println("leaky --> " + response);
    return response;
  }

  @GetMapping("token")
  public String getByTokenBucket(){
    String response = tokenBucket.get();
    System.out.println("token --> " + response);
    return response;
  }

}
