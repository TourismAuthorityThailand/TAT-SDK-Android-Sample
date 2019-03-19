package org.tat.sdksample.placesearch.model;

public class Detail {
    String placeName;
    String placeAddress;
    String placeDetail;
    String placeTel;
    String placeWebsite;
    String placeFacilities;
    String placeServices;
    String placePaymentOptions;

    public Detail(String placeName, String placeAddress, String placeDetail, String placeTel, String placeWebsite, String placeFacilities, String placeServices, String placePaymentOptions) {
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeDetail = placeDetail;
        this.placeTel = placeTel;
        this.placeWebsite = placeWebsite;
        this.placeFacilities = placeFacilities;
        this.placeServices = placeServices;
        this.placePaymentOptions = placePaymentOptions;
    }

    public static String sortAddress(String address, String subDistrict, String district, String province) {
        String sortedAddress = "";

        if (address != null && !address.isEmpty()) {
            sortedAddress = address;
        }
        if (subDistrict != null && !subDistrict.isEmpty()) {
            sortedAddress = sortedAddress + " " + subDistrict;
        }
        if (district != null && !district.isEmpty()) {
            sortedAddress = sortedAddress + " " + district;
        }
        if (province != null && !province.isEmpty()) {
            sortedAddress = sortedAddress + " " + province;
        }
        return sortedAddress.trim();
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceDetail() {
        return placeDetail;
    }

    public void setPlaceDetail(String placeDetail) {
        this.placeDetail = placeDetail;
    }

    public String getPlaceTel() {
        return placeTel;
    }

    public void setPlaceTel(String placeTel) {
        this.placeTel = placeTel;
    }

    public String getPlaceWebsite() {
        return placeWebsite;
    }

    public void setPlaceWebsite(String placeWebsite) {
        this.placeWebsite = placeWebsite;
    }

    public String getPlaceFacilities() {
        return placeFacilities;
    }

    public void setPlaceFacilities(String placeFacilities) {
        this.placeFacilities = placeFacilities;
    }

    public String getPlaceServices() {
        return placeServices;
    }

    public void setPlaceServices(String placeServices) {
        this.placeServices = placeServices;
    }

    public String getPlacePaymentOptions() {
        return placePaymentOptions;
    }

    public void setPlacePaymentOptions(String placePaymentOptions) {
        this.placePaymentOptions = placePaymentOptions;
    }
}
