// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2006-09-27
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

package corner.orm.tapestry.component.radio;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;

/**
 * 扩展了Tapestry中的RadioGroup,实现了指定默认选中某个Radio的功能
 * @author Ghost
 * @version $Revision$
 * @since 2.2.1
 */
public abstract class RadioGroup extends org.apache.tapestry.form.RadioGroup {
	
    

    private int _optionId;
	private Object _selection;
	/**
	 * @see org.apache.tapestry.form.RadioGroup#getNextOptionId()
	 */
	@Override
	public int getNextOptionId() {
		this._optionId++;
		return super.getNextOptionId();
	}
	/**
	 * @see org.apache.tapestry.form.RadioGroup#isSelection(java.lang.Object)
	 */
	@Override
	public boolean isSelection(Object value) {
		if(_selection==null){
			return this._optionId==this.getDefaultIndex();
		}else{
			return super.isSelection(value);
		}
	}
	
	/**
	 * @see org.apache.tapestry.form.RadioGroup#renderFormComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		_selection = getBinding("selected").getObject();
		getBinding("selected").setObject(_selection==null?null:_selection.toString().trim());
		super.renderFormComponent(writer, cycle);
	}
	
	/**
	 * @see org.apache.tapestry.form.RadioGroup#cleanupAfterRender(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void cleanupAfterRender(IRequestCycle cycle) {
		super.cleanupAfterRender(cycle);
		this._selection=null;
		this._optionId = 0;
	}

	/**
     * 得到需要被默认选中的索引，当索引为0时为不选中任何一个值
     * @return 索引有效值,从 1开始.
     */
    public abstract int getDefaultIndex();
}
