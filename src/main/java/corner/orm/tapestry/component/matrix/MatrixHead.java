// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-10-19
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

package corner.orm.tapestry.component.matrix;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.components.ForBean;

import corner.orm.hibernate.v3.MatrixRow;

/**
 * 显示矩阵头的部分.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author Ghost
 * @version $Revision$
 * @since 2.2.2
 */
public abstract class MatrixHead extends BaseComponent {
	@Component(type="For",bindings={"source=refVector","value=headObj"})
	public abstract ForBean getHeadIter();
	
	
	public abstract void setHeadObj(Object headObj);
	public abstract Object getHeadObj();
	
	
	@Parameter(required=true)
	public abstract MatrixRow getRefVector();
	
	@Parameter(defaultValue="-1")
	public abstract int getRefSize();
	
	/**
	 * 是否继续循环.
	 * @return
	 */
	public  boolean isLoop(){
		if(this.getRefSize()==-1){
			return true;
		}
		return this.getRefSize()>this.getHeadIter().getIndex();
	}
	/**
	 * 是否需要补齐剩下的空格
	 * @return 
	 */
	public boolean isNeedFill(){
		return this.getRefSize()>(this.getRefVector()!=null?this.getRefVector().size():0);
	}
	
	public int [] getFillSource(){
		return new int[this.getRefSize()-this.getRefVector().size()];
	}
}
