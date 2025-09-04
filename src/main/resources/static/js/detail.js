document.addEventListener('DOMContentLoaded', function () {
    // 1. 이미지 갤러리 (Swiper.js 연동)
    // 썸네일 갤러리 초기화
    const galleryThumbs = new Swiper('.gallery-thumbs', {
        spaceBetween: 10,
        slidesPerView: 4,
        freeMode: true,
        watchSlidesProgress: true,
    });

    // 메인 갤러리 초기화
    const galleryMain = new Swiper('.gallery-main', {
        spaceBetween: 10,
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        thumbs: {
            swiper: galleryThumbs,
        },
    });

    // 2. 탭 기능
    const tabButtons = document.querySelectorAll('.tab-button');
    const tabPanels = document.querySelectorAll('.tab-panel');

    tabButtons.forEach(button => {
        button.addEventListener('click', () => {
            const targetTab = button.getAttribute('data-tab');

            // 모든 버튼과 패널에서 'active' 클래스 제거
            tabButtons.forEach(btn => btn.classList.remove('active'));
            tabPanels.forEach(panel => panel.classList.remove('active'));

            // 클릭된 버튼과 해당 패널에 'active' 클래스 추가
            button.classList.add('active');
            document.getElementById(targetTab).classList.add('active');
        });
    });
});