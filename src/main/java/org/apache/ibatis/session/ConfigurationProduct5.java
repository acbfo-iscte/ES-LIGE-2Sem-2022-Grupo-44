package org.apache.ibatis.session;


import java.util.Map;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.session.Configuration.StrictMap;
import java.util.Collection;

public class ConfigurationProduct5 {
	private final Map<String, KeyGenerator> keyGenerators = new StrictMap<>("Key Generators collection");

	public void addKeyGenerator(String id, KeyGenerator keyGenerator) {
		keyGenerators.put(id, keyGenerator);
	}

	public Collection<String> getKeyGeneratorNames() {
		return keyGenerators.keySet();
	}

	public Collection<KeyGenerator> getKeyGenerators() {
		return keyGenerators.values();
	}

	public KeyGenerator getKeyGenerator(String id) {
		return keyGenerators.get(id);
	}

	public boolean hasKeyGenerator(String id) {
		return keyGenerators.containsKey(id);
	}
}