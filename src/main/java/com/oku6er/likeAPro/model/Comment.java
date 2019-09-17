package com.oku6er.likeAPro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oku6er.likeAPro.model.post.Post;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Comment")
public class Comment extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq")
    private Long id;

    @NonNull
    @NotNull(message = "Author must not be null")
    private String author;

    private Long parentId;

    @NotNull(message = "Rating must not be null")
    private Integer rating;

    @NotNull(message = "Text must not be null")
    private String text;

    @NotNull(message = "Post must not be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_comment_post"))
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;
}
