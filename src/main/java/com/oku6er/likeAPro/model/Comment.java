package com.oku6er.likeAPro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oku6er.likeAPro.model.post.Post;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @NotNull
    private String text;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_comment_post"))
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;
}
