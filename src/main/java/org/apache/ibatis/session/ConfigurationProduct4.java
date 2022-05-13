package org.apache.ibatis.session;


import java.util.Map;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.Configuration.StrictMap;
import java.util.Collection;

public class ConfigurationProduct4 {
	private final Map<String, Cache> caches = new StrictMap<>("Caches collection");

	public Collection<String> getCacheNames() {
		return caches.keySet();
	}

	public Collection<Cache> getCaches() {
		return caches.values();
	}

	public Cache getCache(String id) {
		return caches.get(id);
	}

	public boolean hasCache(String id) {
		return caches.containsKey(id);
	}

	public void addCache(Cache cache) {
		caches.put(cache.getId(), cache);
	}
}