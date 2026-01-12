-- =================================================================
-- 1. 테마 데이터 삽입 (가장 먼저 실행)
-- =================================================================
INSERT INTO theme (name) VALUES ('가족여행'), ('허니문'), ('골프'), ('휴양'), ('역사탐방'), ('자유여행'), ('도시탐방');


-- =================================================================
-- 2. 여행 패키지 데이터 삽입 (TravelPackage)
-- PK (id)는 1부터 순차적으로 자동 생성됩니다.
-- =================================================================
-- 인기 상품 (is_popular = true) 5개 --
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('제주도 힐링 완전 정복', '대한민국', '제주', '푸른 바다와 오름이 펼쳐진 환상의 섬, 제주에서 완벽한 휴식을 경험하세요.', 1, '2박 3일', '2025-10-01', '2025-12-31', 2, 10, 'ON_SALE', true);
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('활기찬 항구 도시 부산으로', '대한민국', '부산', '광안대교의 야경과 신선한 해산물, 활기찬 해변을 모두 즐길 수 있는 최고의 선택입니다.', 620000, '2박 3일', '2025-09-01', '2025-12-31', 2, 12, 'ON_SALE', true);
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('휴양과 관광을 한번에, 다낭', '베트남', '다낭', '에메랄드빛 미케 해변에서의 휴양과 활기찬 야시장의 즐거움을 동시에 누릴 수 있는 최고의 휴가지입니다.', 1350000, '3박 5일', '2025-10-01', '2026-03-31', 2, 20, 'ON_SALE', true);
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('미식과 쇼핑의 천국, 오사카', '일본', '오사카', '도톤보리의 화려한 밤과 신사이바시의 쇼핑 거리, 맛있는 음식이 가득한 오사카로 떠나보세요.', 980000, '2박 3일', '2025-10-01', '2026-01-31', 2, 15, 'ON_SALE', true);
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('서울의 밤, 시티 라이프 투어', '대한민국', '서울', '역사와 현대가 공존하는 서울의 다채로운 야경과 문화를 즐기는 도심 여행 패키지입니다.', 550000, '1박 2일', '2025-09-01', '2025-11-30', 4, 15, 'ON_SALE', true);

-- 일반 추천 상품 (is_popular = false) 5개 --
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('예술과 낭만의 도시, 파리', '프랑스', '파리', '에펠탑의 야경과 센 강의 낭만, 루브르의 예술 작품을 직접 만나보세요. 잊지 못할 추억을 선사합니다.', 2800000, '6박 8일', '2025-11-01', '2026-02-28', 2, 8, 'ON_SALE', false);
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('영원의 도시, 로마 일주', '이탈리아', '로마', '콜로세움의 웅장함과 트레비 분수의 전설, 고대 로마의 역사를 따라 걷는 특별한 시간 여행.', 2650000, '5박 7일', '2025-10-15', '2026-01-31', 2, 12, 'ON_SALE', false);
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('알프스의 심장, 인터라켄', '스위스', '인터라켄', '만년설이 덮인 융프라우와 그림 같은 호수. 대자연 속에서 즐기는 패러글라이딩과 하이킹의 천국.', 3200000, '6박 8일', '2025-11-01', '2026-04-30', 2, 10, 'ON_SALE', false);
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('에게해의 보석, 산토리니', '그리스', '산토리니', '하얀 건물과 파란 지붕이 어우러진 환상의 섬, 에게해의 보석 산토리니로 떠나보세요.', 3100000, '5박 7일', '2025-10-01', '2026-05-31', 2, 6, 'ON_SALE', false);
INSERT INTO travel_package (name, country, city, description, price, travel_period, departure_date_start, departure_date_end, min_travelers, max_travelers, status, is_popular)
VALUES ('미식의 도시, 싱가포르', '싱가포르', '싱가포르', '다양한 문화가 공존하는 미식의 도시, 싱가포르의 맛을 찾아 떠나는 여행.', 1800000, '3박 5일', '2025-10-01', '2026-02-28', 2, 18, 'ON_SALE', false);


