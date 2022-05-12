package org.apache.ibatis.reflection;


import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.reflection.invoker.Invoker;
import java.util.HashMap;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import org.apache.ibatis.reflection.invoker.MethodInvoker;
import org.apache.ibatis.reflection.invoker.AmbiguousMethodInvoker;
import java.text.MessageFormat;
import java.lang.reflect.Type;
import org.apache.ibatis.reflection.invoker.GetFieldInvoker;
import org.apache.ibatis.reflection.invoker.SetFieldInvoker;
import java.util.List;
import java.util.Arrays;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Array;

public class ReflectorProduct2 {
	private ReflectorProduct reflectorProduct = new ReflectorProduct();
	private final Class<?> type;
	private final Map<String, Invoker> setMethods = new HashMap<>();
	private final Map<String, Invoker> getMethods = new HashMap<>();
	private final Map<String, Class<?>> setTypes = new HashMap<>();
	private final Map<String, Class<?>> getTypes = new HashMap<>();
	private Constructor<?> defaultConstructor;

	public ReflectorProduct2(Class<?> clazz) {
		type = clazz;
	}

	public ReflectorProduct getReflectorProduct() {
		return reflectorProduct;
	}

	public Class<?> getType() {
		return type;
	}

	public Map<String, Invoker> getSetMethods() {
		return setMethods;
	}

	public Map<String, Invoker> getGetMethods() {
		return getMethods;
	}

