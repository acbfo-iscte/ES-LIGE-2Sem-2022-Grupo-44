package org.apache.ibatis.type;


import java.util.Map;
import java.util.EnumMap;

public class TypeHandlerRegistryProduct {
	private final Map<JdbcType, TypeHandler<?>> jdbcTypeHandlerMap = new EnumMap<>(JdbcType.class);

	public TypeHandler<?> getTypeHandler(JdbcType jdbcType) {
		return jdbcTypeHandlerMap.get(jdbcType);
	}

	public void register(JdbcType jdbcType, TypeHandler<?> handler) {
		jdbcTypeHandlerMap.put(jdbcType, handler);
	}
}