package kr.co.won.service;

import kr.co.won.domain.HashTagDomain;
import kr.co.won.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public Set<String> parseHashtagNames(String content) {
        if (content == null) {
            return Set.of();
        }
        Pattern pattern = Pattern.compile("#[\\w가-힣]+");
        Matcher matcher = pattern.matcher(content.toString());
        Set<String> result = new HashSet<>();
        while (matcher.find()) {
            // 일치하는 값을 group 으로 받을 수 있다.
            result.add(matcher.group().replace("#", ""));
        }
        // 불변 객체를 반환하기 위해서 복사하여 반환을 해준다.
        return Set.copyOf(result);
    }

    public Set<HashTagDomain> findHashtagsByNames(Set<String> hashtagNames) {
        return new HashSet<>(hashtagRepository.findByHashTagNameIn(hashtagNames));
    }

    public void deleteHashtagWithoutArticles(Long hashtagId) {
        HashTagDomain referenceById = hashtagRepository.getReferenceById(hashtagId);
        if (referenceById.getArticles().isEmpty()) {
            hashtagRepository.delete(referenceById);
        }
    }
}
