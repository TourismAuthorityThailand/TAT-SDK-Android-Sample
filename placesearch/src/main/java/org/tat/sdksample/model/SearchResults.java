package org.tat.sdksample.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SearchResults implements Parcelable {

    public List<SearchResult> searchResults;

    public SearchResults(List<SearchResult> searchResults){
        this.searchResults = searchResults;
    }


    protected SearchResults(Parcel in) {
        searchResults = in.createTypedArrayList(SearchResult.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(searchResults);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchResults> CREATOR = new Creator<SearchResults>() {
        @Override
        public SearchResults createFromParcel(Parcel in) {
            return new SearchResults(in);
        }

        @Override
        public SearchResults[] newArray(int size) {
            return new SearchResults[size];
        }
    };
}
