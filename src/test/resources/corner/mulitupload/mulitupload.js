	/**
	 * 增加一个节点
	 */
	function add()
		{
			var displayArea = dojo.byId("files");
			var fileCounter = dojo.byId("filecounter");
			var currentValue = fileCounter.value;
			currentValue++
			fileCounter.value = currentValue;
			dojo.debug("currentValue:"+currentValue);
			
			var inputNode = document.createElement("input");
			inputNode.type="file";
			inputNodeName = "file"+currentValue;
			inputNode.name = inputNodeName;
			inputNode.id = inputNodeName;
			
			var buttonNode = document.createElement("input");
			buttonNode.type="button"
			buttonNode.setAttribute("onClick","remove("+currentValue+")");
			buttonNodeName = "button"+currentValue;
			buttonNode.name = buttonNodeName;
			buttonNode.id = buttonNodeName;
			buttonNode.value = "delete"
			
			var divNode=document.createElement("div");
			divNode.id = currentValue;
			
			divNode.appendChild(inputNode);
			divNode.appendChild(buttonNode);
			displayArea.appendChild(divNode);
			
			inputNode.click();
			
		}
		/**
		 * 删除选中节点
		 */
		function remove(obj)
		{
			var displayArea = dojo.byId("files");
			var divNode = dojo.byId(obj.toString());
			displayArea.removeChild(divNode);
		}
		/**
		 * 删除全部节点
		 */
		function removeAll(){
			var displayArea = dojo.byId("files");
			var fileCounter = dojo.byId("filecounter");
			fileCounter.value = 0;
			dojo.dom.removeChildren(displayArea);			
		}
