/*
	Copyright (c) Beijing Maxinfo Technology Co.,Ltd.
	All Rights Reserved.

*/
dojo.provide("corner.menu.slide");

dojo.require("dojo.event.*");
dojo.require("dojo.logging.*");
dojo.require("dojo.html.extras");
dojo.require("dojo.collections.ArrayList");

corner.menu.slide=function(menuId){
	dojo.debug(menuId);
	this.menu=dojo.byId(menuId);
	if(!this.menu){
		dojo.raise("wrong menu id");
		return;
	};
	this.movingHandler=null;
	this.moveoutMenu=function(evt){
		dojo.debug("on mouse over,over evt:["+evt+"]");
		if(this.movingHandler){
			clearTimeout(this.movingHandler);
		}
		left=dojo.style.totalOffsetLeft(this.menu,true);
		if(left<=0){
			this.movingHandler=dojo.lang.setTimeout(this,"moveoutMenu",this.speed);
			if((left+10)>0)
				this.moveMenu(0,0);
			else
				this.moveMenu(left,10);
		}
	};
	this.delayMovebackMenu=function(evt){
		dojo.debug("on mouse out,over evt:["+evt+"]");

		if(this.movingHandler){
			clearTimeout(this.movingHandler);
		}
		dojo.debug("left::"+left+" init left::"+this.menuInitLeft);
		if(evt&&dojo.html.overElement(this.menu,evt)){
			this.movingHandler=dojo.lang.setTimeout(this,"delayMovebackMenu",500);
			return;
		}else{
			this.movebackMenu();
		}
		
	};
	this.movebackMenu=function(){
		if(this.movingHandler){
			clearTimeout(this.movingHandler);
		}
		left=dojo.style.totalOffsetLeft(this.menu,true);
		if(left>this.menuInitLeft){
					
			this.movingHandler=dojo.lang.setTimeout(this,"movebackMenu",this.speed);
			if((left-10)<this.menuInitLeft){
				this.moveMenu(this.menuInitLeft,0);
			}else{
				this.moveMenu(left,-10);
			}
		}
		
	}

	this.moveMenu=function(left,distance){
		this.menu.style.left=(left+distance)+"px";
	}
	this.buildMenu=function(){
		this.menuWidth=dojo.style.getContentWidth(this.menu);
		this.barWidth=dojo.style.getContentWidth(this.menuBar);
		this.menuInitLeft=this.barWidth-this.menuWidth;

		dojo.html.placeOnScreen(this.menu,this.menuInitLeft,this.topDistance,[-this.menuWidth,0],false);
		//this.menu.style.top=this.topDistance;
		//this.menu.style.left=this.menuInitLeft;
		this.moveCorrectPosition();
	};
	this.menuInitLeft=0;
	this.scrollCached=null;
	this.fixPositionHandler=null;
	this.moveCorrectPosition=function(){
		if(this.fixPositionHandler){
			clearTimeout(this.fixPositionHandler);
		}
		var scroll = dojo.html.getScrollOffset();

		this.menu.style.top=(this.topDistance+scroll.y)+"px";		
		this.fixPositionHandler=dojo.lang.setTimeout(this,"moveCorrectPosition",500);
	}
	this.menuWidth=0;
	this.barWidth=0;
	this.topDistance=0;
	this.setTop=function(top){
		this.topDistance=top;
	}
	this.setSpeed=function(speed){
		this.speed=speed;
	};
	this.setMenuBarId=function(menuBarId){
		this.menuBar=dojo.byId(menuBarId);
	}


	dojo.event.connect(this.menu,"onmouseover",this,"moveoutMenu");
	dojo.event.connect(this.menu,"onmouseout",this,"delayMovebackMenu");
	
//	layer1=dojo.byId("layer1");
//	dojo.event.connect(layer1,"onmouseover",this,"moveoutMenu");
//	dojo.event.connect(layer1,"onmouseout",this,"delayMovebackMenu");
	
	

};