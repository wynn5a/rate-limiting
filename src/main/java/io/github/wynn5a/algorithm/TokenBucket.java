package io.github.wynn5a.algorithm;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author fuwenming
 * @date 2022/3/2
 */
@Service
public class TokenBucket implements InitializingBean {

  private final int rate = 40;
  private final int limit = 30;
  private final AtomicInteger bucket = new AtomicInteger(limit);

  public String get() {
    if (bucket.get() <= 0) {
      throw new IllegalStateException("the request exceeds the maximum limit: " + limit);
    }
    return "request: " + bucket.getAndDecrement();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(1000 / rate);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        if (bucket.get() < limit) {
          System.out.println("now token in bucket: " + bucket.incrementAndGet());
        }
      }
    }).start();
  }
}
