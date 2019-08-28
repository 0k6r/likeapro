package com.oku6er.likeAPro.model.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Text implements Serializable {
    private String subheader;
    private List<Paragraph> paragraphs;
    private String link;
}
