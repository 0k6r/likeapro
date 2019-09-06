package com.oku6er.likeAPro.model;

import com.oku6er.likeAPro.model.post.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Comment")
public class Comment extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    private Long id;

    @NonNull
    private String author;

    private Long parentId;

    @NotNull
    private Integer rating;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_comment_post"))
    private Post post;
}
