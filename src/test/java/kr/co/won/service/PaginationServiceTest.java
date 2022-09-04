package kr.co.won.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName(value = "비즈니스 로직 - 페이지네이션")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {
        PaginationService.class
})
class PaginationServiceTest {

    private final PaginationService paginationService;

    PaginationServiceTest(@Autowired PaginationService paginationService) {
        this.paginationService = paginationService;
    }

    @DisplayName(value = "현재 페이지 번호와 전체 페이지 수를 넣어주면, 페이지 바 리스트를 만들어준다.")
    @MethodSource(value = "dumpyPagination")
    @ParameterizedTest(name = "[{index}] - ({0}, {1}) => {2} pagination Loop ServiceTest") // 테스트 이름을 조금 더 보기 좋게 하기 위해서 display 에 대한 설정을 해주는 것
        // 값을 여러번 주입해서 동일한 메소드를 여러번 테스트 진행이 가능하게 해주는 annotation 이다.
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnPaginationBarNumber(int currentPageNumber, int totalPages, List<Integer> expected) {

        // given


        // when
        List<Integer> actual = paginationService.getPaginationBarNumbers(currentPageNumber, totalPages);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    // 입력 값을 변경을 하면서 테스트를 하기 위해서 methodSource 에 넣어주는 파라미터의 값을 만들어주는 함수 이다.
    static Stream<Arguments> dumpyPagination() {
        return Stream.of(
                arguments(0, 13, List.of(0, 1, 2, 3, 4)),
                arguments(1, 13, List.of(0, 1, 2, 3, 4)),
                arguments(2, 13, List.of(0, 1, 2, 3, 4)),
                arguments(3, 13, List.of(1, 2, 3, 4, 5)),
                arguments(4, 13, List.of(2, 3, 4, 5, 6)),
                arguments(5, 13, List.of(3, 4, 5, 6, 7)),
                arguments(10, 13, List.of(8, 9, 10, 11, 12)),
                arguments(11, 13, List.of(9, 10, 11, 12)),
                arguments(12, 13, List.of(10, 11, 12))
        );
    }

    @DisplayName(value = "현재 설정되어 있는 페이지네이 바의 길이를 알려준다.")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        // given

        // when
        int barLength = paginationService.getBarLength();
        // then
        assertThat(barLength).isEqualTo(5);
    }
}