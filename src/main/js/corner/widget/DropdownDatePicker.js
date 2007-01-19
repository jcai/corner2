/**
 * 提供一个输入框是disable的DropdownDatePicker脚本
 */
dojo.provide("corner.widget.DropdownDatePicker");
dojo.require("dojo.widget.DropdownDatePicker");
//定义一个DropdownDatePicker的widget.
dojo.widget.defineWidget("corner.widget.DropdownDatePicker");

//继承
dojo.inherits(corner.widget.DropdownDatePicker,dojo.widget.DropdownDatePicker);

//对DropdownDatePicker进行扩展.
dojo.lang.extend(corner.widget.DropdownDatePicker,{
	templateString: '<span style="white-space:nowrap"><input type="hidden" name="" value="" dojoAttachPoint="valueNode" /><input name="" type="text" value="" style="vertical-align:middle;" dojoAttachPoint="inputNode" autocomplete="off" readonly="true" /> <img src="${this.iconURL}" alt="${this.iconAlt}" dojoAttachEvent="onclick:onIconClick" dojoAttachPoint="buttonNode" style="vertical-align:middle; cursor:pointer; cursor:hand" /></span>',	
});