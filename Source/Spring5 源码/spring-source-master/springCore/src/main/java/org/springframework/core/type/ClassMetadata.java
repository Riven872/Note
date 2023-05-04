/*
 * Copyright 2002-2017 the original author or authors.
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

package org.springframework.core.type;

import org.springframework.lang.Nullable;

/**
 * Interface that defines abstract metadata of a specific class,
 * in a form that does not require that class to be loaded yet.
 *
 * @author Juergen Hoeller
 * @since 2.5
 * @see StandardClassMetadata
 * @see org.springframework.core.type.classreading.MetadataReader#getClassMetadata()
 * @see AnnotationMetadata
 */
/*xxx: 顶级接口，类元数据*/
public interface ClassMetadata {

	/**
	 * Return the name of the underlying class.
	 */
	/*xxx: 获取类名*/
	String getClassName();

	/**
	 * Return whether the underlying class represents an interface.
	 */
	/*xxx: 是否是接口*/
	boolean isInterface();

	/**
	 * Return whether the underlying class represents an annotation.
	 * @since 4.1
	 */
	/*xxx: 是否是注解*/
	boolean isAnnotation();

	/**
	 * Return whether the underlying class is marked as abstract.
	 */
	/*xxx: 是否是抽象类*/
	boolean isAbstract();

	/**
	 * Return whether the underlying class represents a concrete class,
	 * i.e. neither an interface nor an abstract class.
	 */
	/*xxx: 是否是具体的类*/
	default boolean isConcrete() {
		return !(isInterface() || isAbstract());
	}

	/**
	 * Return whether the underlying class is marked as 'final'.
	 */
	/*xxx: 是否是 final*/
	boolean isFinal();

	/**
	 * Determine whether the underlying class is independent, i.e. whether
	 * it is a top-level class or a nested class (static inner class) that
	 * can be constructed independently from an enclosing class.
	 */
	/*xxx: 确定基础类是否独立，
	   即它是顶级类还是可以独立于封闭类构造的嵌套类（静态内部类）*/
	boolean isIndependent();

	/**
	 * Return whether the underlying class is declared within an enclosing
	 * class (i.e. the underlying class is an inner/nested class or a
	 * local class within a method).
	 * <p>If this method returns {@code false}, then the underlying
	 * class is a top-level class.
	 */
	/*xxx: 返回基础类是否在封闭类中声明（即，基础类是内部/嵌套类还是方法中的本地类）。*/
	default boolean hasEnclosingClass() {
		return (getEnclosingClassName() != null);
	}

	/**
	 * Return the name of the enclosing class of the underlying class,
	 * or {@code null} if the underlying class is a top-level class.
	 */
	@Nullable
	/*xxx: 返回基础类的封闭类的名称，
	   如果基础类是顶级类，则返回{@code null}。*/
	String getEnclosingClassName();

	/**
	 * Return whether the underlying class has a super class.
	 */
	/*xxx: 返回基础类的超类*/
	default boolean hasSuperClass() {
		return (getSuperClassName() != null);
	}

	/**
	 * Return the name of the super class of the underlying class,
	 * or {@code null} if there is no super class defined.
	 */
	@Nullable
	/*xxx: 获取超类的名称*/
	String getSuperClassName();

	/**
	 * Return the names of all interfaces that the underlying class
	 * implements, or an empty array if there are none.
	 */
	/*xxx: 获取基础类实现的所有接口*/
	String[] getInterfaceNames();

	/**
	 * Return the names of all classes declared as members of the class represented by
	 * this ClassMetadata object. This includes public, protected, default (package)
	 * access, and private classes and interfaces declared by the class, but excludes
	 * inherited classes and interfaces. An empty array is returned if no member classes
	 * or interfaces exist.
	 * @since 3.1
	 */
	/*xxx: 返回声明为此ClassMetadata对象表示的类的成员的所有类的名称。
	   这包括类声明的公共、受保护、默认（包）访问以及私有类和接口，
	   但不包括继承的类和接口。
	   如果不存在成员类或接口，则返回空数组。*/
	String[] getMemberClassNames();

}
