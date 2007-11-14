// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2007-11-01
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

import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;



/**
 * 针对目录操作的表示类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
class DirectoryFacade {
	/** 记录所有的目录callback **/
	private List<DirectoryCallback> pathList;
	/** 资源库路径的分割符**/
	final static String SVN_PATH_SEPERATOR="/";
	/**
	 * 创建一个目录的facade.
	 * @param repository 资源路径.
	 * @param path 目录
	 * @throws SVNException
	 */
	DirectoryFacade(SVNRepository repository,String path) throws SVNException{
		//根据路径来创建Directory callback
	     String [] paths=path.split(SVN_PATH_SEPERATOR);
	     pathList = new ArrayList<DirectoryCallback>();
	     StringBuffer sb=new StringBuffer();
	     sb.append("/");
	     for(String tmp:paths){
	    	 sb.append(tmp);
	    	 DirectoryCallback callback=new DirectoryCallback(repository,sb.toString());
	    	 pathList.add(callback);
	    	 sb.append(SVN_PATH_SEPERATOR);
	     }
	}

	/**
	 * 打开路径
	 * @param editor svn editor 
	 * @throws SVNException 
	 */
	void openPath(ISVNEditor editor) throws SVNException{
		for(DirectoryCallback callback:pathList){
			callback.openPath(editor);
		}
	}
	/**
	 * 关闭路径.
	 * @param editor svn editor 
	 * @throws SVNException 
	 */
	void closePath(ISVNEditor editor) throws SVNException{
		for(DirectoryCallback callback:pathList){
			callback.closePath(editor);
		}
	}
}
