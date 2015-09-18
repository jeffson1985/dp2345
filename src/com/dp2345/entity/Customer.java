/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.entity;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.dp2345.interceptor.CustomerInterceptor;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.dp2345.entity.CustomerAttribute.Type;
import com.dp2345.util.JsonUtils;

/**
 * Entity - 商家
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Entity
@Table(name = "xx_customer")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_customer_sequence")
public class Customer extends BaseEntity {

	private static final long serialVersionUID = 3083177352995559548L;

	/** "身份信息"参数名称 */
	public static final String PRINCIPAL_ATTRIBUTE_NAME = CustomerInterceptor.class.getName() + ".PRINCIPAL";

	/** "店铺用户名"Cookie名称 */
	public static final String USERNAME_COOKIE_NAME = "customerusername";

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 商家名称*/
	private String name;
	
	/**  商家注册项值属性个数 */
	public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 10;
	
	/** 商家注册项值属性名称前缀 */
	public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

	/** 积分 */
	private Long point;

	/** 卖出金额 */
	private BigDecimal amount;

	/** 是否启用 */
	private Boolean isEnabled;

	/** 是否锁定 */
	private Boolean isLocked;

	/** 锁定日期 */
	private Date lockedDate;

	/** 创建日期 */
	private Date birth;
	
	/** E-mail */
	private String email;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String phone;

	/** 手机 */
	private String mobile;

	/** 商家注册项值0 */
	private String attributeValue0;

	/** 商家注册项值1 */
	private String attributeValue1;

	/** 商家注册项值2 */
	private String attributeValue2;

	/** 商家注册项值3 */
	private String attributeValue3;

	/** 商家注册项值4 */
	private String attributeValue4;

	/** 商家注册项值5 */
	private String attributeValue5;

	/** 商家注册项值6 */
	private String attributeValue6;

	/** 商家注册项值7 */
	private String attributeValue7;

	/** 商家注册项值8 */
	private String attributeValue8;

	/** 商家注册项值9 */
	private String attributeValue9;


	/** 地区 */
	private Area area;

	/** 商家等级 */
	private CustomerRank customerRank;

	/** 订单 */
	private Set<Order> orders = new HashSet<Order>();

	/** 收款单 */
	private Set<Payment> payments = new HashSet<Payment>();

	/** 评论 */
	private Set<Review> reviews = new HashSet<Review>();

	/** 咨询 */
	private Set<Consultation> consultations = new HashSet<Consultation>();

	/** 接收的消息 */
	private Set<Message> inMessages = new HashSet<Message>();

	/** 发送的消息 */
	private Set<Message> outMessages = new HashSet<Message>();
	
	// 多用户商城开发添加
	/** 商品 */
	private Set<Product> products = new HashSet<Product>();
	
	/** 商家文章 */
	private Set<Article> articles = new HashSet<Article>();
	
	/** 商家URL */
	private String domain;
	
	/** 商家 logo */
	private String logo;
	
	/** 商家介绍照片 */
	private String image;
	
	/** 商家模版 */
	private String template;
	
	/** 商家模版照片 */
	private String templateImage;
	
	/** 商家介绍 */
	private String descript;
	
	
	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[0-9a-z_A-Z\\u4e00-\\u9fa5]+$")
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取密码
	 * 
	 * @return 密码
	 */
	@NotEmpty(groups = Save.class)
	@Pattern(regexp = "^[^\\s&\"<>]+$")
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 * 
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 获取商家名称
	 * 
	 * @return 商家名称
	 */
	@Length(max = 200)
	public String getName() {
		return name;
	}

	/**
	 * 设置商家名称
	 * 
	 * @param name
	 *            商家名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取E-mail
	 * 
	 * @return E-mail
	 */
	@NotEmpty
	@Email
	@Length(max = 200)
	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	/**
	 * 设置E-mail
	 * 
	 * @param email
	 *            E-mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取积分
	 * 
	 * @return 积分
	 */
	@NotNull(groups = Save.class)
	@Min(0)
	@Column(nullable = false)
	public Long getPoint() {
		return point;
	}

	/**
	 * 设置积分
	 * 
	 * @param point
	 *            积分
	 */
	public void setPoint(Long point) {
		this.point = point;
	}

	/**
	 * 获取消费金额
	 * 
	 * @return 消费金额
	 */
	@Column(nullable = false, precision = 27, scale = 12)
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置消费金额
	 * 
	 * @param amount
	 *            消费金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取是否启用
	 * 
	 * @return 是否启用
	 */
	@NotNull
	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * 设置是否启用
	 * 
	 * @param isEnabled
	 *            是否启用
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 获取是否锁定
	 * 
	 * @return 是否锁定
	 */
	@Column(nullable = false)
	public Boolean getIsLocked() {
		return isLocked;
	}

	/**
	 * 设置是否锁定
	 * 
	 * @param isLocked
	 *            是否锁定
	 */
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}


	/**
	 * 获取锁定日期
	 * 
	 * @return 锁定日期
	 */
	public Date getLockedDate() {
		return lockedDate;
	}

	/**
	 * 设置锁定日期
	 * 
	 * @param lockedDate
	 *            锁定日期
	 */
	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	
	/**
	 * 获取创建日期
	 * 
	 * @return 创建日期
	 */
	public Date getBirth() {
		return birth;
	}

	/**
	 * 设置创建日期
	 * 
	 * @param birth
	 *            创建日期
	 */
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	/**
	 * 获取地址
	 * 
	 * @return 地址
	 */
	@Length(max = 200)
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 * 
	 * @param address
	 *            地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取邮编
	 * 
	 * @return 邮编
	 */
	@Length(max = 200)
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 * 
	 * @param zipCode
	 *            邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取电话
	 * 
	 * @return 电话
	 */
	@Length(max = 200)
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话
	 * 
	 * @param phone
	 *            电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取手机
	 * 
	 * @return 手机
	 */
	@Length(max = 200)
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 * 
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取商家注册项值0
	 * 
	 * @return 商家注册项值0
	 */
	@Length(max = 200)
	public String getAttributeValue0() {
		return attributeValue0;
	}

	/**
	 * 设置商家注册项值0
	 * 
	 * @param attributeValue0
	 *            商家注册项值0
	 */
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	/**
	 * 获取商家注册项值1
	 * 
	 * @return 商家注册项值1
	 */
	@Length(max = 200)
	public String getAttributeValue1() {
		return attributeValue1;
	}

	/**
	 * 设置商家注册项值1
	 * 
	 * @param attributeValue1
	 *            商家注册项值1
	 */
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	/**
	 * 获取商家注册项值2
	 * 
	 * @return 商家注册项值2
	 */
	@Length(max = 200)
	public String getAttributeValue2() {
		return attributeValue2;
	}

	/**
	 * 设置商家注册项值2
	 * 
	 * @param attributeValue2
	 *            商家注册项值2
	 */
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	/**
	 * 获取商家注册项值3
	 * 
	 * @return 商家注册项值3
	 */
	@Length(max = 200)
	public String getAttributeValue3() {
		return attributeValue3;
	}

	/**
	 * 设置商家注册项值3
	 * 
	 * @param attributeValue3
	 *            商家注册项值3
	 */
	public void setAttributeValue3(String attributeValue3) {
		this.attributeValue3 = attributeValue3;
	}

	/**
	 * 获取商家注册项值4
	 * 
	 * @return 商家注册项值4
	 */
	@Length(max = 200)
	public String getAttributeValue4() {
		return attributeValue4;
	}

	/**
	 * 设置商家注册项值4
	 * 
	 * @param attributeValue4
	 *            商家注册项值4
	 */
	public void setAttributeValue4(String attributeValue4) {
		this.attributeValue4 = attributeValue4;
	}

	/**
	 * 获取商家注册项值5
	 * 
	 * @return 商家注册项值5
	 */
	@Length(max = 200)
	public String getAttributeValue5() {
		return attributeValue5;
	}

	/**
	 * 设置商家注册项值5
	 * 
	 * @param attributeValue5
	 *            商家注册项值5
	 */
	public void setAttributeValue5(String attributeValue5) {
		this.attributeValue5 = attributeValue5;
	}

	/**
	 * 获取商家注册项值6
	 * 
	 * @return 商家注册项值6
	 */
	@Length(max = 200)
	public String getAttributeValue6() {
		return attributeValue6;
	}

	/**
	 * 设置商家注册项值6
	 * 
	 * @param attributeValue6
	 *            商家注册项值6
	 */
	public void setAttributeValue6(String attributeValue6) {
		this.attributeValue6 = attributeValue6;
	}

	/**
	 * 获取商家注册项值7
	 * 
	 * @return 商家注册项值7
	 */
	@Length(max = 200)
	public String getAttributeValue7() {
		return attributeValue7;
	}

	/**
	 * 设置商家注册项值7
	 * 
	 * @param attributeValue7
	 *            商家注册项值7
	 */
	public void setAttributeValue7(String attributeValue7) {
		this.attributeValue7 = attributeValue7;
	}

	/**
	 * 获取商家注册项值8
	 * 
	 * @return 商家注册项值8
	 */
	@Length(max = 200)
	public String getAttributeValue8() {
		return attributeValue8;
	}

	/**
	 * 设置商家注册项值8
	 * 
	 * @param attributeValue8
	 *            商家注册项值8
	 */
	public void setAttributeValue8(String attributeValue8) {
		this.attributeValue8 = attributeValue8;
	}

	/**
	 * 获取商家注册项值9
	 * 
	 * @return 商家注册项值9
	 */
	@Length(max = 200)
	public String getAttributeValue9() {
		return attributeValue9;
	}

	/**
	 * 设置商家注册项值9
	 * 
	 * @param attributeValue9
	 *            商家注册项值9
	 */
	public void setAttributeValue9(String attributeValue9) {
		this.attributeValue9 = attributeValue9;
	}

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 获取商家等级
	 * 
	 * @return 商家等级
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public CustomerRank getCustomerRank() {
		return customerRank;
	}

	/**
	 * 设置商家等级
	 * 
	 * @param customerRank
	 *            商家等级
	 */
	public void setCustomerRank(CustomerRank customerRank) {
		this.customerRank = customerRank;
	}
	
	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Order> getOrders() {
		return orders;
	}

	/**
	 * 设置订单
	 * 
	 * @param orders
	 *            订单
	 */
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	/**
	 * 获取收款单
	 * 
	 * @return 收款单
	 */
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Payment> getPayments() {
		return payments;
	}

	/**
	 * 设置收款单
	 * 
	 * @param payments
	 *            收款单
	 */
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * 获取评论
	 * 
	 * @return 评论
	 */
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public Set<Review> getReviews() {
		return reviews;
	}

	/**
	 * 设置评论
	 * 
	 * @param reviews
	 *            评论
	 */
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * 获取咨询
	 * 
	 * @return 咨询
	 */
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate desc")
	public Set<Consultation> getConsultations() {
		return consultations;
	}

	/**
	 * 设置咨询
	 * 
	 * @param consultations
	 *            咨询
	 */
	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

	/**
	 * 获取接收的消息
	 * 
	 * @return 接收的消息
	 */
	@OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Message> getInMessages() {
		return inMessages;
	}

	/**
	 * 设置接收的消息
	 * 
	 * @param inMessages
	 *            接收的消息
	 */
	public void setInMessages(Set<Message> inMessages) {
		this.inMessages = inMessages;
	}

	/**
	 * 获取发送的消息
	 * 
	 * @return 发送的消息
	 */
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Message> getOutMessages() {
		return outMessages;
	}

	/**
	 * 设置发送的消息
	 * 
	 * @param outMessages
	 *            发送的消息
	 */
	public void setOutMessages(Set<Message> outMessages) {
		this.outMessages = outMessages;
	}
	
	
	
	// 多用户商城添加
	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 设置商品
	 * 
	 * @param products
	 *            商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
	/**
	 * 获取文章
	 * 
	 * @return 文章
	 */
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	public Set<Article> getArticles() {
		return articles;
	}
	
	/**
	 * 设置文章
	 * 
	 * @param articles
	 *            文章
	 */
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	/**
	 * 获取商家域名
	 * 
	 * @return 商家域名
	 */
	public String getdomain() {
		return domain;
	}

	/**
	 * 设置商家域名
	 * 
	 * @param domain
	 *            商家域名
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * 获取商家logo
	 * 
	 * @return 商家logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * 设置商家logo
	 * 
	 * @param logo
	 *            商家logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * 获取商家照片
	 * 
	 * @return 商家照片
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 设置商家照片
	 * 
	 * @param image
	 *            商家照片
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取商家模版
	 * 
	 * @return 商家模版
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * 设置商家模版
	 * 
	 * @param template
	 *            商家模版
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * 获取模版照片
	 * 
	 * @return 模版照片
	 */
	public String getCustomerImage() {
		return templateImage;
	}

	/**
	 * 设置商家模版照片
	 * 
	 * @param templateImage
	 *            模版照片
	 */
	public void setCustomerImage(String templateImage) {
		this.templateImage =templateImage;
	}

	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	public String getDescript() {
		return descript;
	}

	/**
	 * 设置商家介绍
	 * 
	 * @param descript
	 *            商家介绍
	 */
	public void setDescript(String descript) {
		this.descript = descript;
	}

	/**
	 * 获取商家注册项值
	 * 
	 * @param customerAttribute
	 *            商家注册项
	 * @return 商家注册项值
	 */
	@Transient
	public Object getAttributeValue(CustomerAttribute customerAttribute) {
		if (customerAttribute != null) {
			if (customerAttribute.getType() == Type.name) {
				return getName();
			} else if (customerAttribute.getType() == Type.area) {
				return getArea();
			} else if (customerAttribute.getType() == Type.address) {
				return getAddress();
			} else if (customerAttribute.getType() == Type.zipCode) {
				return getZipCode();
			} else if (customerAttribute.getType() == Type.phone) {
				return getPhone();
			} else if (customerAttribute.getType() == Type.mobile) {
				return getMobile();
			} else if (customerAttribute.getType() == Type.checkbox) {
				if (customerAttribute.getPropertyIndex() != null) {
					try {
						String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + customerAttribute.getPropertyIndex();
						String propertyValue = (String) PropertyUtils.getProperty(this, propertyName);
						if (propertyValue != null) {
							return JsonUtils.toObject(propertyValue, List.class);
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (customerAttribute.getPropertyIndex() != null) {
					try {
						String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + customerAttribute.getPropertyIndex();
						return (String) PropertyUtils.getProperty(this, propertyName);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 设置商家注册项值
	 * 
	 * @param customerAttribute
	 *            商家注册项
	 * @param attributeValue
	 *            商家注册项值
	 */
	@Transient
	public void setAttributeValue(CustomerAttribute customerAttribute, Object attributeValue) {
		if (customerAttribute != null) {
			if (attributeValue instanceof String && StringUtils.isEmpty((String) attributeValue)) {
				attributeValue = null;
			}
			if (customerAttribute.getType() == Type.name && (attributeValue instanceof String || attributeValue == null)) {
				setName((String) attributeValue);
			}else if (customerAttribute.getType() == Type.birth && (attributeValue instanceof Date || attributeValue == null)) {
				setBirth((Date) attributeValue);
			} else if (customerAttribute.getType() == Type.area && (attributeValue instanceof Area || attributeValue == null)) {
				setArea((Area) attributeValue);
			} else if (customerAttribute.getType() == Type.address && (attributeValue instanceof String || attributeValue == null)) {
				setAddress((String) attributeValue);
			} else if (customerAttribute.getType() == Type.zipCode && (attributeValue instanceof String || attributeValue == null)) {
				setZipCode((String) attributeValue);
			} else if (customerAttribute.getType() == Type.phone && (attributeValue instanceof String || attributeValue == null)) {
				setPhone((String) attributeValue);
			} else if (customerAttribute.getType() == Type.mobile && (attributeValue instanceof String || attributeValue == null)) {
				setMobile((String) attributeValue);
			} else if (customerAttribute.getType() == Type.checkbox && (attributeValue instanceof List || attributeValue == null)) {
				if (customerAttribute.getPropertyIndex() != null) {
					if (attributeValue == null || (customerAttribute.getOptions() != null && customerAttribute.getOptions().containsAll((List<?>) attributeValue))) {
						try {
							String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + customerAttribute.getPropertyIndex();
							PropertyUtils.setProperty(this, propertyName, JsonUtils.toJson(attributeValue));
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				if (customerAttribute.getPropertyIndex() != null) {
					try {
						String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + customerAttribute.getPropertyIndex();
						PropertyUtils.setProperty(this, propertyName, attributeValue);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 移除所有商家注册项值
	 */
	@Transient
	public void removeAttributeValue() {
		setBirth(null);
		setArea(null);
		setAddress(null);
		setZipCode(null);
		setPhone(null);
		setMobile(null);
		for (int i = 0; i < ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			try {
				PropertyUtils.setProperty(this, propertyName, null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除前处理
	 * 商家删除前必须处理商品，订单，收款单，文章，等的关系
	 */
	@PreRemove
	public void preRemove() {
		Set<Product> products = getProducts();
		if (products != null) {
			for (Product product : products) {
				product.setCustomer(null);
			}
		}
		/**
		// 商品分类删除操作
		Set<ProductCategory> productCategories = getProductCategories();
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				productCategory.getMembers().remove(this);
			}
		}
		// 商品促销删除操作
		Set<Promotion> promotions = getPromotions();
		if (promotions != null) {
			for (Promotion promotion : promotions) {
				promotion.getMembers().remove(this);
			}
		}
		**/
	}
}