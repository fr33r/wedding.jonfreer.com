function initializeMap(){
    var googleMap = new google.maps.Map(document.getElementById('map'), {
        center: {
            lat: 39.14,  lng: -84.5187659
        },
        zoom: 12,
        scrollwheel: false,
        mapTypeControl: false,
        zoomControl: true,
        streetViewControl: false,
        styles:         
        [
            {
                "featureType": "water",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#e9e9e9"
                    },
                    {
                        "lightness": 17
                    }
                ]
            },
            {
                "featureType": "landscape",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#f5f5f5"
                    },
                    {
                        "lightness": 20
                    }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "geometry.fill",
                "stylers": [
                    {
                        "color": "#ffffff"
                    },
                    {
                        "lightness": 17
                    }
                ]
            },
            {
                "featureType": "road.highway",
                "elementType": "geometry.stroke",
                "stylers": [
                    {
                        "color": "#ffffff"
                    },
                    {
                        "lightness": 29
                    },
                    {
                        "weight": 0.2
                    }
                ]
            },
            {
                "featureType": "road.arterial",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#ffffff"
                    },
                    {
                        "lightness": 18
                    }
                ]
            },
            {
                "featureType": "road.local",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#ffffff"
                    },
                    {
                        "lightness": 16
                    }
                ]
            },
            {
                "featureType": "poi",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#f5f5f5"
                    },
                    {
                        "lightness": 21
                    }
                ]
            },
            {
                "featureType": "poi.park",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#dedede"
                    },
                    {
                        "lightness": 21
                    }
                ]
            },
            {
                "elementType": "labels.text.stroke",
                "stylers": [
                    {
                        "visibility": "on"
                    },
                    {
                        "color": "#ffffff"
                    },
                    {
                        "lightness": 16
                    }
                ]
            },
            {
                "elementType": "labels.text.fill",
                "stylers": [
                    {
                        "saturation": 36
                    },
                    {
                        "color": "#333333"
                    },
                    {
                        "lightness": 40
                    }
                ]
            },
            {
                "elementType": "labels.icon",
                "stylers": [
                    {
                        "visibility": "off"
                    }
                ]
            },
            {
                "featureType": "transit",
                "elementType": "geometry",
                "stylers": [
                    {
                        "color": "#f2f2f2"
                    },
                    {
                        "lightness": 19
                    }
                ]
            },
            {
                "featureType": "administrative",
                "elementType": "geometry.fill",
                "stylers": [
                    {
                        "color": "#fefefe"
                    },
                    {
                        "lightness": 20
                    }
                ]
            },
            {
                "featureType": "administrative",
                "elementType": "geometry.stroke",
                "stylers": [
                    {
                        "color": "#fefefe"
                    },
                    {
                        "lightness": 17
                    },
                    {
                        "weight": 1.2
                    }
                ]
            }
        ]

    });

    //create the marker for the Transept.
    var theTranseptMarker = new google.maps.Marker({
        position: { lat: 39.1079041,  lng: -84.5187659 },
        map: googleMap,
        title: "The Transept",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/red-marker-64.png'
    });

    //create the marker for Rhinegeist.
    var rhinegeistMarker = new google.maps.Marker({
        position: { lat: 39.1171843,  lng: -84.5200749 },
        map: googleMap,
        title: "Rhinegeist Brewery",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    //create the marker for MadTree.
    var madTreeMarker = new google.maps.Marker({
        position: { lat: 39.1667096,  lng: -84.4197198 },
        map: googleMap,
        title: "MadTree Brewery",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    //create the marker for the Reds game.
    var redsMarker = new google.maps.Marker({
        position: { lat: 39.0972431,  lng: -84.5066258 },
        map: googleMap,
        title: "Cincinnati Reds vs. Pittsburg Pirates",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    //create the marker for the Oktoberfest.
    var oktoberfestMarker = new google.maps.Marker({
        position: { lat: 39.1017886,  lng: -84.5127938 },
        map: googleMap,
        title: "Oktoberfest",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    //create the marker for the Oktoberfest.
    var cityFleaMarker = new google.maps.Marker({
        position: { lat: 39.1092335,  lng: -84.5172957 },
        map: googleMap,
        title: "The City Flea",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    //create the marker for the Oktoberfest.
    var graetersMarker = new google.maps.Marker({
        position: { lat: 39.101658,  lng: -84.511846 },
        map: googleMap,
        title: "Graeter's Ice Cream",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    var skylineMarker = new google.maps.Marker({
        position: { lat: 39.103494,  lng: -84.513775 },
        map: googleMap,
        title: "Skyline Chili",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    var horseshoeMarker = new google.maps.Marker({
        position: { lat: 39.108285,  lng: -84.504839 },
        map: googleMap,
        title: "Horseshoe Casino",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    var moerleinMarker = new google.maps.Marker({
        position: { lat: 39.096308,  lng: -84.508687 },
        map: googleMap,
        title: "Moerlein Lager House",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    //create the info window that shows when clicking on the marker.
    var theTranseptInfoWindow = new google.maps.InfoWindow({
        content: "The Transept"
    });

    var rhinegeistInfoWindow = new google.maps.InfoWindow({
        content: "Rhinegeist Brewery"
    });

    var madTreeInfoWindow = new google.maps.InfoWindow({
        content: "MadTree Brewery"
    });

    var redsInfoWindow = new google.maps.InfoWindow({
        content: "Cincinnati Reds vs. Pittsburg Pirates"
    });

    var oktoberfestInfoWindow = new google.maps.InfoWindow({
        content: "Oktoberfest"
    });

    var cityFleaInfoWindow= new google.maps.InfoWindow({
        content: "The City Flea"
    });

    var graetersInfoWindow = new google.maps.InfoWindow({
        content: "Graeter's Ice Cream"
    });

    var skylineInfoWindow = new google.maps.InfoWindow({
        content: "Skyline Chili"
    });

    var horseshoeInfoWindow = new google.maps.InfoWindow({
        content: "Horseshoe Casino"
    });

    var moerleinInfoWindow = new google.maps.InfoWindow({
        content: "Moerlein Lager House"
    });

    theTranseptMarker.addListener('click', function() {
        theTranseptInfoWindow.open(googleMap, theTranseptMarker);
    });

    rhinegeistMarker.addListener('click', function() {
        rhinegeistInfoWindow.open(googleMap, rhinegeistMarker);
    });

    madTreeMarker.addListener('click', function() {
        madTreeInfoWindow.open(googleMap, madTreeMarker);
    });

    redsMarker.addListener('click', function() {
        redsInfoWindow.open(googleMap, redsMarker);
    });

    oktoberfestMarker.addListener('click', function() {
        oktoberfestInfoWindow.open(googleMap, oktoberfestMarker);
    });

    cityFleaMarker.addListener('click', function() {
        cityFleaInfoWindow.open(googleMap, cityFleaMarker);
    });

    graetersMarker.addListener('click', function() {
        graetersInfoWindow.open(googleMap, graetersMarker);
    });

    skylineMarker.addListener('click', function() {
        skylineInfoWindow.open(googleMap, skylineMarker);
    });

    horseshoeMarker.addListener('click', function() {
        horseshoeInfoWindow.open(googleMap, horseshoeMarker);
    });

    moerleinMarker.addListener('click', function() {
        moerleinInfoWindow.open(googleMap, moerleinMarker);
    });

}