	public void addFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!setMethods.containsKey(field.getName())) {
				int modifiers = field.getModifiers();
				if (!(Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers))) {
					addSetField(field);
				}
			}
			if (!getMethods.containsKey(field.getName())) {
				addGetField(field);
			}
		}
		if (clazz.getSuperclass() != null) {
			addFields(clazz.getSuperclass());
		}
	}

	/**
	* Gets the type for a property setter.
	* @param propertyName  - the name of the property
	* @return  The Class of the property setter
	*/
	public Class<?> getSetterType(String propertyName) {
		Class<?> clazz = setTypes.get(propertyName);
		if (clazz == null) {
			throw new ReflectionException(
					"There is no setter for property named '" + propertyName + "' in '" + type + "'");
		}
		return clazz;
	}

	public void addGetMethod(String name, Method method, boolean isAmbiguous) {
		MethodInvoker invoker = isAmbiguous ? new AmbiguousMethodInvoker(method, MessageFormat.format(
				"Illegal overloaded getter method with ambiguous type for property ''{0}'' in class ''{1}''. This breaks the JavaBeans specification and can cause unpredictable results.",
				name, method.getDeclaringClass().getName())) : new MethodInvoker(method);
		getMethods.put(name, invoker);
		Type returnType = TypeParameterResolver.resolveReturnType(method, type);
		getTypes.put(name, typeToClass(returnType));
	}

	public void addGetField(Field field) {
		if (reflectorProduct.isValidPropertyName(field.getName())) {
			getMethods.put(field.getName(), new GetFieldInvoker(field));
			Type fieldType = TypeParameterResolver.resolveFieldType(field, type);
			getTypes.put(field.getName(), typeToClass(fieldType));
		}
	}

	public Method pickBetterSetter(Method setter1, Method setter2, String property) {
		if (setter1 == null) {
			return setter2;
		}
		Class<?> paramType1 = setter1.getParameterTypes()[0];
		Class<?> paramType2 = setter2.getParameterTypes()[0];
		if (paramType1.isAssignableFrom(paramType2)) {
			return setter2;
		} else if (paramType2.isAssignableFrom(paramType1)) {
			return setter1;
		}
		MethodInvoker invoker = new AmbiguousMethodInvoker(setter1, MessageFormat.format(
				"Ambiguous setters defined for property ''{0}'' in class ''{1}'' with types ''{2}'' and ''{3}''.",
				property, setter2.getDeclaringClass().getName(), paramType1.getName(), paramType2.getName()));
		setMethods.put(property, invoker);
		Type[] paramTypes = TypeParameterResolver.resolveParamTypes(setter1, type);
		setTypes.put(property, typeToClass(paramTypes[0]));
		return null;
	}

	public void addSetMethod(String name, Method method) {
		MethodInvoker invoker = new MethodInvoker(method);
		setMethods.put(name, invoker);
		Type[] paramTypes = TypeParameterResolver.resolveParamTypes(method, type);
		setTypes.put(name, typeToClass(paramTypes[0]));
	}

	public void addSetField(Field field) {
		if (reflectorProduct.isValidPropertyName(field.getName())) {
			setMethods.put(field.getName(), new SetFieldInvoker(field));
			Type fieldType = TypeParameterResolver.resolveFieldType(field, type);
			setTypes.put(field.getName(), typeToClass(fieldType));
		}
	}

	public Invoker getSetInvoker(String propertyName) {
		Invoker method = setMethods.get(propertyName);
		if (method == null) {
			throw new ReflectionException(
					"There is no setter for property named '" + propertyName + "' in '" + type + "'");
		}
		return method;
	}

	public Invoker getGetInvoker(String propertyName) {
		Invoker method = getMethods.get(propertyName);
		if (method == null) {
			throw new ReflectionException(
					"There is no getter for property named '" + propertyName + "' in '" + type + "'");
		}
		return method;
	}

	/**
	* Gets the type for a property getter.
	* @param propertyName  - the name of the property
	* @return  The Class of the property getter
	*/
	public Class<?> getGetterType(String propertyName) {
		Class<?> clazz = getTypes.get(propertyName);
		if (clazz == null) {
			throw new ReflectionException(
					"There is no getter for property named '" + propertyName + "' in '" + type + "'");
		}
		return clazz;
	}

	/**
	* Check to see if a class has a writable property by name.
	* @param propertyName  - the name of the property to check
	* @return  True if the object has a writable property by the name
	*/
	public boolean hasSetter(String propertyName) {
		return setMethods.containsKey(propertyName);
	}

	/**
	* Check to see if a class has a readable property by name.
	* @param propertyName  - the name of the property to check
	* @return  True if the object has a readable property by the name
	*/
	public boolean hasGetter(String propertyName) {
		return getMethods.containsKey(propertyName);
	}

	public void resolveSetterConflicts(Map<String, List<Method>> conflictingSetters) {
		for (Entry<String, List<Method>> entry : conflictingSetters.entrySet()) {
			String propName = entry.getKey();
			List<Method> setters = entry.getValue();
			Class<?> getterType = getTypes.get(propName);
			boolean isGetterAmbiguous = getMethods.get(propName) instanceof AmbiguousMethodInvoker;
			boolean isSetterAmbiguous = false;
			Method match = null;
			match = resolveSetterConflicts_extracted1(propName, setters, getterType, isGetterAmbiguous,
					isSetterAmbiguous, match);
			if (match != null) {
				addSetMethod(propName, match);
			}
		}
	}

	public void addDefaultConstructor(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		Arrays.stream(constructors).filter(constructor -> constructor.getParameterTypes().length == 0).findAny()
				.ifPresent(constructor -> this.defaultConstructor = constructor);
	}

	public boolean hasDefaultConstructor() {
		return defaultConstructor != null;
	}

	public Constructor<?> getDefaultConstructor() {
		if (defaultConstructor != null) {
			return defaultConstructor;
		} else {
			throw new ReflectionException("There is no default constructor for " + type);
		}
	}

	public Class<?> typeToClass(Type src) {
		Class<?> result = null;
		if (src instanceof Class) {
			result = (Class<?>) src;
		} else if (src instanceof ParameterizedType) {
			result = (Class<?>) ((ParameterizedType) src).getRawType();
		} else if (src instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) src).getGenericComponentType();
			if (componentType instanceof Class) {
				result = Array.newInstance((Class<?>) componentType, 0).getClass();
			} else {
				Class<?> componentClass = typeToClass(componentType);
				result = Array.newInstance(componentClass, 0).getClass();
			}
		}
		if (result == null) {
			result = Object.class;
		}
		return result;
	}

	public Method resolveSetterConflicts_extracted1(String propName, List<Method> setters, Class<?> getterType,
			boolean isGetterAmbiguous, boolean isSetterAmbiguous, Method match) {
		for (Method setter : setters) {
			if (!isGetterAmbiguous && setter.getParameterTypes()[0].equals(getterType)) {
				match = setter;
				break;
			}
			if (!isSetterAmbiguous) {
				match = pickBetterSetter(match, setter, propName);
				isSetterAmbiguous = match == null;
			}
		}
		return match;
	}
}