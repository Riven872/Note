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

package org.springframework.aop.framework;

import org.aopalliance.aop.Advice;

import org.springframework.aop.Advisor;
import org.springframework.aop.TargetClassAware;
import org.springframework.aop.TargetSource;

/**
 * Interface to be implemented by classes that hold the configuration
 * of a factory of AOP proxies. This configuration includes the
 * Interceptors and other advice, Advisors, and the proxied interfaces.
 *
 * <p>Any AOP proxy obtained from Spring can be cast to this interface to
 * allow manipulation of its AOP advice.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 13.03.2003
 * @see org.springframework.aop.framework.AdvisedSupport
 */
/*xxx: 被通知的对象，即需要动态代理的对象 */
public interface Advised extends TargetClassAware {

	/**
	 * Return whether the Advised configuration is frozen,
	 * in which case no advice changes can be made.
	 */
	/*xxx: 是否允许动态代理*/
	boolean isFrozen();

	/**
	 * Are we proxying the full target class instead of specified interfaces?
	 */
	/*xxx: 是否是在代理类，而非接口 */
	boolean isProxyTargetClass();

	/**
	 * Return the interfaces proxied by the AOP proxy.
	 * <p>Will not include the target class, which may also be proxied.
	 */
	/*xxx: 获取需要被 aop代理的接口，不包括本类 */
	Class<?>[] getProxiedInterfaces();

	/**
	 * Determine whether the given interface is proxied.
	 * @param intf the interface to check
	 */
	/*xxx: 判断给定的接口是否已被代理*/
	boolean isInterfaceProxied(Class<?> intf);

	/**
	 * Change the {@code TargetSource} used by this {@code Advised} object.
	 * <p>Only works if the configuration isn't {@linkplain #isFrozen frozen}.
	 * @param targetSource new TargetSource to use
	 */
	/*xxx: 需要被代理的对象类，只有当配置没有被冻结的时候，才能够使用*/
	void setTargetSource(TargetSource targetSource);

	/**
	 * Return the {@code TargetSource} used by this {@code Advised} object.
	 */
	/*xxx: 获取当前动态代理所作用的类 */
	TargetSource getTargetSource();

	/**
	 * Set whether the proxy should be exposed by the AOP framework as a
	 * {@link ThreadLocal} for retrieval via the {@link AopContext} class.
	 * <p>It can be necessary to expose the proxy if an advised object needs
	 * to invoke a method on itself with advice applied. Otherwise, if an
	 * advised object invokes a method on {@code this}, no advice will be applied.
	 * <p>Default is {@code false}, for optimal performance.
	 */
	/*xxx: 设置AOP框架是否应将代理公开为ThreadLocal，以便通过AopContext类进行检索 */
	void setExposeProxy(boolean exposeProxy);

	/**
	 * Return whether the factory should expose the proxy as a {@link ThreadLocal}.
	 * <p>It can be necessary to expose the proxy if an advised object needs
	 * to invoke a method on itself with advice applied. Otherwise, if an
	 * advised object invokes a method on {@code this}, no advice will be applied.
	 * <p>Getting the proxy is analogous to an EJB calling {@code getEJBObject()}.
	 * @see AopContext
	 */
	/*xxx: 获取 代理是否公开为 ThreadLocal*/
	boolean isExposeProxy();

	/**
	 * Set whether this proxy configuration is pre-filtered so that it only
	 * contains applicable advisors (matching this proxy's target class).
	 * <p>Default is "false". Set this to "true" if the advisors have been
	 * pre-filtered already, meaning that the ClassFilter check can be skipped
	 * when building the actual advisor chain for proxy invocations.
	 * @see org.springframework.aop.ClassFilter
	 */
	/*xxx: 设置是否对此代理配置进行前置过滤，使其仅包含匹配的通知器（与此代理的目标类匹配） */
	void setPreFiltered(boolean preFiltered);

	/**
	 * Return whether this proxy configuration is pre-filtered so that it only
	 * contains applicable advisors (matching this proxy's target class).
	 */
	/*xxx: 返回是否对此代理配置进行前置过滤 */
	boolean isPreFiltered();

	/**
	 * Return the advisors applying to this proxy.
	 * @return a list of Advisors applying to this proxy (never {@code null})
	 */
	/*xxx: 获取与此代理相匹配的通知器 */
	Advisor[] getAdvisors();

	/**
	 * Add an advisor at the end of the advisor chain.
	 * <p>The Advisor may be an {@link org.springframework.aop.IntroductionAdvisor},
	 * in which new interfaces will be available when a proxy is next obtained
	 * from the relevant factory.
	 * @param advisor the advisor to add to the end of the chain
	 * @throws AopConfigException in case of invalid advice
	 */
	/*xxx: 为当前的通知器链 添加一个通知器 */
	void addAdvisor(Advisor advisor) throws AopConfigException;

