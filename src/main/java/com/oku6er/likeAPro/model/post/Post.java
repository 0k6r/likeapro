package com.oku6er.likeAPro.model.post;

import com.oku6er.likeAPro.model.Auditable;
import com.oku6er.likeAPro.model.Comment;
import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.Tag;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"slug"}, callSuper = false)
@Entity(name = "Post")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NaturalIdCache
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Post extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
    private Long id;

    @NotNull(message = "The title must not be null")
    private String title;

    @NotNull(message = "The slug must not be null")
    @Length(max = 50, message = "The slug has a max length with 50 characters")
    @NaturalId
    @Column(unique = true, length = 50)
    private String slug;

    @NotNull(message = "The text must not be null")
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private Text text;

    @Min(value = 0, message = "The vote has a min length with 0")
    private Integer vote = 0;

    @Enumerated
    private Language language = Language.RU;

    @Setter(AccessLevel.NONE)
    @NotEmpty(message = "The post must not be without a tags")
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            foreignKey = @ForeignKey(name = "fk_tag_post")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Comment> comments;

    public void addTag(final Tag tag) {
        Objects.requireNonNull(tag, "The tag must not be null");
        this.tags.add(tag);
    }
}
