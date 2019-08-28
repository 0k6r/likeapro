package com.oku6er.likeAPro.model;

import com.oku6er.likeAPro.model.post.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Comment")
public class Comment extends Auditable<String>{

    @Id
    @GeneratedValue
    @NonNull private Long id;

    @NonNull private String author;

    @CreatedDate
    private LocalDateTime createDate;

    private Long parentId;

    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_comment_post"))
    private Post post;
}
