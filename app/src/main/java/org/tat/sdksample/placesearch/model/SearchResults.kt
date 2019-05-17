package org.tat.sdksample.placesearch.model

import android.os.Parcel
import android.os.Parcelable

data class SearchResults(var searchResults: List<SearchResult>? = null) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(SearchResult)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(searchResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchResults> {
        override fun createFromParcel(parcel: Parcel): SearchResults {
            return SearchResults(parcel)
        }

        override fun newArray(size: Int): Array<SearchResults?> {
            return arrayOfNulls(size)
        }
    }


}
