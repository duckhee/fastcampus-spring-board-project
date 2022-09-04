package kr.co.won.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PaginationService {

    // 최대로 보여줄 페이지네이션 수 default 값
    private static final int BAR_LENGTH = 5;

    // 현재 pagination 의 값을 얻어오기 위한 설정
    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        // start number
        int starNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0); // 중간 값을 찾아오기 위한 계산 0보다 큰 값을 사용을 하겠다는 의미이다.
        int endNumber = Math.min(starNumber + BAR_LENGTH, totalPages); // 두 값을 비교해서 작은 값을 사용하겠다는 의미이다.


        return IntStream.range(starNumber, endNumber).boxed().toList(); // primitive 변수를 List 에 담을 수 있는형태로 변환을 하기 위해서 boxed() 를 사용해서 변환을 한다.
    }

    public int getBarLength() {
        return BAR_LENGTH;
    }

}
