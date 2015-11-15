/*
	Exception object that should be thrown when an arguement
	to a function was not specified or incorrect.
*/
function ArguementException(message){
	this.exceptionMessage = message;
}

/*
	Exception object that should be thrown when a class is being
	applied to an element and the element already has that class
	applied to it.
*/
function ClassAlreadyAppliedException(message){
	this.exceptionMessage = message;
}

/*
	Exception object that should be thrown when a class is being
	removed from an element and the element does not have the class
	being removed.
*/
function ClassNotFoundException(message){
	this.exceptionMessage = message;
}

/*
	Exception object that should be thrown when a single page has
	more than one modal containers present.
*/
function TooManyModalContainersException(message){
	this.exceptionMessage = message;
}

/*
	Exception object that should be thrown when a single page
	that desired to use a modal view, has no modal container.
*/
function ModalContainerNotFoundException(message){
	this.exceptionMessage = message;
}

/*
	Exception object that should be thrown when a single page
	that desired to use a modal view, has no modal container.
*/
function ModalContentNotFoundException(message){
	this.exceptionMessage = message;
}