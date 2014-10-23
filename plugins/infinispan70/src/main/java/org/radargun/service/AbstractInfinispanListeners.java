package org.radargun.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.radargun.traits.CacheListeners;

public abstract class AbstractInfinispanListeners<T extends AbstractInfinispanListeners.GenericListener> implements
      CacheListeners {

   protected final ConcurrentMap<String, T> listeners = new ConcurrentHashMap<String, T>();

   protected abstract GenericListener getOrCreateListener(String cacheName);

   protected abstract GenericListener getListenerOrThrow(String cacheName);

   @Override
   public void addCreatedListener(String cacheName, CreatedListener listener) {
      getOrCreateListener(cacheName).add(listener);
   }

   @Override
   public void addUpdatedListener(String cacheName, UpdatedListener listener) {
      getOrCreateListener(cacheName).add(listener);
   }

   @Override
   public void addRemovedListener(String cacheName, RemovedListener listener) {
      getOrCreateListener(cacheName).add(listener);
   }

   @Override
   public void addEvictedListener(String cacheName, EvictedListener listener) {
      getOrCreateListener(cacheName).add(listener);
   }

   @Override
   public void addExpiredListener(String cacheName, ExpiredListener listener) {
      getOrCreateListener(cacheName).add(listener);
   }

   @Override
   public void removeCreatedListener(String cacheName, CreatedListener listener) {
      getListenerOrThrow(cacheName).remove(listener);
   }

   @Override
   public void removeUpdatedListener(String cacheName, UpdatedListener listener) {
      getListenerOrThrow(cacheName).remove(listener);
   }

   @Override
   public void removeRemovedListener(String cacheName, RemovedListener listener) {
      getListenerOrThrow(cacheName).remove(listener);
   }

   @Override
   public void removeEvictedListener(String cacheName, EvictedListener listener) {
      getListenerOrThrow(cacheName).remove(listener);
   }

   @Override
   public void removeExpiredListener(String cacheName, ExpiredListener listener) {
      getListenerOrThrow(cacheName).remove(listener);
   }

   protected static class GenericListener {
      CopyOnWriteArraySet<CreatedListener> created = new CopyOnWriteArraySet<CreatedListener>();
      CopyOnWriteArraySet<UpdatedListener> updated = new CopyOnWriteArraySet<UpdatedListener>();
      CopyOnWriteArraySet<RemovedListener> removed = new CopyOnWriteArraySet<RemovedListener>();
      CopyOnWriteArraySet<EvictedListener> evicted = new CopyOnWriteArraySet<EvictedListener>();
      CopyOnWriteArraySet<ExpiredListener> expired = new CopyOnWriteArraySet<ExpiredListener>();

      public void add(CreatedListener listener) {
         created.add(listener);
      }

      public void add(UpdatedListener listener) {
         updated.add(listener);
      }

      public void add(RemovedListener listener) {
         removed.add(listener);
      }

      public void add(EvictedListener listener) {
         evicted.add(listener);
      }

      public void add(ExpiredListener listener) {
         expired.add(listener);
      }

      public void remove(CreatedListener listener) {
         created.remove(listener);
      }

      public void remove(UpdatedListener listener) {
         updated.remove(listener);
      }

      public void remove(RemovedListener listener) {
         removed.remove(listener);
      }

      public void remove(EvictedListener listener) {
         evicted.remove(listener);
      }

      public void remove(ExpiredListener listener) {
         expired.remove(listener);
      }
   }
}