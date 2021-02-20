$(document).ready(function(){
        meetCommon = new meetCommon();

        $('.datepicker').datepicker({
            format: 'yyyy-mm-dd'
        });
        $('.timepicker').timepicker({
            twelveHour: false
        });

        $('.slide-modal').modal({
            onOpenEnd: function(e){
                let modalId = $(this).attr('id');
                //let functionName = $('#'+modalId).attr('data-function-name');
                if(location.href.split('#').pop() != modalId) {
                    window.history.pushState({}, '', location.href + '#' + $(this).attr('id'));
                }
                modalStack.push(modalId);
                //window[functionName]();
                meetCommon.setCollectionHeight();
            },
            onCloseStart: function(e){
                if(location.href.split('#').pop() == $(this).attr('id')) {
                    window.history.back();
                }
            },
            onCloseEnd: function(e) {
                let memberChipLength = $('#member-chips .chip').length;
                if(memberChipLength == 0) {
                    $('[href=#memberModal]').text('선택');
                    $('[href=#memberModal]').css('color', '#9e9e9e');
                } else {
                    if(memberChipLength == 1) {
                        $('[href=#memberModal]').text($('#member-chips .chip').eq(0).attr('data-nameAccount'));
                    } else {
                        $('[href=#memberModal]').text($('#member-chips .chip').eq(0).attr('data-nameAccount') + ' 외 ' + (memberChipLength-1) + '명');
                    }
                    $('[href=#memberModal]').css('color', '#ffffff');
                }

                $('#memberInput').empty();
                $('#member-chips [name="idAccount"]').clone().appendTo($('#memberInput'));

                modalStack.pop();
            }
        });

        // 참여자 모달 검색
        $('[name="memberSearchForm"]').on('submit', function(e) {
            e.preventDefault();
            // todo: e메일패턴 인식하여 분기
            searchPage = 1;
            meetCommon.searchMember(searchPage);
            $('#memberSearchForm .searchKeyword').val('');
        });

        // 모임 참여자 더보기 이벤트
        $('#memberModal .collection').on('scroll', function(e) {
            let scrollHeight = $(this).prop('scrollHeight');
            let ulHeight = $(this).height();
            let scrollTop = Number($(this).scrollTop());
            if(scrollTop >= (scrollHeight - ulHeight - 0.5) && hasNextPage) {
                console.info('더보기 호출');
                meetCommon.searchMember(searchPage+1);
            }
        });

        // 참여자 추가
        $(document).on('click', '#memberModal .collection-item', function(e){
            let idAccount = $(this).attr('data-idAccount');
            let nameAccount = $(this).attr('data-nameAccount');
            if($('.chip[data-idAccount="'+ idAccount +'"]').length > 0) {
                // 중복알림
                $('.chip[data-idAccount="'+ idAccount +'"]').toggleClass('pulse');
                setTimeout(function(){
                    $('.chip[data-idAccount="'+ idAccount +'"]').toggleClass('pulse');
                }, 500);
            } else {
                $('#memberSearchForm .searchKeyword').blur(); // 가상 키보드 닫기
                setTimeout(function(){ // 가상 키보드 닫히는 시간 상정하여 지연
                    let memberChipStr = '';
                    memberChipStr += '<div class="chip" tabindex="0" style="float:left; z-index: 1;" data-idAccount="'+ idAccount +'" data-nameAccount="'+ nameAccount +'">';
                    memberChipStr += nameAccount;
                    memberChipStr += '<i class="material-icons close">close</i>';
                    memberChipStr += '<input type="hidden" name="idAccount" value="'+ idAccount +'"/>';
                    memberChipStr += '</div>';

                    let memberChipLength = $('#member-chips .chip').length;
                    if(memberChipLength == 0) {
                        $('#member-chips').prepend(memberChipStr);
                    } else {
                        $('#member-chips .chip').last().after(memberChipStr);
                    }
                }, 200);
            }
        });

        // 검색영역 비어있는 곳 클릭했을 때 input 에 포커싱
        $('#memberSearchForm').on('click', function(e){
            $('#memberSearchForm .searchKeyword').focus();
        });


        // 멤버태그 변경 감지
        let memberChipObserver = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation){
                meetCommon.setCollectionHeight(); // 높이조절
            });
        });

        // 옵저버 바인딩
        let target = document.getElementById('member-chips');
        memberChipObserver.observe(target, {childList: true});
});


let searchPage;
let hasNextPage = false;
function meetCommon() {

    // 모임 참여자 검색 이벤트
    this.searchMember = function(searchPage) {
        comn.preLoading();
        $.ajax({
            url: '/account/list.do',
            type: 'get',
            data: {
                searchType: 'nameAccount',
                searchKeyword: $('#memberModal .searchKeyword').val(),
                page: searchPage,
                listSize: 10,
                sortProp: 'nameAccount',
                direction: 'ASC',
                ynDel: null
            },
            success: function(data) {
                //console.info(data);
                if(searchPage == 1) {
                    $('#memberModal .collection').empty();
                    $('#memberModal .collection').scrollTop(0);
                }
                let listStr = '';
                data.dtoList.forEach(function(item, index){
                    listStr += '<li class="collection-item avatar" data-idAccount="'+ item.idAccount +'" data-nameAccount="'+ item.nameAccount +'">';
                        listStr += '<i class="material-icons circle">person</i>';
                        listStr += '<p>'+ item.nameAccount +'</p>';
                    listStr += '</li>';
                });
                $('#memberModal .collection').append(listStr);
                hasNextPage = data.hasNext;
            },
            error: function(errorThrown) {
                alert(errorThrown.statusText);
            },
            complete: function() {
                comn.afterLoading();
            }
        });
    }// 참여자검색 끝

    // 선택한 멤버리스트 조회
    this.getIdMemberList = function() {
        return $('#member-chips .chip').map(function(index, item){
            return $(item).attr('data-idAccount');
        }).get();
    }

    // 검색결과 영역 높이조절
    this.setCollectionHeight = function() {
        let contentHeight = $('#memberModal .modal-content').height();
        let firstChildHeight = $('#memberModal .modal-content').children().eq(0).outerHeight();
        let secondChildHeight = $('#memberModal .modal-content').children().eq(1).outerHeight();
        let thirdChildHeight = $('#memberModal .modal-content').children().eq(2).outerHeight();
        let thirdChildMarginTop = Number($('#memberModal .modal-content').children().eq(2).css('marginTop').replace('px',''));
        let thirdChildMarginBottom = Number($('#memberModal .modal-content').children().eq(2).css('marginBottom').replace('px',''));
        let collectionHeight = contentHeight - firstChildHeight - secondChildHeight - thirdChildHeight - thirdChildMarginTop - thirdChildMarginBottom;

        $('#memberModal .collection').height(collectionHeight);
    }
}