-- =================================================================
-- 3. 상품 이미지 데이터 삽입 (PackageImage)
-- package_id는 위에서 생성된 TravelPackage의 ID와 일치해야 합니다.
-- =================================================================
-- 인기 상품 이미지 --
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (1, '/images/dest-jeju.jpg', true);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (1, '/images/review-greece.jpg', false);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (2, '/images/dest-busan.jpg', true);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (3, '/images/dest-danang.jpg', true);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (3, '/images/review-singapore.jpg', false);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (4, '/images/dest-osaka.jpg', true);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (5, '/images/dest-seoul.jpg', true);

-- 추천 상품 이미지 --
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (6, '/images/dest-paris.jpg', true);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (6, '/images/dest-london.jpg', false);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (7, '/images/dest-rome.jpg', true);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (8, '/images/dest-interlaken.jpg', true);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (9, '/images/dest-santorini.jpg', true);
INSERT INTO package_image (package_id, image_url, is_thumbnail) VALUES (10, '/images/review-singapore.jpg', true);


-- =================================================================
-- 4. 상세 일정 데이터 삽입 (Itinerary)
-- =================================================================
-- 제주도(ID:1) 일정
INSERT INTO itinerary (package_id, day_number, title, description, meal_info, hotel_info) VALUES (1, 1, '제주 도착 및 동부 해안 탐방', '제주 국제공항 도착 후 함덕 해수욕장과 성산일출봉의 장엄한 풍경 감상.', '중식: 흑돼지, 석식: 자유식', '제주 시내 4성급 호텔');
INSERT INTO itinerary (package_id, day_number, title, description, meal_info, hotel_info) VALUES (1, 2, '서귀포 자연 경관 투어', '천지연 폭포와 주상절리대를 방문하여 제주의 대자연을 만끽합니다.', '조식: 호텔식, 중식: 해물뚝배기', '제주 시내 4성급 호텔');
INSERT INTO itinerary (package_id, day_number, title, description, meal_info, hotel_info) VALUES (1, 3, '제주 시내 및 공항 이동', '오전 자유 시간 후 제주 국제공항으로 이동하여 여행 마무리.', '조식: 호텔식', '');
-- 파리(ID:6) 일정
INSERT INTO itinerary (package_id, day_number, title, description, meal_info, hotel_info) VALUES (6, 1, '인천 출발, 파리 도착', '인천 국제공항 출발. 샤를 드골 국제공항 도착 후 호텔로 이동하여 휴식.', '석식: 기내식', '파리 시내 3성급 호텔');
INSERT INTO itinerary (package_id, day_number, title, description, meal_info, hotel_info) VALUES (6, 2, '파리 시내 핵심 투어', '오전: 루브르 박물관 관람. 오후: 에펠탑 전망대 등정 및 센 강 유람선 탑승.', '조식: 호텔식, 중식: 현지식', '파리 시내 3성급 호텔');


-- =================================================================
-- 5. 패키지와 테마 관계 설정 (package_theme - ManyToMany)
-- =================================================================
-- 인기 상품 테마 --
INSERT INTO package_theme (package_id, theme_id) VALUES (1, 1), (1, 4); -- 제주 -> 가족, 휴양
INSERT INTO package_theme (package_id, theme_id) VALUES (2, 7), (2, 6); -- 부산 -> 도시, 자유
INSERT INTO package_theme (package_id, theme_id) VALUES (3, 1), (3, 4); -- 다낭 -> 가족, 휴양
INSERT INTO package_theme (package_id, theme_id) VALUES (4, 6), (4, 7); -- 오사카 -> 자유, 도시
INSERT INTO package_theme (package_id, theme_id) VALUES (5, 7);       -- 서울 -> 도시

-- 추천 상품 테마 --
INSERT INTO package_theme (package_id, theme_id) VALUES (6, 2), (6, 5); -- 파리 -> 허니문, 역사
INSERT INTO package_theme (package_id, theme_id) VALUES (7, 5);       -- 로마 -> 역사
INSERT INTO package_theme (package_id, theme_id) VALUES (8, 4);       -- 인터라켄 -> 휴양
INSERT INTO package_theme (package_id, theme_id) VALUES (9, 2), (9, 4); -- 산토리니 -> 허니문, 휴양
INSERT INTO package_theme (package_id, theme_id) VALUES (10, 7);      -- 싱가포르 -> 도시