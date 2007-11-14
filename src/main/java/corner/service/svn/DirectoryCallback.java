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

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;

/**
 * 针对目录的进行操作的判断
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision$
 * @since 2.5
 */
class DirectoryCallback {
	private String path;
	
	private boolean noExist;

	/**
	 * 根据repository以及对应的目录来创建一个callback.
	 * @param repository 资源库.
	 * @param path 目录 
	 * @throws SVNException 
	 */
	DirectoryCallback(SVNRepository repository,String path) throws SVNException{
		this.path = path;
		this.noExist= repository.checkPath(path, -1) == SVNNodeKind.NONE;
	}
	/**
	 * 打开对应的目录，如果改目录不存在，则进行创建
	 * @param editor Subversion editor
	 * @throws SVNException 
	 */
	void openPath( ISVNEditor editor) throws SVNException{
		if(noExist){
			editor.addDir(path,null,-1);
		}else{
			editor.openDir(path,-1);
		}
	}
	/**
	 * 管理路径
	 * @param editor svn编辑器.
	 * @throws SVNException
	 */
	void closePath(ISVNEditor editor) throws SVNException{
		editor.closeDir();
	}
}
