// DOM이 모두 로드된 후에 스크립트를 실행합니다.
document.addEventListener('DOMContentLoaded', function () {

    // 인기 여행 상품 슬라이드 초기화 (변경 없음)
    const popularSwiper = new Swiper('.popular-swiper', {
        slidesPerView: 1,
        spaceBetween: 20,
        loop: true,
        navigation: {
            nextEl: '.popular-swiper .swiper-button-next',
            prevEl: '.popular-swiper .swiper-button-prev',
        },
        breakpoints: {
            768: {
                slidesPerView: 2,
                spaceBetween: 30
            },
            1024: {
                slidesPerView: 3,
                spaceBetween: 30
            }
        }
    });

    // =================================================================
    // 추천 여행 상품 슬라이드 초기화 (수정된 부분)
    // =================================================================
    const normalSwiper = new Swiper('.normal-swiper', {
        // 한 번에 보여줄 슬라이드 개수
        slidesPerView: 1,
        // 슬라이드 간의 간격
        spaceBetween: 20,
        // 무한 루프 활성화
        loop: true,

        // 페이지네이션 (점)
        pagination: {
            el: '.normal-swiper .swiper-pagination',
            clickable: true,
        },

        // 좌우 이동 버튼
        navigation: {
            nextEl: '.normal-swiper .swiper-button-next',
            prevEl: '.normal-swiper .swiper-button-prev',
        },

        // 화면 크기에 따른 반응형 설정
        breakpoints: {
            // 640px 이상일 때
            640: {
                slidesPerView: 2,
                spaceBetween: 20,
            },
            // 768px 이상일 때
            768: {
                slidesPerView: 3,
                spaceBetween: 30,
            },
            // 1200px 이상일 때
            1200: {
                slidesPerView: 4,
                spaceBetween: 24,
            }
        }
    });

});
