(function initializeMap(){
    var googleMap = new google.maps.Map(document.getElementById('map'), {
        center: {
            lat: 39.106,  lng: -84.5187659
        },
        zoom: 15,
        scrollwheel: false,
        mapTypeControl: false,
        zoomControl: false,
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

    //create the marker for the Millenium.
    var theMilleniumMarker = new google.maps.Marker({
        position: { lat: 39.1016336,  lng: -84.5159766 },
        map: googleMap,
        title: "The Millenium Hotel",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    //create the marker for The Westin Cincinnati.
    var theWestinMarker = new google.maps.Marker({
        position: { lat: 39.1009303,  lng: -84.5124668 },
        map: googleMap,
        title: "The Westin Cincinnati",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    //create the marker for The Cincinnatian Hotel.
    var theCincinnatianMarker = new google.maps.Marker({
        position: { lat: 39.1026158,  lng: -84.5138112 },
        map: googleMap,
        title: "The Cincinnatian Hotel",
        animation: google.maps.Animation.DROP,
        icon: 'img/icon/marker-64.png'
    });

    //create the info window that shows when clicking on the marker.
    var theTranseptInfoWindow = new google.maps.InfoWindow({
        content: "The Transept"
    });

    var theWestinInfoWindow = new google.maps.InfoWindow({
        content: "The Westin Cincinnati"
    });

    var theCincinnatianInfoWindow = new google.maps.InfoWindow({
        content: "The Cincinnatian Hotel"
    });

    var theMilleniumInfoWindow = new google.maps.InfoWindow({
        content: "Millennium Hotel Cincinnati"
    });

    theWestinMarker.addListener('click', function() {
        theWestinInfoWindow.open(googleMap, theWestinMarker);
    });

    theCincinnatianMarker.addListener('click', function() {
        theCincinnatianInfoWindow.open(googleMap, theCincinnatianMarker);
    });

    theTranseptMarker.addListener('click', function() {
        theTranseptInfoWindow.open(googleMap, theTranseptMarker);
    });

    theMilleniumMarker.addListener('click', function() {
        theMilleniumInfoWindow.open(googleMap, theMilleniumMarker);
    });
})();