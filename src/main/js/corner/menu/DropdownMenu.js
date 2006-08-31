/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/
dojo.provide("corner.menu.dropdown");
dojo.provide("corner.menu.DropdownMenu");

dojo.require("dojo.logging.*");

dojo.require("dojo.collections.ArrayList");
dojo.require("dojo.html.extras");
dojo.require("dojo.style");
corner.menu.DropdownMenu={
	//下拉菜单的层
	menus:new dojo.collections.ArrayList(),
	popMenuDivs:new dojo.collections.ArrayList(),
	currentPopMenuDiv:null,
	/**
	 * 注册一个菜单
	 * @param id 待注册的菜单ID
	 * 
	 */
	registerMenu:function(id,popMenuDivId){
		var menuNode=dojo.byId(id);
		var popMenuDiv=dojo.byId(popMenuDivId)
		if(menuNode){
			this.menus.add(menuNode);
			if(!popMenuDiv){
				popMenuDiv=document.createElement("div");
				if(popMenuDivId)
					popMenuDiv.id=popMenuDivId;
				dojo.html.setClass(popMenuDiv,"popMenuDiv");
				document.body.appendChild(popMenuDiv);
			}
			this.popMenuDivs.add(popMenuDiv);
			
			dojo.event.connect(menuNode,"onmouseover",this,"showPopMenu");
			dojo.event.connect(menuNode,"onmouseout",this,"delayHidePopMenu");
			
			//建立事件响应
			dojo.event.connect(popMenuDiv,"onmouseover",this,"clearHideMenuHandler");
			//建立时间响应
			dojo.event.connect(popMenuDiv,"onmouseout",this,"delayHidePopMenu");
		}else{
			dojo.raise("wrong node id"+id);
		}
		
	},
	/**
	 * 响应显示下拉菜单．
	 * @param evt 事件句柄
	 */
	showPopMenu:function(evt){
		if(this.currentPopMenuDiv){
			this.currentPopMenuDiv.style.display="none";
			this.currentPopMenuDiv=null;
			
		}
		this.clearHideMenuHandler();
		//得到响应的菜单
		var src=dojo.html.getEventTarget(evt);
		
		//得到当前menu的索引号.
		index =	this.menus.indexOf(src);
		node=this.popMenuDivs.item(index);
		if(!node){
			dojo.raise("popmenu div is null!");
			
		}
		
		//设定下拉菜单的宽度
		contentWidth=dojo.style.getContentBoxWidth(src);
		dojo.style.setContentBoxWidth(node,contentWidth);

		//得到菜单的位置
		with(dojo.style){
			var _left=totalOffsetLeft(src,false);
			var _top=totalOffsetTop(src,false)
		}
		//设定下拉菜单的位置
		with(dojo.html){
			_top+=dojo.html.getContentHeight(src);
			dojo.debug("left :"+_left+" _top:"+_top);
			dojo.html.placeOnScreen(node,_left,_top,[0,0],false);	
		}
		
		//设定显示菜单
		node.style.display="block";
		this.currentPopMenuDiv=node;
		
	},
	hideMenuHandler:null,
	delayHidePopMenu:function(evt){
		this.hideMenuHandler=dojo.lang.setTimeout(this,"hidePopMenu",500);
	},
	hidePopMenu:function(evt){
		dojo.debug("evt:"+evt);
		if(!evt||!dojo.html.overElement(this.currentPopMenuDiv,evt)){
			if(this.currentPopMenuDiv){
				dojo.debug("hide id "+this.currentPopMenuDiv.id+" evt"+evt);
				this.currentPopMenuDiv.style.display="none";
				this.currentPopMenuDiv=null;
			}
		}
		
	},
	clearHideMenuHandler:function(evt){
		dojo.debug("clear hide menu");
		if(this.hideMenuHandler){
			clearTimeout(this.hideMenuHandler);
		}
	},
	setCurrentMenu:function(currentMenuId){
		node=dojo.byId(currentMenuId);
		if(node){
			for(i=0;i<this.menus.count;i++)
				dojo.html.setClass(this.menus.item(i),"menu-normal");
			dojo.html.setClass(node,"menu-selected");
			index=this.menus.indexOf(node);
			if(index>0){
				dojo.html.setClass(this.menus.item(index-1),"menu-selected-before");
			}
		}
	}

}


