// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-05-18
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.hibernate.expression;

/**
 * 用于语法分析器的回调.
 * <p>提供了分析用户输入的查询字符串.譬如: asdf or asdf and fda 等.
 * 
 * @author Jun Tsai
 *
 */
public interface CreateCriterionCallback {
	/**
	 * 创建hibernate的Criterion,根据给定的表达式以及对应的值.
	 * @param expression 表达式符号,譬如: or and 等.
	 * @param value 对应的值.
	 */
	public void doCreateCriterion(String expression,String value);
}
