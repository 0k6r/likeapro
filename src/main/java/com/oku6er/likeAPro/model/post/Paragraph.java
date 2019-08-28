package com.oku6er.likeAPro.model.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paragraph implements Serializable {
    private int paragraphNumber;
    private String text;
}
