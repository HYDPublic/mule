/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.modules.schedulers.cron;

import static junit.framework.Assert.assertEquals;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.mule.runtime.core.api.MuleException;
import org.mule.runtime.core.api.lifecycle.InitialisationException;
import org.mule.runtime.core.api.schedule.Scheduler;
import org.mule.runtime.core.api.schedule.SchedulerFactoryPostProcessor;
import org.mule.runtime.core.api.schedule.Schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;


public class StoppedCronSchedulerTestCase extends MuleArtifactFunctionalTestCase {

  private static List<String> foo = new ArrayList<String>();

  @Override
  protected String getConfigFile() {
    return "cron-scheduler-stopped-config.xml";
  }

  @Test
  public void test() throws Exception {
    runSchedulersOnce();
    Thread.sleep(6000);

    assertEquals(1, foo.size());
  }


  public static class FooComponent {

    public boolean process(String s) {
      synchronized (foo) {

        foo.add(s);

      }

      return false;
    }
  }

  private void runSchedulersOnce() throws Exception {
    Collection<Scheduler> schedulers =
        muleContext.getRegistry().lookupScheduler(Schedulers.flowConstructPollingSchedulers("pollfoo"));

    for (Scheduler scheduler : schedulers) {
      scheduler.schedule();
    }
  }

  public static class TestSchedulerFactoryPostProcessor implements SchedulerFactoryPostProcessor {

    @Override
    public Scheduler process(Object job, Scheduler scheduler) {
      return new Scheduler() {

        @Override
        public void schedule() throws Exception {
          scheduler.schedule();
        }

        @Override
        public void dispose() {
          scheduler.dispose();
        }

        @Override
        public void initialise() throws InitialisationException {
          scheduler.initialise();
        }

        @Override
        public void setName(String s) {
          scheduler.setName(s);
        }

        @Override
        public String getName() {
          return scheduler.getName();
        }

        @Override
        public void start() throws MuleException {
          // Does Nothing
        }

        @Override
        public void stop() throws MuleException {
          // Does Nothing
        }
      };
    }
  }
}