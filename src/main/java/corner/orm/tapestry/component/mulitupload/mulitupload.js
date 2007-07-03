	/**
	 * 增加一个节点
	 */
	function add(image_url)
		{
			var displayArea = dojo.byId("files");
			var fileCounter = dojo.byId("filecounter");
			var currentValue = fileCounter.value;
			currentValue++
			fileCounter.value = currentValue;
			dojo.debug("currentValue:"+currentValue);

			var innerData = "<input type='file' name='file"+currentValue+"' id='file"+currentValue
							+"'/><img scr=\"\" border=\"0\" name='img"+currentValue+"' id='img"
							+currentValue+"' onclick=\"remove('"+currentValue+"')\" value=\"删除\"/>";
			
			var divNode=document.createElement("div");
			divNode.id = currentValue;
			divNode.innerHTML = innerData;
			
			displayArea.appendChild(divNode);
			
		}
		/**
		 * 删除选中节点
		 */
		function remove(obj)
		{
			var displayArea = dojo.byId("files");
			var divNode = dojo.byId(obj.toString());
			dojo.debug(divNode);
			dojo.dom.removeNode(divNode);
		}
		/**
		 * 删除全部节点
		 */
		function removeAll(){
			var displayArea = dojo.byId("files");
			var fileCounter = dojo.byId("filecounter").value;
			
			for(var i=1;i<=fileCounter;i++){
				var divNode = dojo.byId(i.toString());
				if(divNode != null){
//					dojo.debug(fileCounter);
					dojo.dom.removeChildren(divNode);
				}
			}			
		}
