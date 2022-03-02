package io.github.wynn5a.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author fuwenming
 * @date 2022/3/1
 */
@Service
public class SlidingWindowCounting implements InitializingBean {

  private final int limit = 50;
  //1s
  private final int window = 1000;
  private final ArrayDeque<Long> requests = new ArrayDeque<>();

  public String get() {
    long current = System.currentTimeMillis();
    if (count(current) > limit) {
      throw new IllegalStateException("the request exceeds the maximum limit: " + limit);
    }
    requests.addLast(current);
    return "request: " + current;
  }

  //count request in time window
  private int count(long current) {
    long end = current - window;
    int count = 0;
    Deque<Long> copyOfRequest = requests.clone();
    for (Long request : copyOfRequest) {
      if(request >= end){
        count ++;
      }
    }
    return count;
  }


  /**
   * periodic check to clean the expired request
   * @throws Exception
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    new Thread(()->{
      while (true){
        try {
          Thread.sleep(window * 2);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        long end = System.currentTimeMillis() - window;
//        System.out.println("clean request before @ " + end);
        requests.removeIf(aLong -> aLong < end);
      }
    }).start();
  }
}
