package org.radargun.service.gemfire;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;
import org.radargun.Service;
import org.radargun.config.Property;
import org.radargun.traits.Lifecycle;
import org.radargun.traits.ProvidesTrait;

@Service(doc = "Gemfire")
public class Gemfire8Service implements Lifecycle {

    private Cache cache;
    private boolean isRunning;

    @Property(name = "cacheName", doc = "Name of the cache. Default is 'testCache'")
    protected String cacheName = "testCache";

    @ProvidesTrait
    public Gemfire8Operations createBasicOperations () {
        return new Gemfire8Operations(this);
    }

    // This method is crucial here, it exposes the Lifecycle trait,
    // without it the "start" method won't be invoked
    @ProvidesTrait
    public Gemfire8Service getLifecycle() {
        return this;
    }

    @Override
    public void start() {
        CacheFactory cacheFactory = new CacheFactory();
        cache = cacheFactory.create();
        isRunning = true;
    }

    public <K,V> Region<K, V> getOrCreateRegion(String name) {
        /*

        There are two ways to specify the cache name used for tests.
        First one with the plugin setup:

            <default xmlns="urn:radargun:plugins:gemfire82:2.1" cache-name="theCache"/>

        Second with the stage setup:

            <load-data num-entries="10000">
                <cache-selector>
                    <use-cache cache="testCache"/>
                </cache-selector>
            </load-data>

         In this method name parameter will be null unless you pass a cache selector to a stage
         */
        String actualName = name != null ? name : cacheName;

        Region<K, V> region = cache.getRegion(actualName);

        if (region != null) {
            return region;
        }

        return cache.<K, V>createRegionFactory().create(actualName);
    }

    @Override
    public void stop() {
        isRunning = false;
        cache.close();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
