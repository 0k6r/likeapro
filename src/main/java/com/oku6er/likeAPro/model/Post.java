package com.oku6er.likeAPro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String text;
    private String tags;
    private String comments;
//    private List<String> tags;
//    private List<String> comments;
    private LocalDateTime createDate;
}
