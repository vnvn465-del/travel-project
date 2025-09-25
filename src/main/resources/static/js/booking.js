// 가장 안정적인 방법입니다.
$(document).ready(function() {

    // 1. 필요한 HTML 요소들을 가져옵니다. JQuery의 선택자 '$'를 사용합니다.
    const paymentButton = $('#payment-btn');

    // 만약 이 페이지에 'payment-btn' ID를 가진 버튼이 없다면,
    // 아무것도 실행하지 않고 스크립트를 즉시 종료합니다. (오류 방지)
    if (paymentButton.length === 0) {
        return;
    }

    // CSRF 토큰 정보를 HTML의 meta 태그에서 가져옵니다.
    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");

    // 아임포트(Portone) 라이브러리를 초기화합니다.
    const IMP = window.IMP;
    IMP.init("iamport"); // V1 방식과 호환되는 범용 상점 ID

    // 2. '결제하기' 버튼에 클릭 이벤트 리스너를 추가합니다.
    paymentButton.on('click', function () {
        // 숨겨진 input 필드들로부터 예약 정보를 수집하여 JavaScript 객체를 만듭니다.
        const bookingData = {
            packageId: $('#packageId').val(),
            departureDate: $('#departureDate').val(),
            travelerCount: $('#travelerCount').val()
        };

        // 3. 서버에 사전 예약 정보 생성을 요청합니다. (JQuery AJAX 사용)
        $.ajax({
            url: "/booking/prepare",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(bookingData),
            beforeSend: function(xhr) {
                // AJAX 요청을 보내기 직전에, 헤더에 CSRF 토큰을 추가합니다.
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(booking) {
                // 4. 서버로부터 성공적으로 예약 정보를 받으면, 해당 정보로 결제창을 호출합니다.
                if (booking && booking.bookingNumber) {
                    requestPay(booking);
                } else {
                    alert('서버로부터 유효한 예약 정보를 받지 못했습니다.');
                }
            },
            error: function(xhr, status, error) {
                // 서버로부터 에러 응답을 받으면, 에러 내용을 알림창으로 보여줍니다.
                console.error("Error during booking preparation:", xhr.responseText);
                alert("예약 준비 중 오류가 발생했습니다.\n" + xhr.responseText);
            }
        });
    });

    /**
     * 아임포트 결제창을 호출하는 함수입니다.
     * @param {object} booking - 서버로부터 받은 사전 예약 정보 객체
     */
    function requestPay(booking) {
        IMP.request_pay({
            // KG이니시스 테스트 환경의 불안정성을 피해, 가장 안정적인 다날 테스트 PG를 사용합니다.
            pg: "danal_tpay.9810030929",
            pay_method: "card",
            merchant_uid: booking.bookingNumber,  // 우리가 생성한 고유 예약 번호
            name: booking.packageInfo.name,       // 상품명
            amount: booking.totalPrice,           // 총 결제 금액
            buyer_email: booking.userInfo.email,
            buyer_name: booking.userInfo.name
        }, function (rsp) { // 결제창 완료 후 실행될 콜백 함수
            if (rsp.success) {
                // 5. 결제가 성공하면, 서버에 최종 검증을 요청합니다.
                verifyPayment(rsp.imp_uid, rsp.merchant_uid);
            } else {
                alert("결제에 실패했습니다: " + rsp.error_msg);
            }
        });
    }

    /**
     * 서버에 결제 정보의 최종 검증을 요청하는 함수입니다.
     * @param {string} impUid - 아임포트가 발급한 결제 고유 ID
     * @param {string} merchantUid - 우리가 전달했던 예약 번호
     */
    function verifyPayment(impUid, merchantUid) {
        $.ajax({
            url: "/booking/verify",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({ impUid: impUid, merchantUid: merchantUid }),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
},
            success: function() {
                // 6. 최종 검증까지 성공하면, 예약 완료 페이지로 이동합니다.
                window.location.href = '/booking/success';
            },
            error: function(xhr) {
                // 서버에서 검증 실패 시 (예: 금액 불일치 등)
                alert("결제 정보 검증에 실패했습니다: " + xhr.responseText);
            }
        });
    }

});