package mezzari.torres.lucas.dietbox_challenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public class ListWrapper<T> {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<T> results;

    public int getPage() {
        return page;
    }

    public List<T> getResults() {
        return results;
    }
}
