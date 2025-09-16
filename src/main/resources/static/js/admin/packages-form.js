// package-form.js
document.addEventListener('DOMContentLoaded', function () {

    // --- Itinerary Dynamic Add/Remove ---
    const addItineraryBtn = document.getElementById('add-itinerary-btn');
    const itineraryContainer = document.getElementById('itinerary-container');
    let itineraryIndex = document.querySelectorAll('.itinerary-item').length;

    addItineraryBtn.addEventListener('click', function () {
        const newItinerary = document.createElement('div');
        newItinerary.classList.add('itinerary-item');
        newItinerary.innerHTML = `
            <button type="button" class="remove-itinerary-btn">&times;</button>
            <h4>${itineraryIndex + 1}일차</h4>
            <div class="form-group">
                <label>일정 제목</label>
                <input type="text" class="form-control" name="itineraries[${itineraryIndex}].title">
            </div>
            <div class="form-group">
                <label>상세 설명</label>
                <textarea class="form-control" name="itineraries[${itineraryIndex}].description"></textarea>
            </div>
             <input type="hidden" name="itineraries[${itineraryIndex}].dayNumber" value="${itineraryIndex + 1}">
        `;
        itineraryContainer.appendChild(newItinerary);
        itineraryIndex++;
    });

    itineraryContainer.addEventListener('click', function(e) {
        if (e.target && e.target.classList.contains('remove-itinerary-btn')) {
            e.target.closest('.itinerary-item').remove();
            // Re-index day numbers after removal
            const items = itineraryContainer.querySelectorAll('.itinerary-item');
            items.forEach((item, index) => {
                item.querySelector('h4').textContent = `${index + 1}일차`;
                item.querySelector('input[name*="dayNumber"]').value = index + 1;
            });
            itineraryIndex = items.length;
        }
    });


    // --- Image Preview ---
    const imageFileInput = document.getElementById('imageFiles');
    const imagePreviewContainer = document.getElementById('image-preview-container');

    imageFileInput.addEventListener('change', function(event) {
        imagePreviewContainer.innerHTML = ''; // Clear existing previews
        const files = event.target.files;

        for (const file of files) {
            if (file.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    const previewItem = document.createElement('div');
                    previewItem.classList.add('image-preview-item');
                    previewItem.innerHTML = `<img src="${e.target.result}" alt="${file.name}">`;
                    imagePreviewContainer.appendChild(previewItem);
                }
                reader.readAsDataURL(file);
            }
        }
    });
});