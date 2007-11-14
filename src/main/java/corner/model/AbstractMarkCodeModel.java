// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-05-16
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

package corner.model;

/**
 * 抽象的代码表。
 * @author Jun Tsai
 * @version $Revision:3677 $
 * @since 2006-4-18
 */
public abstract class AbstractMarkCodeModel {
	/**
	 * code，保存在数据库中为数字类型,譬如:2345。
	 * @hibernate.property column="MARK_Code" length="20"
	 * 
	 */
	private String code=null;
	/**
	 * 拼音，供查询使用，保存在数据库中为字母类型，譬如 abcdj。
	 * @hibernate.property column="MARK_PINYIN" length="20"
	 * 
	 */
	private String pinyin;
	/**
	 * 
	 * 项目，供查询使用，保存在数据库中为汉子类型，譬如 我们。
	 * @hibernate.property column="MARK_ITEM" length="20"
	 * 
	 */
	private String item;
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	
	
}