	/**
	 * Add an Advisor at the specified position in the chain.
	 * @param advisor the advisor to add at the specified position in the chain
	 * @param pos position in chain (0 is head). Must be valid.
	 * @throws AopConfigException in case of invalid advice
	 */
	/*xxx: 为当前的通知器链 添加一个通知器，并指定优先级顺序 */
	void addAdvisor(int pos, Advisor advisor) throws AopConfigException;

	/**
	 * Remove the given advisor.
	 * @param advisor the advisor to remove
	 * @return {@code true} if the advisor was removed; {@code false}
	 * if the advisor was not found and hence could not be removed
	 */
	/*xxx: 移除通知器链的某个通知器*/
	boolean removeAdvisor(Advisor advisor);

	/**
	 * Remove the advisor at the given index.
	 * @param index the index of advisor to remove
	 * @throws AopConfigException if the index is invalid
	 */
	/*xxx: 移除通知器链某个位置的通知器*/
	void removeAdvisor(int index) throws AopConfigException;

	/**
	 * Return the index (from 0) of the given advisor,
	 * or -1 if no such advisor applies to this proxy.
	 * <p>The return value of this method can be used to index into the advisors array.
	 * @param advisor the advisor to search for
	 * @return index from 0 of this advisor, or -1 if there's no such advisor
	 */
	/*xxx: 返回某个通知器在通知器链上的位置*/
	int indexOf(Advisor advisor);

	/**
	 * Replace the given advisor.
	 * <p><b>Note:</b> If the advisor is an {@link org.springframework.aop.IntroductionAdvisor}
	 * and the replacement is not or implements different interfaces, the proxy will need
	 * to be re-obtained or the old interfaces won't be supported and the new interface
	 * won't be implemented.
	 * @param a the advisor to replace
	 * @param b the advisor to replace it with
	 * @return whether it was replaced. If the advisor wasn't found in the
	 * list of advisors, this method returns {@code false} and does nothing.
	 * @throws AopConfigException in case of invalid advice
	 */
	/*xxx: 替换某个通知器链的某个通知器 */
	boolean replaceAdvisor(Advisor a, Advisor b) throws AopConfigException;

	/**
	 * Add the given AOP Alliance advice to the tail of the advice (interceptor) chain.
	 * <p>This will be wrapped in a DefaultPointcutAdvisor with a pointcut that always
	 * applies, and returned from the {@code getAdvisors()} method in this wrapped form.
	 * <p>Note that the given advice will apply to all invocations on the proxy,
	 * even to the {@code toString()} method! Use appropriate advice implementations
	 * or specify appropriate pointcuts to apply to a narrower set of methods.
	 * @param advice the advice to add to the tail of the chain
	 * @throws AopConfigException in case of invalid advice
	 * @see #addAdvice(int, Advice)
	 * @see org.springframework.aop.support.DefaultPointcutAdvisor
	 */
	/*xxx: 追加一个通知到通知链尾部 （在内部处理成一个 通知器）*/
	void addAdvice(Advice advice) throws AopConfigException;

	/**
	 * Add the given AOP Alliance Advice at the specified position in the advice chain.
	 * <p>This will be wrapped in a {@link org.springframework.aop.support.DefaultPointcutAdvisor}
	 * with a pointcut that always applies, and returned from the {@link #getAdvisors()}
	 * method in this wrapped form.
	 * <p>Note: The given advice will apply to all invocations on the proxy,
	 * even to the {@code toString()} method! Use appropriate advice implementations
	 * or specify appropriate pointcuts to apply to a narrower set of methods.
	 * @param pos index from 0 (head)
	 * @param advice the advice to add at the specified position in the advice chain
	 * @throws AopConfigException in case of invalid advice
	 */
	/*xxx: 在指定位置追加一个通知（在内部处理成一个 通知器）*/
	void addAdvice(int pos, Advice advice) throws AopConfigException;

	/**
	 * Remove the Advisor containing the given advice.
	 * @param advice the advice to remove
	 * @return {@code true} of the advice was found and removed;
	 * {@code false} if there was no such advice
	 */
	/*xxx: 移除一个通知 （在内部处理成一个 通知器）*/
	boolean removeAdvice(Advice advice);

	/**
	 * Return the index (from 0) of the given AOP Alliance Advice,
	 * or -1 if no such advice is an advice for this proxy.
	 * <p>The return value of this method can be used to index into
	 * the advisors array.
	 * @param advice the AOP Alliance advice to search for
	 * @return index from 0 of this advice, or -1 if there's no such advice
	 */
	/*xxx: 获取某个通知的位置 （在内部处理成一个 通知器）*/
	int indexOf(Advice advice);

	/**
	 * As {@code toString()} will normally be delegated to the target,
	 * this returns the equivalent for the AOP proxy.
	 * @return a string description of the proxy configuration
	 */
	/*xxx: 获取aop配置的文本信息 （在内部处理成一个 通知器）*/
	String toProxyConfigString();

}
