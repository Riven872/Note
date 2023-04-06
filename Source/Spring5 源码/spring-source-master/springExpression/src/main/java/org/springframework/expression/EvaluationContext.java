/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.expression;

import java.util.List;

import org.springframework.lang.Nullable;

/**
 * Expressions are executed in an evaluation context. It is in this context that
 * references are resolved when encountered during expression evaluation.
 *
 * <p>There is a default implementation of this EvaluationContext interface:
 * {@link org.springframework.expression.spel.support.StandardEvaluationContext}
 * which can be extended, rather than having to implement everything manually.
 *
 * @author Andy Clement
 * @author Juergen Hoeller
 * @since 3.0
 */
/*xxx: 顶级接口，执行期上下文*/
public interface EvaluationContext {

	/**
	 * Return the default root context object against which unqualified
	 * properties/methods/etc should be resolved. This can be overridden
	 * when evaluating an expression.
	 */
	/*xxx: 获取根对象*/
	TypedValue getRootObject();

	/**
	 * Return a list of accessors that will be asked in turn to read/write a property.
	 */
	/*xxx: 获取属性访问器列表*/
	List<PropertyAccessor> getPropertyAccessors();

	/**
	 * Return a list of resolvers that will be asked in turn to locate a constructor.
	 */
	/*xxx: 获取构造解析器列表*/
	List<ConstructorResolver> getConstructorResolvers();

	/**
	 * Return a list of resolvers that will be asked in turn to locate a method.
	 */
	/*xxx: 获取方法解析器列表 */
	List<MethodResolver> getMethodResolvers();

	/**
	 * Return a bean resolver that can look up beans by name.
	 */
	@Nullable
	/*xxx: 获取bean解析器*/
	BeanResolver getBeanResolver();

	/**
	 * Return a type locator that can be used to find types, either by short or
	 * fully qualified name.
	 */
	/*xxx: 获取类型查找器 */
	TypeLocator getTypeLocator();

	/**
	 * Return a type converter that can convert (or coerce) a value from one type to another.
	 */
	/*xxx: 获取类型转换器*/
	TypeConverter getTypeConverter();

	/**
	 * Return a type comparator for comparing pairs of objects for equality.
	 */
	/*xxx: 获取类型比较器*/
	TypeComparator getTypeComparator();

	/**
	 * Return an operator overloader that may support mathematical operations
	 * between more than the standard set of types.
	 */
	OperatorOverloader getOperatorOverloader();

	/**
	 * Set a named variable within this evaluation context to a specified value.
	 * @param name the name of the variable to set
	 * @param value the value to be placed in the variable
	 */
	/*xxx: 设置上下文变量*/
	void setVariable(String name, @Nullable Object value);

	/**
	 * Look up a named variable within this evaluation context.
	 * @param name variable to lookup
	 * @return the value of the variable, or {@code null} if not found
	 */
	@Nullable
	/*xxx: 查找上下文变量*/
	Object lookupVariable(String name);

}
