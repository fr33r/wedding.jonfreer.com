/*
	Global utility object.
*/
function Utility(){

	/*
		Adds a class to a specified element.
	*/
	this.addClass = function(classToAdd, element){
		try{
			//gaurd clause.
			if(classToAdd !== undefined && classToAdd !== null &&
				element !== undefined && element !== null){

				//check to see if the element has any classes.
				if(element.className.length == 0){
					element.className += classToAdd;
				}else{
					element.className += (" " + classToAdd);
				}

				console.log("Successfully added the '" + classToAdd + "'.");

			}else{
				throw new ArguementException("You did not specify the class to add or the element to add the class to.");
			}
		}catch(exception){
			window.alert(exception.exceptionMessage);
		}
	}

	/*
		Removes a class from the specified element.
	*/
	this.removeClass = function(classToRemove, element){
		try{
			//gaurd clause.
			if(classToRemove !== undefined && classToRemove !== null &&
				element !== undefined && element !== null){

				//grab the classes applied to the element.
				var classes = element.className.split(" ");

				//check to see if there are any classes applied to the element.
				if(classes.length > 0){

					//classes not removed.
					var classesNotRemoved = [];

					for(var i = 0; i < classes.length; i++){
						if(classes[i] !== classToRemove){
							classesNotRemoved.push(classes[i]);
						}
					}

					element.className = classesNotRemoved.length !== 0 ? classesNotRemoved.join(" ") : "";
				}else{
					element.className = "";
				}

			}else{
				throw new ArguementException("You did not specify the class to add or the element to remove the class from.");
			}
		}catch(exception){
			window.alert(exception.exceptionMessage);
		}
	}

	/*
		Checks to see if an element has a class applied.
	*/
	this.hasClass = function(classToLookFor, element){
		try{
			//gaurd clause.
			if(classToLookFor !== undefined && classToLookFor !== null &&
				element !== undefined && element !== null){

				//grab the classes applied to the element.
				var classes = element.className.split(" ");

				//check to see if there are any classes applied to the element.
				if(classes.length > 0){

					for(var i = 0; i < classes.length; i++){
						if(classes[i] === classToLookFor){
							return true;
						}
					}

				}

				return false;
				
			}else{
				throw new ArguementException("You did not specify the class to add or the element to remove the class from.");
			}
		}catch(exception){
			window.alert(exception.exceptionMessage);
		}
	}
}