package kr.co.won.repository.querydsl;


import kr.co.won.domain.HashTagDomain;
import kr.co.won.domain.QHashTagDomain;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class HashtagRepositoryCustomImpl extends QuerydslRepositorySupport implements HashtagRepositoryCustom {

    public HashtagRepositoryCustomImpl() {
        super(HashTagDomain.class);
    }

    @Override
    public List<String> findAllHashtagNames() {
        QHashTagDomain hashtag = QHashTagDomain.hashTagDomain;

        return from(hashtag)
                .select(hashtag.hashTagName)
                .fetch();
    }

}
