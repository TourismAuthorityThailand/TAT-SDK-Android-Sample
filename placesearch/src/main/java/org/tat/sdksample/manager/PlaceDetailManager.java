package org.tat.sdksample.manager;

import org.tat.sdksample.inteface.DetailLoadingListener;
import org.tat.sdksample.model.Detail;
import org.th.tatsdk.ServiceRequestListener;
import org.th.tatsdk.common.TATItemCode;
import org.th.tatsdk.common.TATLanguage;
import org.th.tatsdk.common.TATLocation;
import org.th.tatsdk.common.TATPlaceContact;
import org.th.tatsdk.search.TATGetPlaceDetailParameter;
import org.th.tatsdk.search.accommodation.TATGetAccommodationDetail;
import org.th.tatsdk.search.accommodation.TATGetAccommodationDetailResult;
import org.th.tatsdk.search.attraction.TATGetAttractionDetail;
import org.th.tatsdk.search.attraction.TATGetAttractionDetailResult;
import org.th.tatsdk.search.other.TATGetOtherPlaceDetail;
import org.th.tatsdk.search.other.TATGetOtherPlaceDetailResult;
import org.th.tatsdk.search.restaurant.TATGetRestaurantDetail;
import org.th.tatsdk.search.restaurant.TATGetRestaurantDetailResult;
import org.th.tatsdk.search.shop.TATGetShopDetail;
import org.th.tatsdk.search.shop.TATGetShopDetailResult;

public class PlaceDetailManager {

    DetailLoadingListener listener = null;

    public PlaceDetailManager(DetailLoadingListener listener) {
        this.listener = listener;
    }

    /**
     * @loadPlaceDetail request for detail of place using place id for each category.
     * For this example will map only used value.
     */
    public void loadPlaceDetail(String placeId, String placeCategory) {

        TATGetPlaceDetailParameter param = new TATGetPlaceDetailParameter(placeId, TATLanguage.ENGLISH);

        switch (placeCategory) {
            case "Attraction":
                TATGetAttractionDetail.executeAsync(param, new ServiceRequestListener<TATGetAttractionDetailResult>() {
                    @Override
                    public void onResponse(TATGetAttractionDetailResult result) {
                        TATLocation location = result.getLocation();
                        Detail detail = new Detail(result.getName(),
                                Detail.sortAddress(location.getAddress(), location.getSubDistrict(), location.getDistrict(), location.getProvince()),
                                result.getInfo().getDetail(),
                                checkTelephone(result.getContact()),
                                checkUrl(result.getContact()),
                                sortList(result.getFacilities()),
                                sortList(result.getServices()),
                                sortList(result.getPayments())
                        );
                        if (listener != null) {
                            listener.onSucceed(detail);
                        }
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        if (listener != null) {
                            listener.onError(message, errorCode);
                        }
                    }
                });
                break;
            case "Accommodation":
                TATGetAccommodationDetail.executeAsync(param, new ServiceRequestListener<TATGetAccommodationDetailResult>() {
                    @Override
                    public void onResponse(TATGetAccommodationDetailResult result) {
                        TATLocation location = result.getLocation();
                        Detail detail = new Detail(result.getName(),
                                Detail.sortAddress(location.getAddress(), location.getSubDistrict(), location.getDistrict(), location.getProvince()),
                                result.getInfo().getDetail(),
                                checkTelephone(result.getContact()),
                                checkUrl(result.getContact()),
                                sortList(result.getFacilities()),
                                sortList(result.getServices()),
                                sortList(result.getPayments())
                        );
                        if (listener != null) {
                            listener.onSucceed(detail);
                        }
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        if (listener != null) {
                            listener.onError(message, errorCode);
                        }
                    }
                });
                break;
            case "Restaurant":
                TATGetRestaurantDetail.executeAsync(param, new ServiceRequestListener<TATGetRestaurantDetailResult>() {
                    @Override
                    public void onResponse(TATGetRestaurantDetailResult result) {
                        TATLocation location = result.getLocation();
                        Detail detail = new Detail(result.getName(),
                                Detail.sortAddress(location.getAddress(), location.getSubDistrict(), location.getDistrict(), location.getProvince()),
                                result.getInfo().getDetail(),
                                checkTelephone(result.getContact()),
                                checkUrl(result.getContact()),
                                sortList(result.getFacilities()),
                                sortList(result.getServices()),
                                sortList(result.getPayments())
                        );
                        if (listener != null) {
                            listener.onSucceed(detail);
                        }
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        if (listener != null) {
                            listener.onError(message, errorCode);
                        }
                    }
                });
                break;
            case "Shop":
                TATGetShopDetail.executeAsync(param, new ServiceRequestListener<TATGetShopDetailResult>() {
                    @Override
                    public void onResponse(TATGetShopDetailResult result) {
                        TATLocation location = result.getLocation();
                        Detail detail = new Detail(result.getName(),
                                Detail.sortAddress(location.getAddress(), location.getSubDistrict(), location.getDistrict(), location.getProvince()),
                                result.getInfo().getDetail(),
                                checkTelephone(result.getContact()),
                                checkUrl(result.getContact()),
                                sortList(result.getFacilities()),
                                sortList(result.getServices()),
                                sortList(result.getPayments())
                        );
                        if (listener != null) {
                            listener.onSucceed(detail);
                        }
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        if (listener != null) {
                            listener.onError(message, errorCode);
                        }
                    }
                });
                break;
            case "Other":
                TATGetOtherPlaceDetail.executeAsync(param, new ServiceRequestListener<TATGetOtherPlaceDetailResult>() {
                    @Override
                    public void onResponse(TATGetOtherPlaceDetailResult result) {
                        TATLocation location = result.getLocation();
                        Detail detail = new Detail(result.getName(),
                                Detail.sortAddress(location.getAddress(), location.getSubDistrict(), location.getDistrict(), location.getProvince()),
                                result.getInfo().getDetail(),
                                checkTelephone(result.getContact()),
                                checkUrl(result.getContact()),
                                "",
                                "",
                                ""
                        );
                        if (listener != null) {
                            listener.onSucceed(detail);
                        }
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        if (listener != null) {
                            listener.onError(message, errorCode);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    private String checkTelephone(TATPlaceContact contact) {
        if (contact != null) {
            if (contact.getPhones() != null) {
                if (contact.getPhones().length > 0) {
                    return contact.getPhones()[0];
                } else return "";
            } else return "";
        } else return "";
    }

    private String checkUrl(TATPlaceContact contact) {
        if (contact != null) {
            if (contact.getUrls() != null) {
                if (contact.getUrls().length > 0) {
                    return contact.getUrls()[0];
                } else return "";
            } else return "";
        } else return "";
    }

    private String sortList(TATItemCode[] list) {
        if (list != null) {
            if (list.length > 0) {
                int index = 0;
                String nameList = "";
                for (TATItemCode item : list) {
                    if (index != 0) {
                        nameList = nameList + "," + item.getDesc();
                        index++;
                    } else {
                        nameList = item.getDesc();
                        index++;
                    }

                }
                return nameList;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
}
