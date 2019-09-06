package com.oku6er.likeAPro.model;

import com.oku6er.likeAPro.model.post.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"title"})
@Entity(name = "Tag")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NaturalIdCache
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag_id_seq")
    private Long id;

    @NotNull(message = "Title must not be null")
    @NaturalId
    @Column(unique = true)
    private String title;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    public Tag(String title) {
        this.title = title;
    }
}
