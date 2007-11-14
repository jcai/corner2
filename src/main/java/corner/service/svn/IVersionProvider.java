// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-10-31
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

package corner.service.svn;

import org.apache.tapestry.annotations.InjectObject;

/**
 * 提供版本服务类.
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision$
 * @since 2.5
 */
public interface IVersionProvider {
	/** 提供版本服务的spring的bean的名称 **/
	public final static String VERSION_SPRING_BEAN_NAME="versionService";
	@InjectObject("spring:"+VERSION_SPRING_BEAN_NAME)
    public abstract SubversionService getSubversionService();
	
	/**
	 * 要显示的版本号1
	 */
	public abstract long getVersionNum();
	
	/***
	 * 要显示的版本号1
	 */
	public abstract  void  setVersionNum(long versionNum);
	
	/**
	 * 要显示的版本号2
	 */
	public abstract long getOtherVersionNum();
	
	/***
	 * 要显示的版本号2
	 */
	public abstract  void  setOtherVersionNum(long versionNum);
	
	/**
	 * 与不提交的版本对比
	 */
	public abstract boolean isCompareLastVer();
	
	/**
	 * 与不提交的版本对比
	 */
	public abstract void setCompareLastVer(boolean b);
}