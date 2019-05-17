package org.tat.sdksample.placesearch.model

data class Detail(var placeName: String, var placeAddress: String, var placeDetail: String, var placeTel: String, var placeWebsite: String, var placeFacilities: String, var placeServices: String, var placePaymentOptions: String) {
    companion object {

        fun sortAddress(address: String?, subDistrict: String?, district: String?, province: String?): String {
            var sortedAddress = ""

            if (address != null && !address.isEmpty()) {
                sortedAddress = address
            }
            if (subDistrict != null && !subDistrict.isEmpty()) {
                sortedAddress = "$sortedAddress $subDistrict"
            }
            if (district != null && !district.isEmpty()) {
                sortedAddress = "$sortedAddress $district"
            }
            if (province != null && !province.isEmpty()) {
                sortedAddress = "$sortedAddress $province"
            }
            return sortedAddress.trim { it <= ' ' }
        }
    }
}
