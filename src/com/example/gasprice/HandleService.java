package com.example.gasprice;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapPrimitive;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;


public class HandleService {

	private String data;
	private String gas;
	private String value;
	
	private String Gasoline95;
	private String Gasoline91;
	private String Diesel;
	private String Gasohol91;
	private String GasoholE20;
	private String NGV;
	private String Gasohol95;
	private String GasoholE85;
	private String HyForcePremiumDiesel; 
	
	private static final String dataAccess = "DataAccess";
	private static final String priceDate = "PRICE_DATE";
	private static final String product = "PRODUCT";
	private static final String price = "PRICE";
	
	 boolean Data_item_tag = false;
	 String currentTag = "";
	 data temp_data = null;
	 List<data> temp_dataList = new ArrayList<data>();
	
	
	 protected void pareser(SoapPrimitive Results) throws XmlPullParserException, IOException {

			data = Results.toString();
			
		    try{
					
				    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				    XmlPullParser parser = factory.newPullParser();
				    parser.setInput(new StringReader(data));
				    
				    int event = parser.getEventType();
				    
				    Log.d("Log","Parser ptt service");
				    //Log.d("Log",data);
				    
				    while (event != XmlPullParser.END_DOCUMENT) {
				    	 
				        switch (event) {
				            case XmlPullParser.START_TAG:
				                currentTag = parser.getName();
				                if (currentTag.equals(dataAccess)) {
				                    Data_item_tag = true;
				                    temp_data = new data();
				                    temp_dataList.add(temp_data);
				                   
				                }
				                break;

				            case XmlPullParser.END_TAG:
				                if (parser.getName().equals(dataAccess)) {
				                    Data_item_tag = false;
				                    
				                }
				                currentTag = "";
				                break;

				            case XmlPullParser.TEXT:
				                if (Data_item_tag && temp_data != null) {
				                    switch (currentTag) {
				                     	
				                        case priceDate:
				                        	temp_data.setDate(parser.getText());
				                        	Log.d("",parser.getText());
				                        	break;
				                        case product:
				                            temp_data.setProduct(parser.getText());
				                            Log.d("",parser.getText());
				                            break;
				                        case price:
				                            temp_data.setPrice(parser.getText());
				                            Log.d("",parser.getText());
				                        default:
				                            break;
				                    }
				                    
				                }
				                break;
				        }

				       event = parser.next();

				    }
				    
				   
				    getAll();
				    
				} catch (Exception e) {
					e.printStackTrace();
					Log.d("Log","Parser ptt service Fail");
				}
	}
	
	public void getAll(){
	
		for(data item : temp_dataList)
		{   
		      
		      gas = item.getProduct();
		      value = item.getPrice();
		      
		      Log.d("Log",gas+ " "+value);
		      
		      if(value == "" || value == null){
		    	  value = "0.00";
		      }
		      
		      
		      if(gas.equals("Blue Gasoline 95")){
		    	  setGasoline95(value);
		      }else if(gas.equals("Blue Gasoline 91")){
		    	  setGasoline91(value);
		      }else if(gas.equals("Blue Diesel")){
		    	  setDiesel(value);
		      }else if(gas.equals("Blue Gasohol 91")){
		    	  setGasohol91(value);
		      }else if(gas.equals("Blue Gasohol E20")){
		    	  setGasoholE20(value);
		      }else if(gas.equals("NGV")){
		    	  setNGV(value);
		      }else if(gas.equals("Blue Gasohol 95")){
		    	  setGasohol95(value);
		      }else if(gas.equals("Blue Gasohol E85")){
		    	  setGasoholE85(value);
		      }else if(gas.equals("HyForce Premium Diesel")){
		    	  setHyForcePremiumDiesel(value);
		      }
		    	  
		}
	}
	
	public String getGasoline95() {
		return Gasoline95;
	}

	public void setGasoline95(String gasoline95) {
		this.Gasoline95 = gasoline95;
	}

	public String getGasoline91() {
		return Gasoline91;
	}

	public void setGasoline91(String gasoline91) {
		this.Gasoline91 = gasoline91;
	}

	public String getDiesel() {
		return Diesel;
	}

	public void setDiesel(String diesel) {
		this.Diesel = diesel;
	}

	public String getGasohol91() {
		return Gasohol91;
	}

	public void setGasohol91(String gasohol91) {
		this.Gasohol91 = gasohol91;
	}

	public String getGasoholE20() {
		return GasoholE20;
	}

	public void setGasoholE20(String gasoholE20) {
		this.GasoholE20 = gasoholE20;
	}

	public String getNGV() {
		return NGV;
	}

	public void setNGV(String nGV) {
		this.NGV = nGV;
	}

	public String getGasohol95() {
		return Gasohol95;
	}

	public void setGasohol95(String gasohol95) {
		this.Gasohol95 = gasohol95;
	}

	public String getGasoholE85() {
		return GasoholE85;
	}

	public void setGasoholE85(String gasoholE85) {
		this.GasoholE85 = gasoholE85;
	}

	public String getHyForcePremiumDiesel() {
		return HyForcePremiumDiesel;
	}

	public void setHyForcePremiumDiesel(String hyForcePremiumDiesel) {
		this.HyForcePremiumDiesel = hyForcePremiumDiesel;
	}

}
