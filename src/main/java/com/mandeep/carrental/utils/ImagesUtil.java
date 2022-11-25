package com.mandeep.carrental.utils;

import org.springframework.stereotype.Component;

@Component
public class ImagesUtil {

	public String getImageUrl(String model) {
		switch (model) {
		case Constants.VehicleModel.BMW_650:
			return "https://imgd.aeplcdn.com/0x0/n/cw/ec/56433/exterior-right-front-three-quarter.jpeg";
		case Constants.VehicleModel.Toyota_Camry:
			return "https://cdni.autocarindia.com/Utils/ImageResizer.ashx?n=https://cms.haymarketindia.net/model/uploads/modelimages/CamryModelImage.jpg&w=730&h=484&q=75&c=1";
		default:
			return "";
		}
	}
	
	
}
