var cacheModule = (function(){
	
	var cache = {};
	
	return {
		
		add: function(uri, response){
			cache[uri.toLowerCase()] = response;
		},
		
		remove: function(uri){
			cache[uri.toLowerCase()] = null;
		},
		
		get: function(uri){
			if(cache[uri.toLowerCase()] === undefined || cache[uri.toLowerCase()] === null){
				return null;
			}
			
			return cache[uri.toLowerCase()];
		}
		
	};
	
})();