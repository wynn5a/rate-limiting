package io.github.wynn5a.algorithm;

import java.sql.SQLOutput;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author fuwenming
 * @date 2022/3/1
 */
@Service
public class FixedWindowCounting implements InitializingBean {
  private final AtomicInteger count = new AtomicInteger(0);
  private final int limit = 30;
  public String get() {
    if (count.get()>= limit){
      throw new IllegalStateException("the request exceeds the maximum limit: " + limit);
    }
    return "request:" + count.incrementAndGet();
  }

  @Override
  public void afterPropertiesSet() {
    new Thread(()->{
      while(true){
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        count.set(0);
      }
    }).start();
  }
}
