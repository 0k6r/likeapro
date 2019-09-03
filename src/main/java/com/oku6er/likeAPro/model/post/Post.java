package com.oku6er.likeAPro.model.post;

import com.oku6er.likeAPro.model.Auditable;
import com.oku6er.likeAPro.model.Comment;
import com.oku6er.likeAPro.model.Language;
import com.oku6er.likeAPro.model.Tag;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"slug"}, callSuper = false)
@Entity(name = "Post")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NaturalIdCache
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Post extends Auditable<String> {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String slug;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private List<Text> texts;

    @Column(nullable = false)
    private Integer vote;

    @Enumerated
    @Column(nullable = false)
    private Language language;

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

    @CreatedDate
    private LocalDateTime createDate;

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
    }

    public void addTag(final Tag tag) {
        this.tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(final Tag tag) {
        this.tags.remove(tag);
        tag.getPosts().remove(this);
    }
}
