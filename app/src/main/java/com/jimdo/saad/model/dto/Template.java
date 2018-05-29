package com.jimdo.saad.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Saad Aftab on 26/05/2018.
 */
public class Template implements Serializable {

    private String name;
    private String image;
    private int color;
    private List<Template> templates;

    public Template(String name, String image, int color) {
        this.name = name;
        this.image = image;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getColor() {
        return color;
    }

    public List<Template> getVariations() {
        return templates;
    }

    public void setVariations(List<Template> variations) {
        this.templates = variations;
    }

}
