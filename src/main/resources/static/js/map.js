
function handleFileSelect(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const imgElement = document.querySelector('.upload-image img');
            imgElement.src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
}

var mapContainer = document.getElementById('map');
var mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
};

var map = new kakao.maps.Map(mapContainer, mapOption);
var geocoder = new kakao.maps.services.Geocoder();
var places = new kakao.maps.services.Places();
var selectedPlaceName = '';
var selectedRealAddress = ''
var selectedLatitude = '';
var selectedLongitude = '';

var marker = new kakao.maps.Marker({
    position: map.getCenter(),
    map: map
});

kakao.maps.event.addListener(map, 'center_changed', function() {
    marker.setPosition(map.getCenter());
});

kakao.maps.event.addListener(map, 'dragend', function() {
    updateLocationInfo();
});

function searchPlaces() {
    var keyword = document.getElementById('searchBox').value;
    places.keywordSearch(keyword, placesSearchCB);
}

function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        displayPlaces(data);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 없습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 중 오류가 발생했습니다.');
        return;
    }
}

function displayPlaces(places) {
    var searchResults = document.getElementById('searchResults');
    searchResults.style.display = 'block';
    searchResults.innerHTML = '';
    for (var i = 0; i < places.length; i++) {
        var div = document.createElement('div');
        div.className = 'search-result';
        div.onclick = (function(placeName, lat, lng) {
            return function() {
                selectedPlaceName = placeName;
                moveToSelectedPlace(lat, lng);
                hideSearchResults();
            };
        })(places[i].place_name, places[i].y, places[i].x);
        div.innerHTML = places[i].place_name + ' (' + places[i].address_name + ')';
        searchResults.appendChild(div);
    }
}

function moveToSelectedPlace(lat, lng) {
    var moveLatLon = new kakao.maps.LatLng(lat, lng);
    map.setCenter(moveLatLon);
    updateLocationInfo();
    showMap();
}

function updateLocationInfo() {
    var center = map.getCenter();
    geocoder.coord2RegionCode(center.getLng(), center.getLat(), function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            document.getElementById('address').innerText = '법정동: ' + result[0].address_name;
            selectedLatitude = center.getLat();
            selectedLongitude = center.getLng();
            selectedLawRegion = result[0].address_name;
            console.log(selectedLawRegion);
        }
    });
    geocoder.coord2Address(center.getLng(), center.getLat(), function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var roadAddress = result[0].road_address ? result[0].road_address.address_name : result[0].address.address_name;
            document.getElementById('real-address').innerText = '주소: ' + roadAddress;
            selectedRealAddress = roadAddress;
        }
    });
    document.getElementById('coordinates').innerText = '위도: ' + center.getLat() + ', 경도: ' + center.getLng();
}

function openModal() {
    document.getElementById('myModal').style.display = 'block';
    resetModal();
}

function closeModal() {
    document.getElementById('myModal').style.display = 'none';
}

function resetModal() {
    document.getElementById('searchBox').value = '';
    document.getElementById('searchResults').innerHTML = '';
    document.getElementById('searchResults').style.display = 'block';
    document.getElementById('map').style.display = 'none';
    document.getElementById('location-info').style.display = 'none';
    setTimeout(function() {
        map.relayout();
        map.setCenter(new kakao.maps.LatLng(33.450701, 126.570667));
        marker.setPosition(map.getCenter());
        updateLocationInfo();
    }, 0);
}

function showMap() {
    document.getElementById('map').style.display = 'block';
    document.getElementById('location-info').style.display = 'block';
}

function hideSearchResults() {
    document.getElementById('searchResults').style.display = 'none';
}

function confirmLocation() {
    document.getElementById('photoSpotName').value = selectedPlaceName;
    document.getElementById('realAddress').value = selectedRealAddress;
    closeModal();
}

document.getElementById('submit').addEventListener('click', function(event) {
    event.preventDefault();
    var imageUpload = document.getElementById('imageUpload').files[0];
    var contents = document.querySelector('textarea[name="text"]').value;
    var contentsDate = document.querySelector('input[name="date"]').value;
    var photoSpotName = document.getElementById('photoSpotName').value;
    var realAddress = document.getElementById('realAddress').value;

    var formData = new FormData();
    formData.append('image', imageUpload);
    formData.append('feed', new Blob([JSON.stringify({
        contents: contents,
        lawRegion: selectedLawRegion,
        contentsDate: contentsDate,
        photoSpotName: photoSpotName,
        latitude: selectedLatitude,
        longitude: selectedLongitude,
        address: realAddress
    })], { type: 'application/json' }));

    fetch('/feeds', {
        method: 'POST',
        body: formData
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    }).then(data => {
        console.log('Success:', data);
        let feedId = data.data.feedId;
        alert('피드가 성공적으로 업로드되었습니다.');
        window.location.replace(`/feeds-detail/${feedId}`)
    }).catch((error) => {
        console.error('Error:', error);
        alert('피드 업로드 중 오류가 발생했습니다.');
    });
});