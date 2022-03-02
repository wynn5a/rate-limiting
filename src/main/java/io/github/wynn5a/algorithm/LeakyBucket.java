package io.github.wynn5a.algorithm;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author fuwenming
 * @date 2022/3/2
 */
@Service
public class LeakyBucket implements InitializingBean {

  private final AtomicInteger bucket = new AtomicInteger(0);
  private final int limit = 30;
  private final int window = 1000;

  public String get() {
    if (bucket.get() + 1 >= limit) {
      throw new IllegalStateException("the request exceeds the maximum limit: " + limit);
    }

    return "request: " + bucket.incrementAndGet();
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    new Thread(() -> {
      while (true){
        try {
          Thread.sleep(30);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        if (bucket.get() > 0) {
          System.out.println("now request number in  bucket: " + bucket.decrementAndGet());
        }
      }
    }).start();
  }
}
