var eventModule = (function(){
	
	var elements = {};
	
	return {
		
		add: function(elementName, element){
			elements[elementName] = element;
		},
		
		remove: function(elementName){
			elements[elementName] = null;
		},
		
		attach: function(elementName, eventName, listener){
			var element = elements[elementName];
			
			if(element === undefined || element === null){
				throw "There is no element registered with the name '" + elementName + "'.";
			}

			element.addEventListener(eventName, listener);
		},
		
		detach: function(elementName, eventName, listener){
			var element = elements[elementName];
			
			if(element === undefined || element === null){
				throw "There is no element registered with the name '" + elementName + "'.";
			}
			
			element.removeEventListener(eventName, listener);
		}
	};
	
})();