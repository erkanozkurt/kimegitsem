package com.persona.kg.dao;

//Generated Jul 22, 2012 7:33:02 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.persona.kg.common.CachedResources;

/**
* TblPoi generated by hbm2java
*/
public class TblPoi implements java.io.Serializable {

	private int poiId;
	private TblDistrict tblDistrict;
	private TblSubdistrict tblSubdistrict;
	private String uniqueIdentifier;
	private String address;
	private Integer cityId;
	private Integer districtId;
	private Integer subdistrictId;
	private BigDecimal coordLat;
	private BigDecimal coordLong;
	private String info;
	private String phone;
	private Date dateAdded;
	private String poiName;
	private String profileImage;
	private String keywords;
	private String website;
	private Integer category;
	
	public String getPoiName() {
		return this.poiName;
	}

	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}

	private TblSubscriber administrator;
	private Set tblComments = new HashSet(0);
	private Set tblPoiCategories = new HashSet(0);
	private Set tblListItemses = new HashSet(0);
	private Set tblAttributeValues = new HashSet(0);
	private List<TblCategory> categories;
	private List<TblComment> comments;
	private List<TblImage> images;
	private String placeName;
	private String cityName;
	private String categoryName;
	private String authorityEmail;
	
	
	private TblImage profilePicture;

	public List<TblImage> getImages() {
		return images;
	}

	public void setImages(List<TblImage> images) {
		this.images = images;
	}

	public List<TblCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<TblCategory> categories) {
		this.categories = categories;
	}

	public List<TblComment> getComments() {
		return comments;
	}

	public void setComments(List<TblComment> comments) {
		this.comments = comments;
	}

	public TblPoi() {
	}

	public TblPoi(int poiId, String uniqueIdentifier) {
		this.poiId = poiId;
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public TblPoi(int poiId, TblDistrict tblDistrict, TblSubdistrict tblSubdistrict, String uniqueIdentifier,
			String address, Integer cityId, Integer districtId, Integer subdistrictId, BigDecimal coordLat, BigDecimal coordLong,
			String info, String phone, Date dateAdded,
			TblPoiAdministrator tblPoiAdministrator, Set tblComments,
			Set tblPoiCategories, Set tblListItemses, Set tblAttributeValues) {
		this.poiId = poiId;
		this.tblDistrict = tblDistrict;
		this.tblSubdistrict = tblSubdistrict;
		this.uniqueIdentifier = uniqueIdentifier;
		this.address = address;
		this.cityId = cityId;
		this.districtId = districtId;
		this.subdistrictId = subdistrictId;
		this.coordLat = coordLat;
		this.coordLong = coordLong;
		this.info = info;
		this.phone = phone;
		this.dateAdded = dateAdded;
		this.tblComments = tblComments;
		this.tblPoiCategories = tblPoiCategories;
		this.tblListItemses = tblListItemses;
		this.tblAttributeValues = tblAttributeValues;
	}

	public int getPoiId() {
		return this.poiId;
	}

	public void setPoiId(int poiId) {
		this.poiId = poiId;
	}

	public TblDistrict getTblDistrict() {
		return this.tblDistrict;
	}

	public void setTblDistrict(TblDistrict tblDistrict) {
		this.tblDistrict = tblDistrict;
	}

	public TblSubdistrict getTblSubdistrict() {
		return tblSubdistrict;
	}

	public void setTblSubdistrict(TblSubdistrict tblSubdistrict) {
		this.tblSubdistrict = tblSubdistrict;
	}

	public String getUniqueIdentifier() {
		return this.uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	/*public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}*/
	
	public void setDistrictId(Integer districtId){
		if(tblDistrict==null){
			tblDistrict=new TblDistrict();
		}
		tblDistrict.setDistrictId(districtId);
	}

	public int getDistrictId(){
		if(tblDistrict==null){
			tblDistrict=new TblDistrict();
		}
		return tblDistrict.getDistrictId();
	}
	
	public void setSubdistrictId(Integer subdistrictId){
		if(tblSubdistrict==null){
			tblSubdistrict=new TblSubdistrict();
		}
		tblSubdistrict.setSubdistrictId(subdistrictId);
	}

	public int getSubdistrictId(){
		if(tblSubdistrict==null){
			tblSubdistrict=new TblSubdistrict();
		}
		return tblSubdistrict.getSubdistrictId();
	}
	

	public BigDecimal getCoordLat() {
		return this.coordLat;
	}

	public void setCoordLat(BigDecimal coordLat) {
		this.coordLat = coordLat;
	}

	public BigDecimal getCoordLong() {
		return this.coordLong;
	}

	public void setCoordLong(BigDecimal coordLong) {
		this.coordLong = coordLong;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Set getTblComments() {
		return this.tblComments;
	}

	public void setTblComments(Set tblComments) {
		this.tblComments = tblComments;
	}

	public Set getTblPoiCategories() {
		return this.tblPoiCategories;
	}

	public void setTblPoiCategories(Set tblPoiCategories) {
		this.tblPoiCategories = tblPoiCategories;
	}

	public Set getTblListItemses() {
		return this.tblListItemses;
	}

	public void setTblListItemses(Set tblListItemses) {
		this.tblListItemses = tblListItemses;
	}

	public Set getTblAttributeValues() {
		return this.tblAttributeValues;
	}

	public void setTblAttributeValues(Set tblAttributeValues) {
		this.tblAttributeValues = tblAttributeValues;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		profilePicture = new TblImage();
		if (profileImage != null) {
			profilePicture.setFilename(profileImage);
		} else {
			profilePicture.setFilename("noprofile.jpg");
		}
		this.profileImage = profileImage;
	}

	public TblImage getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(TblImage profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public TblSubscriber getAdministrator() {
		return administrator;
	}

	public void setAdministrator(TblSubscriber administrator) {
		this.administrator = administrator;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getPlaceId(){
		String placeId="";
		if(tblSubdistrict!=null)
			placeId+=tblSubdistrict.getSubdistrictId()+",";
		if(tblDistrict!=null){
			placeId+=tblDistrict.getDistrictId()+",";
		}
		if(cityId!=null)
			placeId+=cityId;
		
		return placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getAuthorityEmail() {
		return authorityEmail;
	}

	public void setAuthorityEmail(String authorityEmail) {
		this.authorityEmail = authorityEmail;
	}
	

}

/*public class TblPoi implements java.io.Serializable {

	private int poiId;
	private TblDistrict tblDistrict;
	private TblSubdistrict tblSubdistrict;
	private String uniqueIdentifier;
	private String address;
	private Integer cityId;
	private String city;
	private Integer districtId;
	private String district;
	private Integer subdistrictId;
	private String subdistrict;
	private Float coordLat;
	private Float coordLong;
	private String info;
	private String phone;
	private Date dateAdded;
	private String poiName;
	private String profileImage;
	private String keywords;
	private String website;
	private Integer category;
	
	public String getPoiName() {
		return poiName;
	}

	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}

	private TblSubscriber administrator;
	private Set tblComments = new HashSet(0);
	private Set tblPoiCategories = new HashSet(0);
	private Set tblListItemses = new HashSet(0);
	private Set tblAttributeValues = new HashSet(0);
	private List<TblCategory> categories;
	private List<TblComment> comments;
	private List<TblImage> images;

	private TblImage profilePicture;

	public List<TblImage> getImages() {
		return images;
	}

	public void setImages(List<TblImage> images) {
		this.images = images;
	}

	public List<TblCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<TblCategory> categories) {
		this.categories = categories;
	}

	public List<TblComment> getComments() {
		return comments;
	}

	public void setComments(List<TblComment> comments) {
		this.comments = comments;
	}

	public TblPoi() {
	}

	public TblPoi(int poiId, String uniqueIdentifier) {
		this.poiId = poiId;
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public TblPoi(int poiId, TblDistrict tblDistrict, TblSubdistrict tblSubdistrict, String uniqueIdentifier,
			String address, Integer cityId, Integer districtId, String district, Integer subdistrictId, String subdistrict, Float coordLat, Float coordLong,
			String info, String phone, Date dateAdded,
			TblPoiAdministrator tblPoiAdministrator, Set tblComments,
			Set tblPoiCategories, Set tblListItemses, Set tblAttributeValues) {
		this.poiId = poiId;
		this.tblDistrict = tblDistrict;
		this.tblSubdistrict = tblSubdistrict;
		this.uniqueIdentifier = uniqueIdentifier;
		this.address = address;
		this.cityId = cityId;
		this.districtId = districtId;
		this.district = district;
		this.subdistrictId = subdistrictId;
		this.subdistrict = subdistrict;
		this.coordLat = coordLat;
		this.coordLong = coordLong;
		this.info = info;
		this.phone = phone;
		this.dateAdded = dateAdded;
		this.tblComments = tblComments;
		this.tblPoiCategories = tblPoiCategories;
		this.tblListItemses = tblListItemses;
		this.tblAttributeValues = tblAttributeValues;
	}

	public int getPoiId() {
		return this.poiId;
	}

	public void setPoiId(int poiId) {
		this.poiId = poiId;
	}

	public TblDistrict getTblDistrict() {
		return this.tblDistrict;
	}

	public void setTblDistrict(TblDistrict tblDistrict) {
		this.tblDistrict = tblDistrict;
	}

	public String getUniqueIdentifier() {
		return this.uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Float getCoordLat() {
		return this.coordLat;
	}

	public void setCoordLat(Float coordLat) {
		this.coordLat = coordLat;
	}

	public Float getCoordLong() {
		return this.coordLong;
	}

	public void setCoordLong(Float coordLong) {
		this.coordLong = coordLong;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Set getTblComments() {
		return this.tblComments;
	}

	public void setTblComments(Set tblComments) {
		this.tblComments = tblComments;
	}

	public Set getTblPoiCategories() {
		return this.tblPoiCategories;
	}

	public void setTblPoiCategories(Set tblPoiCategories) {
		this.tblPoiCategories = tblPoiCategories;
	}

	public Set getTblListItemses() {
		return this.tblListItemses;
	}

	public void setTblListItemses(Set tblListItemses) {
		this.tblListItemses = tblListItemses;
	}

	public Set getTblAttributeValues() {
		return this.tblAttributeValues;
	}

	public void setTblAttributeValues(Set tblAttributeValues) {
		this.tblAttributeValues = tblAttributeValues;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		profilePicture = new TblImage();
		if (profileImage != null) {
			profilePicture.setFilename(profileImage);
		} else {
			profilePicture.setFilename("noprofile.jpg");
		}
		this.profileImage = profileImage;
	}

	public TblImage getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(TblImage profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public TblSubscriber getAdministrator() {
		return administrator;
	}

	public void setAdministrator(TblSubscriber administrator) {
		this.administrator = administrator;
	}
	public void setDistrictId(Integer districtId){
		if(tblDistrict==null){
			tblDistrict=new TblDistrict();
		}
		tblDistrict.setDistrictId(districtId);
	}
	
	public int getDistrictId(){
		if(tblDistrict==null){
			tblDistrict=new TblDistrict();
		}
		return tblDistrict.getDistrictId();
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Integer getSubdistrictId() {
		return subdistrictId;
	}

	public void setSubdistrictId(Integer subdistrictId) {
		this.subdistrictId = subdistrictId;
	}

	public String getSubdistrict() {
		return subdistrict;
	}

	public void setSubdistrict(String subdistrict) {
		this.subdistrict = subdistrict;
	}

}*/
