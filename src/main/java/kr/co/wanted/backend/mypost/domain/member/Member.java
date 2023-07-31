package kr.co.wanted.backend.mypost.domain.member;

import kr.co.wanted.backend.mypost.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<MemberAuthority> memberAuthorities = new ArrayList<>();

    private Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private Member(String email, String password, MemberAuthority memberAuthority) {
        this(email, password);
        this.memberAuthorities.add(memberAuthority);
    }


    private void addAuthority(MemberAuthority authority) {
        memberAuthorities.add(authority);
        authority.setMember(this);
    }

    public static Member createMember(String email, String password, MemberAuthority memberAuthority) {
        Member member = new Member(email, password, memberAuthority);
        member.addAuthority(memberAuthority);

        return member;
    }

    public static Member createMember(String email, String password) {
        return new Member(email, password);
    }
}
