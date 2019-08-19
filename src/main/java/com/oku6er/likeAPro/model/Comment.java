package com.oku6er.likeAPro.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue
    @NonNull private Long id;

    @NonNull private String author;

    @CreatedDate
    private LocalDateTime createDate;

    private Long parentId;

    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
