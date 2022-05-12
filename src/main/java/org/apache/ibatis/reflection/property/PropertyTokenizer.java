/*
 *    Copyright 2009-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.reflection.property;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.reflection.ReflectionException;

/**
 * @author Clinton Begin
 */
public class PropertyTokenizer implements Iterator<PropertyTokenizer> {
  private String name;
  private final String indexedName;
  private String index;
  private final String children;

  public PropertyTokenizer(String fullname) {
    int delim = fullname.indexOf('.');
    if (delim > -1) {
      name = fullname.substring(0, delim);
      children = fullname.substring(delim + 1);
    } else {
      name = fullname;
      children = null;
    }
    indexedName = name;
    delim = name.indexOf('[');
    if (delim > -1) {
      index = name.substring(delim + 1, name.length() - 1);
      name = name.substring(0, delim);
    }
  }

  public String getName() {
    return name;
  }

  public String getIndex() {
    return index;
  }

  public String getIndexedName() {
    return indexedName;
  }

  public String getChildren() {
    return children;
  }

  @Override
  public boolean hasNext() {
    return children != null;
  }

  @Override
  public PropertyTokenizer next() {
    return new PropertyTokenizer(children);
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
  }

public Object getCollectionValue(Object collection) {
	if (collection instanceof Map) {
		return ((Map) collection).get(getIndex());
	} else {
		int i = Integer.parseInt(getIndex());
		if (collection instanceof List) {
			return ((List) collection).get(i);
		} else if (collection instanceof Object[]) {
			return ((Object[]) collection)[i];
		} else if (collection instanceof char[]) {
			return ((char[]) collection)[i];
		} else if (collection instanceof boolean[]) {
			return ((boolean[]) collection)[i];
		} else if (collection instanceof byte[]) {
			return ((byte[]) collection)[i];
		} else if (collection instanceof double[]) {
			return ((double[]) collection)[i];
		} else if (collection instanceof float[]) {
			return ((float[]) collection)[i];
		} else if (collection instanceof int[]) {
			return ((int[]) collection)[i];
		} else if (collection instanceof long[]) {
			return ((long[]) collection)[i];
		} else if (collection instanceof short[]) {
			return ((short[]) collection)[i];
		} else {
			throw new ReflectionException(
					"The '" + getName() + "' property of " + collection + " is not a List or Array.");
		}
	}
}

public void setCollectionValue(Object collection, Object value) {
	if (collection instanceof Map) {
		((Map) collection).put(getIndex(), value);
	} else {
		int i = Integer.parseInt(getIndex());
		if (collection instanceof List) {
			((List) collection).set(i, value);
		} else if (collection instanceof Object[]) {
			((Object[]) collection)[i] = value;
		} else if (collection instanceof char[]) {
			((char[]) collection)[i] = (Character) value;
		} else if (collection instanceof boolean[]) {
			((boolean[]) collection)[i] = (Boolean) value;
		} else if (collection instanceof byte[]) {
			((byte[]) collection)[i] = (Byte) value;
		} else if (collection instanceof double[]) {
			((double[]) collection)[i] = (Double) value;
		} else if (collection instanceof float[]) {
			((float[]) collection)[i] = (Float) value;
		} else if (collection instanceof int[]) {
			((int[]) collection)[i] = (Integer) value;
		} else if (collection instanceof long[]) {
			((long[]) collection)[i] = (Long) value;
		} else if (collection instanceof short[]) {
			((short[]) collection)[i] = (Short) value;
		} else {
			throw new ReflectionException(
					"The '" + getName() + "' property of " + collection + " is not a List or Array.");
		}
	}
}
}
