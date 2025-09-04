// '주소 찾기' 버튼이 클릭되었을 때의 동작을 정의합니다.
document.addEventListener('DOMContentLoaded', function() {
    const findAddressButton = document.getElementById("btn-find-address");

    if (findAddressButton) {
        findAddressButton.addEventListener("click", function() {
            // Daum 우편번호 서비스 API를 호출합니다.
            new daum.Postcode({
                // 사용자가 주소를 선택했을 때 실행될 함수입니다.
                oncomplete: function(data) {
                    // 팝업에서 검색된 결과 항목을 클릭했을 때 실행할 코드를 작성하는 부분.

                    // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var roadAddr = data.roadAddress; // 도로명 주소 변수

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById('postcode').value = data.zonecode; // 5자리 우편번호
                    document.getElementById("address").value = roadAddr;

                    // 주소 정보를 선택하면 상세주소 필드로 커서를 이동시킨다.
                    document.getElementById("addressDetail").focus();
                }
            }).open();
        });
    }
});